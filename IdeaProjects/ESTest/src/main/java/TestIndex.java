import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.nio.channels.FileChannel;

public class TestIndex {
    private static String host = "192.168.1.202";
    private static int port = 9300;
    private TransportClient client = null;

    @Before
    public void getClient() throws Exception {
        client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        System.out.println(client);
    }

    @After
    public void close() {
        if (null != client) {
            client.close();
        }
    }

    @Test
    public void testIndex() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "java思想");
        jsonObject.addProperty("price", "122");
        jsonObject.addProperty("address", "黄江路");
        jsonObject.addProperty("date", "2012-11-12");
        IndexResponse response = client.prepareIndex("book", "java", "1").setSource(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称:" + response.getIndex());
        System.out.println("索引类型:" + response.getType());
        System.out.println("索引状态:" + response.status());

    }

    @Test
    public void testGet() {
        GetResponse response = client.prepareGet("book", "java", "1").get();
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void updateGet() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "21212java思想");
        jsonObject.addProperty("price", "2323122");
        jsonObject.addProperty("address", "33黄江路");
        jsonObject.addProperty("date", "2015-11-12");
        UpdateResponse updateResponse = client.prepareUpdate("book", "java", "1").setDoc(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称:" + updateResponse.getIndex());
        System.out.println("索引类型:" + updateResponse.getType());
        System.out.println("索引状态:" + updateResponse.status());

    }

    @Test
    public void deleteGet() {
        DeleteResponse response = client.prepareDelete("book", "java", "1").get();
        System.out.println(response);
    }

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(new File("f://1.jpg"));
        BufferedInputStream bfi = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(new File("f://2.jpg"));
       /* int i = 0;
        byte[] bytes = new byte[1024];
        while ((i = bfi.read(bytes)) != -1) {
            fos.write(bytes, 0, i);
        }
        fos.flush();
        fos.close();
        bfi.close();*/

        FileChannel fci=fis.getChannel();
        FileChannel fco=fos.getChannel();
        fci.transferTo(0, fci.size(), fco);
    }

}
