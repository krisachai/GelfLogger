/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.net.UnknownHostException;

/**
 *
 * @author krisa.chaijaroen
 */
public class GelfLoggerUtil {

    public static String findMachineHostname() throws UnknownHostException {
        return java.net.InetAddress.getLocalHost().getHostName();
    }

    public static int mapLogLevel(SyslogLevel level) {

        if (level.equals(SyslogLevel.DEBUG)) {
            return 7;
        } else if (level.equals(SyslogLevel.INFO)) {
            return 6;
        } else if (level.equals(SyslogLevel.NOTICE)) {
            return 5;
        } else if (level.equals(SyslogLevel.WARNING)) {
            return 4;
        } else if (level.equals(SyslogLevel.ERROR)) {
            return 3;
        } else if (level.equals(SyslogLevel.CRITICAL)) {
            return 2;
        } else if (level.equals(SyslogLevel.ALERT)) {
            return 1;
        } else if (level.equals(SyslogLevel.EMERGENCY)) {
            return 0;
        }
        return -1;
    }

    public static SyslogLevel mapStringToSyslog(String level) {
        level = level.toLowerCase().trim();
        if (level.equals("debug")) {
            return SyslogLevel.DEBUG;
        } else if (level.equals("info")) {
            return SyslogLevel.INFO;
        } else if (level.equals("notice")) {
            return SyslogLevel.NOTICE;
        } else if (level.equals("warning") || level.equals("warn")) {
            return SyslogLevel.WARNING;
        } else if (level.equals("error")) {
            return SyslogLevel.ERROR;
        } else if (level.equals("critical")) {
            return SyslogLevel.CRITICAL;
        } else if (level.equals("alert")) {
            return SyslogLevel.ALERT;
        } else if (level.equals("emergency")) {
            return SyslogLevel.EMERGENCY;
        }
        return SyslogLevel.DEBUG;
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(findMachineHostname());
        System.out.println(mapLogLevel(mapStringToSyslog("error")));
    }
}
