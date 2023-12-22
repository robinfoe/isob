package com.robin.bridge.isob.dataformat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.DataFormatName;
import org.apache.camel.support.service.ServiceSupport;

import com.robin.bridge.isob.factory.IsoMessageFactory;
import com.solab.iso8583.IsoMessage;

public final class ISO8583DataFormat extends ServiceSupport implements DataFormat, DataFormatName {

    private static final String NAME="ISO8583";
    public static final String FILE_CONFIG = "iso8583/config.xml";

    private static  ISO8583DataFormat INSTANCE;


    @Override
    // only required if we have diff message format. netty except byteBuffer and file region as part of exchange.
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {

        System.err.println("marshalling.... ");
        IsoMessage message = exchange.getIn().getBody(IsoMessage.class);


        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        message.write(outStream,0);
        String response = new String(outStream.toByteArray());
        System.err.println(response);
        exchange.getMessage().setBody(response);
       
    }

    @Override
    // only required if we have diff message format. netty except byteBuffer and file region as part of exchange.
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        String incoming = exchange.getIn().getBody(String.class);
        // 1. re-parse this to ISO messages 
        // IsoMessageFactory.get
        IsoMessage message = IsoMessageFactory.getInstance().getMessageFactory().parseMessage(incoming.getBytes(), 12);
        return message;
    }

    @Override
    public String getDataFormatName() {
        return ISO8583DataFormat.NAME;
    }

    private ISO8583DataFormat(){/***ENSURE SINGLETON*/}
    public static ISO8583DataFormat getInstance(){
        if(ISO8583DataFormat.INSTANCE == null){
            ISO8583DataFormat.INSTANCE = new ISO8583DataFormat();
        }
        return ISO8583DataFormat.INSTANCE;
    }
    
}
