package com.robin.bridge.isob.bean;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.camel.Exchange;

import com.robin.bridge.isob.factory.IsoMessageFactory;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;

public class BeanBridge {


    private static BeanBridge INSTANCE;
    private BeanBridge(){/***ENSURE SINGLETON*/}

    public void print(Exchange exchange){
        System.err.println("exchange bean bridge");
        IsoMessage message = exchange.getIn().getBody(IsoMessage.class);

        for (int i = 2; i < 128; i++) {
			if (message.hasField(i)) {
				System.out.printf("F %3d(%s): %s -> '%s'\n", i, message.getField(i)
						.getType(), message.getObjectValue(i), message.getField(i)
						.toString());
			}
		}
    }


    public void generateResponse(Exchange exchange) throws Exception{

        // System.err.println("test");
        IsoMessage m = IsoMessageFactory.getInstance().getMessageFactory().newMessage(0x200);
        m.setBinary(true);
        m.setValue(4, new BigDecimal("501.25"), IsoType.AMOUNT, 0);
        m.setValue(12, new Date(), IsoType.TIME, 0);
        m.setValue(15, new Date(), IsoType.DATE4, 0);
        m.setValue(17, new Date(), IsoType.DATE_EXP, 0);
        m.setValue(37, 12345678, IsoType.NUMERIC, 12);
        m.setValue(41, "TEST-TERMINAL", IsoType.ALPHA, 16);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        m.write(outStream,0);
        String response = new String(outStream.toByteArray());
        exchange.getMessage().setBody(response);


        // exchange.getIn().setBody("hello testing..");
        // exchange.getIn().setBody(m);
    }


    public static BeanBridge getInstance(){
        if(BeanBridge.INSTANCE == null){
            BeanBridge.INSTANCE = new BeanBridge();
        }
        return BeanBridge.INSTANCE;
    }
    
}
