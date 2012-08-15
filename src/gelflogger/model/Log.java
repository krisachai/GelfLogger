/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author krisa.chaijaroen
 */
public class Log {
    private String host;
    private String pattern;
    private String facility;
    private String path;
    private Map fields;
    private String graylogHost;
    private boolean debug;
    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the facility
     */
    public String getFacility() {
        return facility;
    }

    /**
     * @param facility the facility to set
     */
    public void setFacility(String facility) {
        this.facility = facility;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the fields
     */
    public Map getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(Map fields) {
        this.fields = fields;
    }
    
    @Override
    public String toString(){
        String fieldsList = "[";
        for (Iterator it = fields.entrySet().iterator(); it.hasNext();) {
            Map.Entry object = (Map.Entry) it.next();
            String key = (String) object.getKey();
            String val= (String) object.getValue();
            fieldsList += "["+key+","+val+"]";
        }
        fieldsList += "]";
        return path+","+pattern+","+host+","+facility+"\n"+fieldsList;
    }

    /**
     * @return the graylogHost
     */
    public String getGraylogHost() {
        return graylogHost;
    }

    /**
     * @param graylogHost the graylogHost to set
     */
    public void setGraylogHost(String graylogHost) {
        this.graylogHost = graylogHost;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
