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

     MessageListener msl;

    public void setMessageListener(MessageListener msl) {
        
        this.msl = msl;
        
    }
    public JTail(MessageListener msl) {
        
        this.msl = msl;
        
    }
    public  void tail(String file) throws InterruptedException, IOException {
        
        File f = new File(file);
        
        BufferedReader br = new BufferedReader(new FileReader(file.trim()));
        
        br.skip(f.length());
        String line = null;
        
        while (true) {
            
            line = br.readLine();
            
            if (line == null) {


                Thread.sleep(1000);
                
            } else {
                if (msl != null) {
                  
                    msl.onMessage(line);
                }
            }
            
        }
        
        
        
    } //end main
} //end class jtail
