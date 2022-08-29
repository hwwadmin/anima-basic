package com.anima.basic.starter;

import com.anima.basic.boot.config.guide.ApplicationBoot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统启动类
 *
 * @author hww
 */
@SpringBootApplication(scanBasePackages = {"com.anima.basic.*"})
@EnableJpaRepositories(basePackages = {"com.anima.basic.*"})
@EntityScan(basePackages = {"com.anima.basic.*"})
@EnableScheduling
public class AppStartup {

    public static void main(String[] args) {
        ApplicationBoot.boot(AppStartup.class).run(args);
    }

}
