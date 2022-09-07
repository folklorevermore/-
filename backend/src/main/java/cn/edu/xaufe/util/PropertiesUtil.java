package cn.edu.xaufe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static String way = null;
    private static String localHost = null;
    private static String port = null;

    public void getProperties() throws Exception{
        Properties properties=new Properties();
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new Exception("File not found!");
        }
        way = (String) properties.get("way");
        localHost = (String) properties.get("localHost");
        port = (String) properties.get("port");
    }

    public static String getWay() {
        return way;
    }
    public static String getLocalHost() {
        return localHost;
    }

    public static String getPort() {
        return port;
    }
}
