package top.xsword.model.vo;

import lombok.Data;
import top.xsword.model.entity.File;

import java.util.List;

/**
 * Data:2022/11/29
 * Author:ywx
 * Description:
 */
@Data
public class FileVo {
    private String dirKey;
    private List<File> files;
}
