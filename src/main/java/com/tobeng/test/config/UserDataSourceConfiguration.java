package com.tobeng.test.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 91信用卡数据源详细配置
 * 项目名称：credirCardApi
 * 项目版本：V1.0
 * 包名称：com.bankcard.api
 * 创建人：yuqy
 * 创建时间：2017/2/19 15:40
 * 修改人：yuqy
 * 修改时间：2017/2/19 15:40
 * 修改备注：
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryUser",//实体管理引用
        transactionManagerRef="transactionManagerUser",//失误管理引用
        basePackages = { "com.tobeng.test.dao.user"}) //设置用户数据源所应用到的包
public class UserDataSourceConfiguration {
    //注入用户数据源
    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;

    //配置EntityManager实体
    @Bean(name = "entityManagerUser")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryUser(builder).getObject().createEntityManager();
    }

    //配置EntityManager工厂实体
    @Bean(name = "entityManagerFactoryUser")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryUser (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource)
                .properties(getVendorProperties(userDataSource))
                .packages(new String[]{ "com.tobeng.test.entity.user" }) //设置应用creditDataSource的基础包名
                .persistenceUnit("userPersistenceUnit")
                .build();
    }

    //注入jpa配置实体
    @Autowired
    private JpaProperties jpaProperties;

    //获取jpa配置信息
    private Map<String, Object> getVendorProperties(DataSource dataSource) {
        // springboot 1.5.x 方式
//        return jpaProperties.getHibernateProperties(dataSource);

        // springboot 2.1.x 方式
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Autowired
    private HibernateProperties hibernateProperties;

    //配置事务
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager transactionManagerUser(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryUser(builder).getObject());
    }
}
