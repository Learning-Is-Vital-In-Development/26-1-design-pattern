package me.study.strategypattern;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class StrategyInSpring {

    void ac() {
        ApplicationContext ac1 = new ClassPathXmlApplicationContext();
        ApplicationContext ac2 = new FileSystemXmlApplicationContext();
        ApplicationContext ac3 = new AnnotationConfigApplicationContext();
    }

    void bdp() {
        BeanDefinitionParser bdp1 = new AnnotationConfigBeanDefinitionParser();
        BeanDefinitionParser bdp2 = new ComponentScanBeanDefinitionParser();
    }

    void ptm() {
        PlatformTransactionManager ptm1 = new JdbcTransactionManager();
        PlatformTransactionManager ptm2 = new JpaTransactionManager();
    }

    void cm() {
        CacheManager cm1 = new CaffeineCacheManager();
        CacheManager cm2 = new NoOpCacheManager();
    }
}
