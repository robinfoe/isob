package com.robin.bridge.isob.factory;

import java.net.URL;

import com.robin.bridge.isob.dataformat.IsoSample;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

public class IsoMessageFactory {

    public static final String FILE_CONFIG = "iso8583/config.xml";
    private MessageFactory<IsoMessage> messageFactory;
    public MessageFactory<IsoMessage> getMessageFactory(){return this.messageFactory;}

    public static IsoMessageFactory INSTANCE;

    private IsoMessageFactory(){
         try{
            URL configLoader = IsoSample.class.getClassLoader().getResource(FILE_CONFIG);
            messageFactory = ConfigParser.createFromUrl(configLoader);
            messageFactory.setAssignDate(true);
            messageFactory.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System.currentTimeMillis() % 100000)));
        }catch(Exception e){
            throw new RuntimeException("ISO8583DataFormat - Unable to parse File Config");
        }
    }

    public static IsoMessageFactory getInstance(){
        if(IsoMessageFactory.INSTANCE == null){
            IsoMessageFactory.INSTANCE = new IsoMessageFactory();
        }
        return IsoMessageFactory.INSTANCE;

    }


    
}
