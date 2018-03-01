import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class TestCon {
    private static String host = "192.168.1.202";
    private static int port = 9300;

    public static void main(String[] args) {
        try {
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            System.out.println(client);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
