package com.robin.bridge.isob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

// @Component
public class OnStart implements ApplicationRunner{

    private static final Logger log = LoggerFactory.getLogger(OnStart.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("started... ");
        // for(String item : ContextProvider.getContext().getBeanDefinitionNames()){
        //     log.info(item);
        // }
        // CamelContext camelContext = ContextProvider.getCamelContext();
        // camelContext.addRoutes(new NettyRouteBuilder());

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
