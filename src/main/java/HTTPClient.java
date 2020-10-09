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

    public String getOutput(Command cmd) throws IOException {
        this.cmd = cmd;
        String reply = "";
        connectSocket();
        if (cmd.isGet()) {
            reply = optionGet();
        } else if (cmd.isPost()) {
            reply = optionPost();
        }
        return reply;
    }

    private void connectSocket() throws IOException {
        url = new URL(cmd.getUrl());
        InetAddress address = InetAddress.getByName(url.getHost());
        socket = new Socket(address, PORT);
        send = new PrintWriter(socket.getOutputStream());
        receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    private String optionPost() {
        return "fudu";
    }

    private String optionGet() throws IOException {
        send.println("GET " + url + " HTTP/1.0");
        if (cmd.isH()) {
            HashMap<String, String> headerInfo = cmd.gethArg();
            for (String temp : headerInfo.keySet()) {
                System.out.println(temp + ": " + headerInfo.get(temp));
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

        if (cmd.isV()) {
            return reply.toString();
        } else {
            String[] splitReply = reply.toString().split("\\{", 2);
            return splitReply[1].trim();
        }
    }
}
