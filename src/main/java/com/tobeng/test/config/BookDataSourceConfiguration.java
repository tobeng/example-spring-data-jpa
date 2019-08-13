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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryBook",//实体管理引用
        transactionManagerRef="transactionManagerBook",//事务管理引用
        basePackages = { "com.tobeng.test.*.book"}) //设置书籍数据源所应用到的包
public class BookDataSourceConfiguration
{
    //注入书籍数据源
    @Autowired
    @Qualifier("bookDataSource")
    private DataSource bookDataSource;

    //配置EntityManager实体
    @Primary
    @Bean(name = "entityManagerBook")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryBook(builder).getObject().createEntityManager();
    }

    //配置EntityManager工厂实体
    @Primary
    @Bean(name = "entityManagerFactoryBook")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBook (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(bookDataSource)
                .properties(getVendorProperties(bookDataSource))
                .packages(new String[]{ "com.tobeng.test.entity.book" }) //设置应用creditDataSource的基础包名
                .persistenceUnit("bookPersistenceUnit")
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
    @Primary
    @Bean(name = "transactionManagerBook")
    public PlatformTransactionManager transactionManagerBook(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryBook(builder).getObject());
    }
}
