GelfLogger
====================

Log forwarder for Graylog2 in Gelf format
---------------------
GelfLogger is a simple implementation of log forwarder in Java with Gelf format. GelfLogger requires config.yml to be present
in the same directory as the jar file.
The project is set up with Netbeans 7.1.2 and requires the following libraries snakeyaml-1.10 and json-simple-1.1.1.

This version of GelfLogger was designed to work with Weblogic output server log. It can detect inline log level and BEA/ORA error code and forward those to Graylog2 server, automatically.
Furthermore, GelfLogger can continuously run after the log file has rotated, it will detect the new log file automatically. 

### How it works

+ Read Configuration file (config.yml) [ConfigurationLoader]
+ Create new thread for each of logs file that is set in configuration file. [GelfLogger]
+ Each of thread will tail the log file [Gelffer]
+ After a new message is received, it will try to process the message and set up the log level and error code [LogPattern]
+ Format the message in Gelf format [code from gelfj - https://github.com/t0xa/gelfj]
+ the message will be send to the specified graylog2 host [code from gelfj - https://github.com/t0xa/gelfj]

GelfLogger is able to handle more than 200 lines/second for each thread.