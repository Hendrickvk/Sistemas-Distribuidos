package teste;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LookUpClient {
    
	public static void handleLookUp(BufferedReader in, PrintWriter out, String token) throws IOException {
        if (isTokenInvalid(token)) {
            System.out.println("Você precisa estar logado para executar esta operação.");
            return;
        }

        JsonObject requestJson = createRequestJson(token);
        Utils.sendRequest(requestJson, out);

        String jsonResponse = in.readLine();
        System.out.println(jsonResponse);
    }

    private static boolean isTokenInvalid(String token) {
        return token == null || token.isEmpty();
    }

    private static JsonObject createRequestJson(String token) {
        JsonObject requestJson = Utils.createRequest("LOOKUP_ACCOUNT_CANDIDATE");
        requestJson.addProperty("token", token);
        JsonObject data = new JsonObject();
        requestJson.add("data", data);
        return requestJson;
    }
}