package group.anmv.api;

import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import group.anmv.utils.config.ConfigHandler;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.theokanning.openai.service.OpenAiService.*;

public class GPTService {
    private static  GPTService INSTANCE;
    private final OpenAiService service;

    GPTService() {
        final var key = ConfigHandler.get().getGPTKey();
        if (key == null || key.isBlank())
            throw new NullPointerException("There is no API key for GPT-3");

        final var retrofit = defaultRetrofit(
                defaultClient(key, Duration.of(10, TimeUnit.SECONDS.toChronoUnit())),
                defaultObjectMapper()
        );

        service = new OpenAiService(retrofit.create(OpenAiApi.class));
    }

    public String getResponse(String prompt) {
        final var req = CompletionRequest.builder()
                .prompt(prompt)
                .model("ada")
                .echo(true)
                .build();
        final var choices = service.createCompletion(req).getChoices();
        return !choices.isEmpty() ? choices.get(0).getText() : null;
    }

    public static GPTService instance() {
        if (INSTANCE == null)
            INSTANCE = new GPTService();
        return INSTANCE;
    }
}
