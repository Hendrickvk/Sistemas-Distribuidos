package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

public class UpdateClient {
    public static void handleUpdateAccount(BufferedReader reader, PrintWriter out, BufferedReader in, String token) throws IOException {
    	
    	if (isTokenInvalid(token)) {
            System.out.println("Por favor, faça login antes de atualizar sua conta.");
            return;
        }
    	   	
        String email = readInput(reader, "Digite o novo endereço de email:");
        String password = readInput(reader, "Digite a nova senha:");
        String name = readInput(reader, "Digite o novo nome:");

        JsonObject requestJson = createRequestJson(token, email, password, name);

        String jsonResponse = sendRequestToServer(requestJson, out, in);
        System.out.println(jsonResponse);
    }
    
    private static boolean isTokenInvalid(String token) {
    	return token == null || token.isEmpty();
    }
    
    private static String readInput(BufferedReader reader, String prompt) throws IOException {
    	System.out.println(prompt);
    	return reader.readLine();
    }
    
    private static JsonObject createRequestJson(String token, String email, String password, String name) {
    	JsonObject requestJson = Utils.createRequest("UPDATE_ACCOUNT_CANDIDATE");
        JsonObject data = new JsonObject();
        data.addProperty("email", email);
        data.addProperty("password", password);
        data.addProperty("name", name);
        requestJson.addProperty("token", token);
        requestJson.add("data", data);
        return requestJson;
    }
    
    private static String sendRequestToServer(JsonObject requestJson, PrintWriter out, BufferedReader in) throws IOException {
    	return Utils.sendRequest(requestJson, out, in);
    }
}