/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger;

import gelflogger.model.MessageListener;
import gelflogger.model.LogPattern;
import gelflogger.model.Log;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graylog2.GelfMessage;
import org.graylog2.GelfSender;
import util.GelfLoggerUtil;
import util.SyslogLevel;

/**
 *
 * @author krisa.chaijaroen
 */
public class Gelffer implements Runnable, MessageListener {

    private Log log;
    private GelfSender sender;

    public Gelffer(Log log) throws Exception {
        this.log = log;
        sender = new GelfSender(log.getGraylogHost());
    }

    @Override
    public void run() {

        try {
           
            JTail tailler = new JTail(this);
            tailler.tail(log.getPath());



        } catch (InterruptedException ex) {
            Logger.getLogger(Gelffer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Gelffer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onMessage(String msg) {
        GelfMessage message = draft(msg);
        if (message.isValid()) {

            boolean sent = sender.sendMessage(message);
            if (log.isDebug()) {
                System.out.println("Message Sent:" + sent);
            }
        } else {
            if (log.isDebug()) {
                System.out.println("Message is invalid");
            }
        }
    }

    public GelfMessage draft(String s) {
        GelfMessage message = new GelfMessage();
        message.setShortMessage(s);
        message.setFullMessage(s);
        message.setJavaTimestamp(System.currentTimeMillis());
        message.setVersion("1");
        message.setHost(log.getHost());
        message.setFacility(log.getFacility());
        

        Map<String, String> addFields = LogPattern.weblogic(s);
        for (Map.Entry<String, String> entry : addFields.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            message.addField(key, val);

            if (key.equals("Log Level")) {
                message.setLevel(String.valueOf(GelfLoggerUtil.mapLogLevel(GelfLoggerUtil.mapStringToSyslog(val))));
            }
        }
        if(message.getLevel()==null){
            if(log.isDebug()){
                System.out.println("Can't find log level, setting debug as default");
            }
            message.setLevel(String.valueOf(GelfLoggerUtil.mapLogLevel(SyslogLevel.DEBUG)));
        }
        if(log.isDebug()){
            System.out.println("Gelf Message Formed Successfully");
        }
        return message;
    }
}
