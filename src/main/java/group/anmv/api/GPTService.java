package group.anmv.api;

import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import group.anmv.utils.config.ConfigHandler;

import java.time.Duration;
import java.util.List;
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
                defaultClient(key, Duration.of(30, TimeUnit.SECONDS.toChronoUnit())),
                defaultObjectMapper()
        );

        service = new OpenAiService(retrofit.create(OpenAiApi.class));
    }

    public String getResponse(String prompt, String assistant) {
        final var req = ChatCompletionRequest.builder()
                .messages(
                        List.of(
                                new ChatMessage("user", prompt),
                                new ChatMessage("assistant", assistant)
                        )
                )
                .model("gpt-3.5-turbo")
                .build();
        final var choices = service.createChatCompletion(req).getChoices();
        return !choices.isEmpty() ? choices.get(0).getMessage().getContent() : null;
    }

    public static GPTService instance() {
        if (INSTANCE == null)
            INSTANCE = new GPTService();
        return INSTANCE;
    }
}
