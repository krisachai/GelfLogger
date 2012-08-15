/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger;

import gelflogger.model.Log;
import java.util.List;
import org.graylog2.GelfMessage;
import org.graylog2.GelfSender;

/**
 *
 * @author krisa.chaijaroen
 */
public class GelfLogger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        List<Log> logs = ConfigurationLoader.parse();

        for (Log log : logs) {
            //Create New GelfLogger
            log.setHost(util.GelfLoggerUtil.findMachineHostname());
            Gelffer gf = new Gelffer(log);
            Thread t = new Thread(gf);
            t.start();
            if (log.isDebug()) {
                System.out.println("New Thread for " + log.getPath() + " has started");
            }
        }

    }
}
