/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger.model;

import gelflogger.model.LogField;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author krisa.chaijaroen
 */
public class LogPattern {

    private static Pattern logLevelPattern, erroCodePattern;
    private static LogField logLevel = new LogField("Log Level", "<TRACE|DEBUG|INFO|NOTICE|WARNING|ERROR|CRITICAL|ALERT|EMERGENCY>");
    private static LogField errorCode = new LogField("Error Code", "BEA-[0-9]*|ORA-[0-9]*");

    static {
        //Weblogic
        logLevelPattern = Pattern.compile(logLevel.getRegexPattern(), Pattern.CASE_INSENSITIVE);
        erroCodePattern = Pattern.compile(errorCode.getRegexPattern(), Pattern.CASE_INSENSITIVE);
    }

    public static Map<String, String> weblogic(String msg) {

        Map<String, String> result = new HashMap();

        Matcher matchLogLevel = logLevelPattern.matcher(msg);
    

        if (matchLogLevel.find()) {

            result.put(logLevel.getField(), msg.substring(matchLogLevel.start(), matchLogLevel.end()));
        }
        
        Matcher matchErrorCode = erroCodePattern.matcher(msg);

        if (matchErrorCode.find()) {

            result.put(errorCode.getField(), msg.substring(matchErrorCode.start(), matchErrorCode.end()));

        }



        return result;

    }

    public static void main(String[] args) {
        weblogic("<Aug 14, 2012 3:06:19 PM ICT> <Error> <oms> <BEA-000000> <core.logSQLException: SQL Exception 20,502 : ORA-20502: Automation context not found.");
    }
}
