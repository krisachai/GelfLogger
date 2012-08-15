/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger;

import gelflogger.model.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author krisa.chaijaroen
 */
public class ConfigurationLoader {

    static Map data = null;

    static {
        Yaml yaml = new Yaml();
        try {
            data = (Map) yaml.load(new FileInputStream(new File("config.yml")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List parse() {
        boolean debug = (Boolean)data.get("debug");
        if(debug){
            System.out.println("Loading Configuration");
        }
        String grayloghostname = (String) data.get("graylogHost");
        
        List<Map> logs = (List) data.get("logs");
        List<Log> result = new ArrayList<Log>();
        for (Map log : logs) {
            Log tlog = new Log();
            tlog.setDebug(debug);
            tlog.setGraylogHost(grayloghostname);
            tlog.setPath((String) log.get("path"));
            tlog.setPattern((String) log.get("pattern"));
            tlog.setHost((String) log.get("host"));
            tlog.setFacility((String) log.get("facility"));

            List<Map> fields = (List) log.get("fields");
            Map temp = new HashMap<String, String>();
            for (Map field : fields) {

                Iterator it = field.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry) it.next();
                    temp.put(pairs.getKey(), pairs.getValue());
                }


            }
            tlog.setFields(temp);
            result.add(tlog);
        }
        if(debug){
            System.out.println("Configuration Loaded");
        }
        return result;
    }
    public static void main(String[] args){
        System.out.println(parse());
        
       
         
    }
}
