package top.xsword.common_service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.xsword.common_util.oss.OSSUtil;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
@Component
public class OSSClientSchedule {

    @Scheduled(cron = "59 59 * * * ?")
    public void clearCache(){
        OSSUtil.ossClientCache.clear();
    }

}
