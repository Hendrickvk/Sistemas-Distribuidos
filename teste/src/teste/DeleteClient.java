package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

public class DeleteClient {
	
    public static void handleDeleteAccount(BufferedReader reader, PrintWriter out, BufferedReader in, String token) throws IOException {
    	
    	if (isTokenInvalid(token)) {
            System.out.println("Por favor, faça login antes de excluir sua conta.");
            return;
        }
        
        JsonObject requestJson = createDeleteAccountRequest(token);
  
        String jsonResponse = sendRequest(requestJson, out, in);
        System.out.println(jsonResponse);
    }
    
    private static boolean isTokenInvalid(String token) {
    	return token == null || token.isEmpty();
    }
    
    private static JsonObject createDeleteAccountRequest(String token) {
    	JsonObject requestJson = Utils.createRequest("DELETE_ACCOUNT_CANDIDATE");
        requestJson.addProperty("token", token);
        JsonObject data = new JsonObject();
        requestJson.add("data", data);
        return requestJson;
    }
    
    private static String sendRequest(JsonObject requestJson, PrintWriter out, BufferedReader in) throws IOException {
    	return Utils.sendRequest(requestJson, out, in);
    }
}