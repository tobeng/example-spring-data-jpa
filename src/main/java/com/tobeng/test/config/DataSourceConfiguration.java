package com.tobeng.test.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    //用户数据源
    @Bean(name = "userDataSource")//装配该方法返回值为userDataSource管理bean
    @Qualifier("userDataSource")//spring装配bean唯一标识
    @ConfigurationProperties(prefix="spring.datasource.user")//application.yml文件内配置数据源的前缀
    public DataSource userDataSource(){return DataSourceBuilder.create().build();}

    //书籍数据源
    @Bean(name = "bookDataSource")
    @Primary//配置该数据源为主数据源
    @Qualifier("bookDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.book")
    public DataSource bookDataSource(){return DataSourceBuilder.create().build();}

}
