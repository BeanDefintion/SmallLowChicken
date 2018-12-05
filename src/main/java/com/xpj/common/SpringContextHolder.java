package com.xpj.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.context = context;

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("beanName: " + beanName);
        }
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static Object getBean(Class bean) {
        return context.getBean(bean);
    }

}

