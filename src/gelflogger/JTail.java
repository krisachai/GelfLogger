/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gelflogger;

import gelflogger.model.MessageListener;
import java.io.*;
import java.lang.*;
import java.util.*;

class JTail {
    boolean isNeedRun = true;
    MessageListener msl;

    public void setMessageListener(MessageListener msl) {

        this.msl = msl;

    }

    public JTail(MessageListener msl) {

        this.msl = msl;

    }

    public void tail(String file) throws InterruptedException, IOException {
        
        while (isNeedRun) {
            waitUntilFileExist(file);
            try {
                readFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readFile(String file) throws InterruptedException, IOException {
        File f = new File(file);

        FileReader reader = new FileReader(file.trim());
        try {
            BufferedReader br = new BufferedReader(reader);
            br.skip(f.length());
            String line;

            while (f.exists()) {

                f = new File(file);
                line = br.readLine();

                if (line == null) {

                    Thread.sleep(1000);

                } else {
                    if (msl != null) {
                        //Send line to attached interface
                        msl.onMessage(line);
                    }
                }
            }
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void waitUntilFileExist(String file) throws InterruptedException {
        File f = new File(file);
        while (!f.exists()) {
            System.out.println("File doesn't exists");
            Thread.sleep(10000);
        }
        System.out.println("File does exists");
    }

    public static void main(String[] args) throws Exception {
        /*
         * JTail testTail = new JTail(new MessageListener() {
         *
         * @Override public void onMessage(String msg) {
         * System.out.println(msg); } }); testTail.tail("F:\\file.log");
         */
        System.out.println(new Date(System.currentTimeMillis()));
    }
}
