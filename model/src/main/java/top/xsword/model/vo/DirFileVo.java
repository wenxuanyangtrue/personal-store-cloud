package top.xsword.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:返回前端的当前目录的内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirFileVo {
    private Boolean dir; //是否为目录
    private String parentId; //父级目录的id
    private String id; //id
    private String name; //名字
    private Date updateTime; //更新时间，上传时间
    private Long size; //文件大小
    private String formattedSize; //格式化后的文件大小字符串
    private String suffix; //文件后缀
    private String type; //Content-Type类型
}
