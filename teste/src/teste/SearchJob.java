package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SearchJob {
    public static void handleSearchJob(BufferedReader reader, PrintWriter out, BufferedReader in, String token) throws IOException {
        if (token == null || token.isEmpty()) {
            System.out.println("Você precisa estar logado para executar esta operação.");
            return;
        }

        System.out.println("Escolha o tipo de busca:");
        System.out.println("1. Por habilidade");
        System.out.println("2. Por experiência");
        System.out.println("3. Por habilidade e/ou experiência");

        int searchOption;
        try {
            searchOption = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }

        String skill = "";
        String experience = "";
        String filter = "AND";

        switch (searchOption) {
            case 1:
                System.out.println("Digite a skill:");
                skill = reader.readLine();
                break;
            case 2:
                System.out.println("Digite a experiência mínima:");
                experience = reader.readLine();
                break;
            case 3:
                System.out.println("Digite a skill (ou deixe em branco):");
                skill = reader.readLine();
                System.out.println("Digite a experiência mínima (ou deixe em branco):");
                experience = reader.readLine();
                System.out.println("Escolha o filtro (AND/OR):");
                filter = reader.readLine();
                if (!filter.equalsIgnoreCase("AND") && !filter.equalsIgnoreCase("OR")) {
                    System.out.println("Filtro inválido. Usando AND por padrão.");
                    filter = "AND";
                }
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        JsonObject requestJson = Utils.createRequest("SEARCH_JOB");
        requestJson.addProperty("token", token);

        JsonObject dataObject = new JsonObject();
        if (!skill.isEmpty()) {
            JsonArray skillsArray = new JsonArray();
            skillsArray.add(skill);
            dataObject.add("skill", skillsArray);
        }
        if (!experience.isEmpty()) {
            dataObject.addProperty("experience", experience);
        }
        dataObject.addProperty("filter", filter);

        requestJson.add("data", dataObject);

        Utils.sendRequest(requestJson, out);

        String response = in.readLine();
        System.out.println("Server recebeu: " + requestJson);
        System.out.println("Resposta do servidor: " + response);
    }
}