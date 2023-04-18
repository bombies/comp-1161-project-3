package group.anmv.api;

import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import group.anmv.utils.config.ConfigHandler;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.theokanning.openai.service.OpenAiService.*;

/**
 * This is the server that will be used to communicate to
 * OpenAI's GPT-3.5. It utilizes the service provided by
 * the OpenAI Java SDK.
 *
 * @author Ajani Green
 */
public class GPTService {
    /**
     * The singleton instance of the service
     */
    private static  GPTService INSTANCE;
    /**
     * The OpenAI service provided by the OpenAI SDK
     */
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

    /**
     * Asynchronously fetch a chat completion request from OpenAI
     * using the GPT-3.5-turbo model.
     *
     * @param prompt The prompt to give the AI
     * @param assistant Some helper text to guide the AI's response
     * @return A response based on the prompt and the assistant
     */
    public CompletableFuture<String> getResponse(String prompt, String assistant) {
        final var req = ChatCompletionRequest.builder()
                .messages(
                        List.of(
                                new ChatMessage("user", prompt),
                                new ChatMessage("assistant", assistant)
                        )
                )
                .model("gpt-3.5-turbo")
                .build();

        return CompletableFuture.supplyAsync(() -> service.createChatCompletion(req).getChoices())
                .thenApplyAsync(choices -> !choices.isEmpty() ? choices.get(0).getMessage().getContent() : null);
    }

    /**
     * Get the current instance of the GPT service.
     *
     * @return The GPTService instance
     */
    public static GPTService instance() {
        if (INSTANCE == null)
            INSTANCE = new GPTService();
        return INSTANCE;
    }
}
