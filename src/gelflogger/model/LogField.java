/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author krisa.chaijaroen
 */
public class LogField {

    private String field;
    private String regexPattern;

    public LogField(String field, String regexPattern) {
        this.field = field;
        this.regexPattern = regexPattern;
    }

    public static void main(String[] args) {
        //weblogic("<Aug 14, 2012 3:06:19 PM ICT> <Error> <oms> <BEA-000000> <core.logSQLException: SQL Exception 20,502 : ORA-20502: Automation context not found.");
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the regexPattern
     */
    public String getRegexPattern() {
        return regexPattern;
    }

    /**
     * @param regexPattern the regexPattern to set
     */
    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }
}
