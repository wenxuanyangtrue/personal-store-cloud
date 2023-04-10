package top.xsword.system_service.mybatis_plus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.model.base.BaseEntity;

/**
 * Data:2022/11/21
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class MybatisGeneratorTest {
    String url = "jdbc:mysql://localhost:3306/personal-store-cloud?characterEncoding=utf-8&serverTimezone=UTC";
    String username = "root";
    String password = "201029";

    @Test
    public void generator() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("ywx"). // 设置作者
                            enableSwagger(). // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                                    outputDir("D:\\MyStorageSpace\\Code\\JavaCode\\personal-store-cloud\\system-service\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("top.xsword.system_service"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.enableCapitalMode(). // 开启大写命名
                            //addInclude("sys_user"). //增加表匹配
                            entityBuilder().
                            enableLombok().
                            superClass(BaseEntity.class).
                            addSuperEntityColumns("id", "create_time", "update_time", "deleted").
                            idType(IdType.ASSIGN_ID).
                            controllerBuilder().
                            enableRestStyle().
                            enableHyphenStyle().
                            serviceBuilder().
                            formatServiceFileName("%sService");

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}