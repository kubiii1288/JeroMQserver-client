package locationjeromq;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * @author kubic
 */
public class ServerJeroMQ {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Location Server start...");
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        ZContext context = new ZContext(1);
        ZMQ.Socket socket = context.createSocket(SocketType.REP);
        socket.bind("tcp://*:7777");

        while (!Thread.currentThread().isInterrupted()) {

            byte[] request = socket.recv(0);

            System.out.println("Received : " + new String(request));

            System.out.println("Hashing...");

            md5.update(request);

            String hashed = new BigInteger(1, md5.digest()).toString(16);

            byte[] response = hashed.getBytes();
            socket.send(response, 0);
        }

        socket.close();
        context.close();
        System.out.println("Server closed...");
    }
}
