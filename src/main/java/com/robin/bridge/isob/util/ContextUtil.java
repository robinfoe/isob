package com.robin.bridge.isob.util;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import lombok.Getter;

public class ContextUtil {

    private static final Logger log = LoggerFactory.getLogger(ContextUtil.class);
    private static ContextUtil INSTANCE;

    
    @Getter
    private ApplicationContext springContext;

    @Getter
    private CamelContext camelContext;
    
    private ContextUtil(){/* ENSURE SINGLETON */}

    

    public static void init(ApplicationContext context){
        if(ContextUtil.INSTANCE != null){
            ContextUtil.INSTANCE.springContext = context;
            // context.getAliases('null')
        }

        String[] beans = context.getBeanDefinitionNames();

        for(String item : beans){
            log.info(item);
        }
    }

    public static ContextUtil getInstance(){
        if (ContextUtil.INSTANCE == null){ throw new IllegalStateException("ContextUtil is null. Please initialize context util before proceed.");}

        return ContextUtil.INSTANCE;
    }

}
