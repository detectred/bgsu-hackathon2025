import okhttp3.*;
import org.json.*;
import java.io.IOException;

public class AIClient {
    private static final String API_KEY = "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";
    private final OkHttpClient client = new OkHttpClient();

    public String askQuestion(String question) throws IOException {
        // Build JSON request
        JSONObject json = new JSONObject()
                .put("model", "llama-3.1-70b-versatile")  // Groq's Llama model
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("role", "user")
                                .put("content", question)));

        // Create request body
        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        // Build request
        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")  // Groq URL
                .header("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        // Execute request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }

            // Parse response
            JSONObject responseJson = new JSONObject(response.body().string());
            return responseJson.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }
}