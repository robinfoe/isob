package com.robin.bridge.isob.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.robin.bridge.isob.bean.BeanBridge;
import com.robin.bridge.isob.dataformat.ISO8583DataFormat;

@Component
public class NettyRouteBuilder extends RouteBuilder{

    // private static final Logger log = LoggerFactory.getLogger(NettyRouteBuilder.class);


    @Override
    public void configure() throws Exception {
        from("netty:tcp://{{route.netty.host}}")
        .log(LoggingLevel.INFO,"INCOMING :: ")
        .unmarshal(ISO8583DataFormat.getInstance())
        .bean(BeanBridge.getInstance(),"print")
        .bean(BeanBridge.getInstance(),"generateResponse")
        
        // .marshal(ISO8583DataFormat.getInstance())
        .to("log:beforeOut");
    }
    
}
