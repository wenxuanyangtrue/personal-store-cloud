package top.xsword.common_service.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * Data:2022/11/20
 * Author:ywx
 * Description:
 */
@Component
public class TableMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, Date.from(Instant.now()));
        this.strictInsertFill(metaObject, "updateTime", Date.class, Date.from(Instant.now()));
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "avatar", String.class, "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        this.strictInsertFill(metaObject, "role", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateTime", Date.class, Date.from(Instant.now()));
    }
}
