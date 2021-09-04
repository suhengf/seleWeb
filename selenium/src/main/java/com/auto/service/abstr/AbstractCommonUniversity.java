package com.auto.service.abstr;

import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.utils.LoginUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jdk.nashorn.internal.ir.CallNode;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractCommonUniversity implements University{
    @Autowired
    private UniversityResolver universityResolver;

    private static final String WIN_WEBDRIVER = "src\\main\\files\\chromedriver.exe";

    ThreadFactory threadFactory =  new ThreadFactoryBuilder().setNameFormat("threadFactory-%d").build();
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(getPoolParam().getCorePoolSize(), getPoolParam().getMaximumPoolSize(), 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    //解析名单
    //起线程 去执行其中一个子类的主处理方法
    @Override
    public void excute(String university,int course) throws Exception {
        List<UserInfo> userInfoList = LoginUtils.parseUserList(getWinWebdriverPath() , getFilePath());
        ChromeOptions chromeOptions = new ChromeOptions();
        for (UserInfo userInfo : userInfoList) {
            Runnable task = () -> {
                try {
                    universityResolver.getExecutor(university).singleHandler(userInfo,chromeOptions,course);
                } catch (Exception e) {
                    log.info("异常",e);
                    throw new BizException(ErrorEnum.getErrorEnum(university).getMsg());
                }
            };
            executor.execute(task);
            Thread.sleep(10000);
        }
        log.info("哈哈");
    }


    //设置线程参数
    public ThreadPoolParam getPoolParam(){
        return ThreadPoolParam.builder().corePoolSize(1).maximumPoolSize(4).build();
    }

    //传文件名
    public abstract String getFilePath();


    public String getWinWebdriverPath() {
        return WIN_WEBDRIVER;
    }

}
