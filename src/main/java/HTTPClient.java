import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HTTPClient {
    public HTTPClient(Command cmd) {
    }

    public static void main(String[] args) throws IOException {
        final int PORT = 80;

        // create and connect socket
        InetAddress address = InetAddress.getByName("httpbin.org");
        Socket socket = new Socket(address, PORT);

        // setup reader and writer
        PrintWriter send = new PrintWriter(socket.getOutputStream());
        BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // send HTTP GET request to server
        send.println("GET /status/418 HTTP/1.0");
        send.println("Host: httpbin.org");
        send.println("");
        send.flush();

        // receive reply from server
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
        System.out.println(reply);

        socket.close();
    }

}
