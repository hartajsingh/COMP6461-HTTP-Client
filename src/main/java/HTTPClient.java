import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class HTTPClient {
    private Command cmd;
    private URL url;
    private Socket socket;
    final int PORT = 80;
    private PrintWriter send;
    private BufferedReader receive;
    final private int redirectCycles = 3;


    public String getOutput(Command cmd) throws IOException {
        String reply = "";
        this.cmd = cmd;

        int cycle = 0;
        do {

            if (!reply.isEmpty()) {
                cmd.setUrl(extractUrl(reply));
            }
            reply = "";
            connectSocket();
            if (cmd.isGet()) {
                reply = optionGet();
            } else if (cmd.isPost()) {
                reply = optionPost();
            }
        } while (++cycle < redirectCycles && isRedirectResponse(reply));

        if (cmd.isV()) {
            return reply;
        } else {
            String[] splitReply = reply.split("\\{", 2);
            return splitReply[1].trim();
        }
    }

    private String extractUrl(String reply) {
        String url = "";
        if (reply.contains("Location: ")) {
            int i = reply.indexOf("Location: ") + 10;
            while (reply.charAt(i) != '\n') {
                url += reply.charAt(i++);
            }
        }
        return url;
    }

    private boolean isRedirectResponse(String reply) {
        if (reply.charAt(9) == '3') {
            return true;
        }
        return false;
    }

    private void connectSocket() throws IOException {
        url = new URL(cmd.getUrl());
        InetAddress address = InetAddress.getByName(url.getHost());
        socket = new Socket(address, PORT);
        send = new PrintWriter(socket.getOutputStream());
        receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    private String optionPost() throws IOException {
        send.println("POST " + url + " HTTP/1.0");
        if (cmd.isH()) {
            HashMap<String, String> headerInfo = cmd.gethArg();
            for (String temp : headerInfo.keySet()) {
                send.println(temp + ": " + headerInfo.get(temp));
            }
        }

        if(cmd.isD()||cmd.isF()) {
            String arg = cmd.isD()?cmd.getdArg(): cmd.getfArg();
            arg = arg.substring(1, arg.length()-1);
            send.println("Content-Length: " + arg.length());
            send.println("");
            send.println(arg);
        }

        send.flush();

        StringBuffer reply = new StringBuffer();
        while (true) {
            if (receive.ready()) {
                int temp = receive.read();
                while (temp != -1) {
                    reply.append((char) temp);
                    temp = receive.read();
                }
                break;
            }
        }
        return reply.toString();
    }

    private String optionGet() throws IOException {

        send.println("GET " + url + " HTTP/1.0");
        if (cmd.isH()) {
            HashMap<String, String> headerInfo = cmd.gethArg();
            for (String temp : headerInfo.keySet()) {
                send.println(temp + ": " + headerInfo.get(temp));
            }
        }
        send.println("");
        send.flush();

        StringBuffer reply = new StringBuffer();
        while (true) {
            if (receive.ready()) {
                int temp = receive.read();
                while (temp != -1) {
                    reply.append((char) temp);
                    temp = receive.read();
                }
                break;
            }
        }

        return reply.toString();
    }
}
