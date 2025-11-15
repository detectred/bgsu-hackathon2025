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
                .put("model", "llama-3.3-70b-versatile")
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

        // Check response code
        int responseCode = connection.getResponseCode();

        // Read response (or error)
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            // Read error message
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            System.err.println("Error Code: " + responseCode);
        }

        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        // If there was an error, print it and throw exception
        if (responseCode != 200) {
            System.err.println("Error Response: " + response.toString());
            throw new IOException("API Error: " + response.toString());
        }

        // Parse response
        JSONObject responseJson = new JSONObject(response.toString());
        return responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}