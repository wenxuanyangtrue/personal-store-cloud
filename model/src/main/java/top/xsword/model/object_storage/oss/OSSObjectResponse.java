package top.xsword.model.object_storage.oss;

import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/5
 * Description:
 */
@Data
public class OSSObjectResponse {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @ToString(callSuper = true)
    public static class OSSObjectSummaryWrap extends OSSObjectSummary {
        private String formattedSize;
        private Boolean dir = false;

        //文件直接调用该构造方法
        public OSSObjectSummaryWrap(OSSObjectSummary objectSummary) {
            super.setBucketName(objectSummary.getBucketName());
            super.setETag(objectSummary.getETag());
            super.setKey(objectSummary.getKey());
            super.setOwner(objectSummary.getOwner());
            super.setLastModified(objectSummary.getLastModified());
            super.setSize(objectSummary.getSize());
            super.setStorageClass(objectSummary.getStorageClass());
            super.setType(objectSummary.getType());
        }
    }

    private List<OSSObjectSummary> objectSummaries; //目录和文件一同放在这里
    private String bucketName;
    private int keyCount;
    private String continuationToken;
    private String nextContinuationToken;
    private String startAfter;
    private boolean isTruncated;
    private String prefix;
    private int maxKeys;
    private String delimiter;
    private String encodingType;

    public OSSObjectResponse(ListObjectsV2Result listObjectsV2Result) {
        this.bucketName = listObjectsV2Result.getBucketName();
        this.keyCount = listObjectsV2Result.getKeyCount();
        this.continuationToken = listObjectsV2Result.getContinuationToken();
        this.nextContinuationToken = listObjectsV2Result.getNextContinuationToken();
        this.startAfter = listObjectsV2Result.getStartAfter();
        this.isTruncated = listObjectsV2Result.isTruncated();
        this.prefix = listObjectsV2Result.getPrefix();
        this.maxKeys = listObjectsV2Result.getMaxKeys();
        this.delimiter = listObjectsV2Result.getDelimiter();
        this.encodingType = listObjectsV2Result.getEncodingType();

        List<String> commonPrefixes = listObjectsV2Result.getCommonPrefixes();
        List<OSSObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();
        this.objectSummaries = new ArrayList<>(commonPrefixes.size() + objectSummaries.size());

        //将前缀当作目录放入this.objectSummaries
        commonPrefixes.forEach((commonPrefix) -> {
            OSSObjectSummaryWrap ossObjectSummaryWrap = new OSSObjectSummaryWrap();
            ossObjectSummaryWrap.setBucketName(listObjectsV2Result.getBucketName());
            ossObjectSummaryWrap.setDir(true);
            ossObjectSummaryWrap.setKey(commonPrefix);
            this.objectSummaries.add(ossObjectSummaryWrap);
        });
        //将文件放入this.objectSummaries
        objectSummaries.forEach((objectSummary) -> {
            //将文件目录去除掉
            if(!objectSummary.getKey().equals(listObjectsV2Result.getPrefix())){
                this.objectSummaries.add(new OSSObjectSummaryWrap(objectSummary));
            }
        });

    }
}
