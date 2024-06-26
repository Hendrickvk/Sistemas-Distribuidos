package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

public class SignupClient {

    public static void handleSignup(BufferedReader reader, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("Digite o endereço de email:");
        String email = reader.readLine();

        System.out.println("Digite a senha:");
        String password = reader.readLine();

        System.out.println("Digite o nome:");
        String name = reader.readLine();

        JsonObject requestJson = Utils.createRequest("SIGNUP_CANDIDATE");
        JsonObject data = createDataObject(email, password, name);
        requestJson.add("data", data);

        String jsonResponse = Utils.sendRequest(requestJson, out, in);
        System.out.println(jsonResponse);
    }

    private static JsonObject createDataObject(String email, String password, String name) {
        JsonObject data = new JsonObject();
        data.addProperty("email", email);
        data.addProperty("password", password);
        data.addProperty("name", name);
        return data;
    }
}