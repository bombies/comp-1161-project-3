package group.anmv.utils.config;

/**
 * The model for the application's configuration
 */
public class Config {
    public final String gpt_api_key;

    protected Config() {
        this.gpt_api_key = null;
    }

    protected Config(String gpt_api_key) {
        this.gpt_api_key = gpt_api_key;
    }

    public String getGPTKey() {
        return gpt_api_key;
    }
}
