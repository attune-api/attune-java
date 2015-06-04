import java.io.IOException;
import java.util.Properties;

public class TestConfig {

    public static void main(String[] args) {
        String fileName       = "config.properties";
        Properties configFile = new Properties();
        try {
            configFile.load(TestConfig.class.getClassLoader().getResourceAsStream(fileName));
            String endPoint = configFile.getProperty("end_point");
            System.out.println(endPoint);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}