package com.robin.bridge.isob;

import org.apache.camel.CamelContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware{

    private static ApplicationContext context;
    private static String CONTEXT_CAMEL = "camelContext";

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ContextProvider.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static CamelContext getCamelContext(){
        return (CamelContext)ContextProvider.getContext().getBean(CONTEXT_CAMEL, CamelContext.class);
    }
    
}
