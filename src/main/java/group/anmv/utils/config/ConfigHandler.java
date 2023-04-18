package group.anmv.utils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

/**
 * This class is to be used to fetch the configuration
 * information stored in the application's classpath.
 */
public class ConfigHandler {
    private static Config config;

    static {
        try {
            final var is = ConfigHandler.class.getClassLoader().getResourceAsStream("config.yml");
            final var mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(is, Config.class);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private ConfigHandler() {}

    /**
     * Get the serialized configuration.
     * @return The serialized configuration
     */
    public static Config get() {
        return config;
    }

}
