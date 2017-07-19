package com.zkyf;

import com.zkyf.core.dao.supper.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com"})
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
