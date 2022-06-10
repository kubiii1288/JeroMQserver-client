package locationjeromq;

import java.util.Scanner;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * @author kubic
 */
public class ClientJeroMQ {

    public static void main(String[] args) {
        System.out.println("Testing JeroMQ server ...");
        ZContext context = new ZContext(1);
        ZMQ.Socket socket = context.createSocket(SocketType.REQ);

        socket.connect("tcp://localhost:7777");
        boolean requireStop = false;
        Scanner sc = new Scanner(System.in);
        String request = "";
        do {
            request = sc.nextLine();
            if (!request.equalsIgnoreCase("done")) {
                System.out.println("sending...");
                socket.send(request.getBytes(), 0);
                byte[] response = socket.recv(0);

                String serverRes = new String(response);

                System.out.println("Server response: " + serverRes);
            } else {
                requireStop = true;
            }
        } while (!requireStop);

        socket.close();
        context.close();
    }

}
