import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AIClient {
    private static final String API_KEY = "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";

    public String askQuestion(String question) throws IOException {
        // Build JSON request
        JSONObject json = new JSONObject()
                .put("model", "llama-3.1-70b-versatile")
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("role", "user")
                                .put("content", question)));

        // Create connection
        URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send request
        OutputStream os = connection.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();
        os.close();

        // Read response
        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        // Parse response
        JSONObject responseJson = new JSONObject(response.toString());
        return responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}