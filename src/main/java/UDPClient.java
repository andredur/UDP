import java.io.*;
import java.net.*;
import java.util.*;
public class UDPClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }
        String ip = args[0];

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(ip);//args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        FileOutputStream fos = new FileOutputStream("test.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        while(!received.equalsIgnoreCase("EOF"))
        {
            packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            received = new String(packet.getData(), 0, packet.getLength());
            byte[] byteArray = received.getBytes();
            if(!received.equalsIgnoreCase("EOF"))
            {
                bos.write(byteArray);
                bos.write('\n');
            }

        }
        bos.flush();
        bos.close();
        System.out.println("file received");

        socket.close();
    }
}
