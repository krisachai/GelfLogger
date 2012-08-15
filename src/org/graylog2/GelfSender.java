/*
Copyright (c) 2011 Anton Yakimov. 
GELF created by Lennart Koopmann, Aleksey Palazhchenko.

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.graylog2;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class GelfSender {

    private static final int DEFAULT_PORT = 12201;

    private static final int PORT_MIN = 8000;
    private static final int PORT_MAX = 8888;

    private InetAddress host;
    private int port;
    private DatagramSocket socket;

    public GelfSender(String host) throws UnknownHostException, SocketException {
        this(host, DEFAULT_PORT);
    }

    public GelfSender(String host, int port) throws UnknownHostException, SocketException {
        this.host = InetAddress.getByName(host);
        this.port = port;
        this.socket = initiateSocket();
    }

    private DatagramSocket initiateSocket() throws SocketException {
        int port = PORT_MIN;

        DatagramSocket resultingSocket = null;
        boolean binded = false;
        while (!binded) {
            try {
                resultingSocket = new DatagramSocket(port);
                binded = true;
            } catch (SocketException e) {
                port++;

                if (port > PORT_MAX)
                    throw e;
            }
        }
        return resultingSocket;
    }

    public boolean sendMessage(GelfMessage message) {
        return message.isValid() && sendDatagrams(message.toDatagrams());
    }

    public boolean sendDatagrams(List<byte[]> bytesList) {
        for (byte[] bytes : bytesList) {
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, host, port);
            try {
                socket.send(datagramPacket);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public void close() {
        socket.close();
    }
}
