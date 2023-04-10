package top.xsword.model.vo;

import lombok.Data;
import top.xsword.model.entity.Dir;

import java.util.List;

/**
 * Data:2022/11/28
 * Author:ywx
 * Description:
 */
@Data
public class DirVo {
    //创建目录时，当前目录的key
    private String currentDirId;
    //需要创建的目录
    private List<Dir> dirs;
}
