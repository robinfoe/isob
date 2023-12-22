package com.robin.bridge.isob.dataformat;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

public class IsoSample {

    public static final String FILE_CONFIG = "iso8583/config.xml";
    public static final String FILE_SAMPLE = "iso8583/sample.txt";

    public void init(){

        try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(IsoSample.class.getClassLoader().getResourceAsStream(IsoSample.FILE_SAMPLE)));){

            URL configLoader = IsoSample.class.getClassLoader().getResource(FILE_CONFIG);
            MessageFactory<IsoMessage> mfact = ConfigParser.createFromUrl(configLoader);
            mfact.setAssignDate(true);
            mfact.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System.currentTimeMillis() % 100000)));

            
            String line = reader.readLine();
		    while (line != null && line.length() > 0) {
			    IsoMessage m = mfact.parseMessage(line.getBytes(), 12);
			    print(m);
			    line = reader.readLine();
		    }
		    reader.close();


            IsoMessage m = mfact.newMessage(0x200);
            m.setBinary(true);
            m.setValue(4, new BigDecimal("501.25"), IsoType.AMOUNT, 0);
            m.setValue(12, new Date(), IsoType.TIME, 0);
            m.setValue(15, new Date(), IsoType.DATE4, 0);
            m.setValue(17, new Date(), IsoType.DATE_EXP, 0);
            m.setValue(37, 12345678, IsoType.NUMERIC, 12);
            m.setValue(41, "TEST-TERMINAL", IsoType.ALPHA, 16);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            m.write(outStream,0);
            System.err.println("-----");
            System.err.println( new String(outStream.toByteArray()) );

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public  void print(IsoMessage m) {
		System.out.printf("TYPE: %04x\n", m.getType());
		for (int i = 2; i < 128; i++) {
			if (m.hasField(i)) {
				System.out.printf("F %3d(%s): %s -> '%s'\n", i, m.getField(i)
						.getType(), m.getObjectValue(i), m.getField(i)
						.toString());
			}
		}
	}

    // public static void main(String... args){
    //     (new IsoSample()).init();
    // }
    
}
