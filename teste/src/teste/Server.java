package teste;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server extends Thread{
		private ServerSocket serverSocket;
		private Socket clientSocket;

	    public Server(Socket clientSoc, BufferedWriter writer) {
	        clientSocket = clientSoc;
	        start();
	    }
	    public class JWTValidator {
	        private static final String TOKEN_KEY = "DISTRIBUIDOS";
	        private static final Algorithm algorithm = Algorithm.HMAC256(TOKEN_KEY);
	        private static final JWTVerifier verifier = JWT.require(algorithm).build();

	        public static String generateToken(int id, String role) {
	            return JWT.create()
	                    .withClaim("id", id)
	                    .withClaim("role", role)
	                    .sign(algorithm);
	        }

	        public static int getIdClaim(String token) throws JWTVerificationException {
	            DecodedJWT jwt = verifier.verify(token);
	            return jwt.getClaim("id").asInt();
	        }

	        public static String getRoleClaim(String token) throws JWTVerificationException {
	            DecodedJWT jwt = verifier.verify(token);
	            return jwt.getClaim("role").asString();
	        }
	    }

	    private static class User {
	        private String email;
	        private String password;
	        private String name;
	        private String skill;
	        private int experience;

	    	public User(String email, String password, String name, String skill, int experience) {
	            this.email = email;
	            this.password = password;
	            this.name = name;
	            this.skill = skill;
	            this.experience = experience;
	        }
	        

	    	public String getEmail() {
	            return email;
	        }

	        public void setEmail(String email) {
	            this.email = email;
	        }

	        public String getPassword() {
	            return password;
	        }

	        public void setPassword(String password) {
	            this.password = password;
	        }

	        public String getName() {
	            return name;
	        }

	        public void setName(String name) {
	            this.name = name;
	        }
	        
	        public String getSkill() {
	    		return skill;
	    	}

	    	public void setSkill(String skill) {
	    		this.skill = skill;
	    	}
	    	
	    	public int getExperience() {
	    		return experience;
	    	}

	    	public void setExperience(int experience) {
	    		this.experience = experience;
	    	}
	        
	    }
	    
	    private static class Company {
	        private String email;
	        private String password;
	        private String name;
	        private String industry;
			private String description;
	        private String job;

	    	public Company(String email, String password, String name, String industry, String description, String job) {
	            this.email = email;
	            this.password = password;
	            this.name = name;
	            this.industry = industry;
	            this.description = description;
	            this.job = job;
	        }
	        

	        public String getEmail() {
	            return email;
	        }

	        public void setEmail(String email) {
	            this.email = email;
	        }

	        public String getPassword() {
	            return password;
	        }

	        public void setPassword(String password) {
	            this.password = password;
	        }

	        public String getName() {
	            return name;
	        }

	        public void setName(String name) {
	            this.name = name;
	        }
	        
	        public String getIndustry() {
				return industry;
			}


			public void setIndustry(String industry) {
				this.industry = industry;
			}


			public String getDescription() {
				return description;
			}


			public void setDescription(String description) {
				this.description = description;
			}
	        
			public String getJob() {
				return job;
			}

			public void setJob(String job) {
				this.job = job;
			}
	        
	    }
	    

	    @Override
	    public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                while (true) {
                    String jsonMessage = in.readLine();

                    if (jsonMessage != null) {

                        JsonObject requestJson = Utils.parseJson(jsonMessage);
                        String operation = requestJson.get("operation").getAsString();

                        switch (operation) {
                            case "LOGIN_CANDIDATE":
                                handleLogin(requestJson, out);
                                break;
                            case "LOGIN_RECRUITER":
                                handleLoginCompany(requestJson, out);
                                break;
                            case "SIGNUP_CANDIDATE":
                                handleSignup(requestJson, out);
                                break;
                            case "SIGNUP_RECRUITER":
                                handleSignupCompany(requestJson, out);
                                break;
                            case "UPDATE_ACCOUNT_CANDIDATE":
                                handleUpdate(requestJson, out);
                                break;
                            case "UPDATE_ACCOUNT_RECRUITER":
                                handleUpdateCompany(requestJson, out);
                                break;
                            case "UPDATE_JOB":
                                handleUpdateJob(requestJson, out);
                                break;
                            case "UPDATE_SKILL":
                                handleUpdateSkill(requestJson, out);
                                break;
                            case "DELETE_ACCOUNT_CANDIDATE":
                                handleDelete(requestJson, out);
                                break;
                            case "DELETE_ACCOUNT_RECRUITER":
                                handleDeleteCompany(requestJson, out);
                                break;
                            case "DELETE_JOB":
                                handleDeleteJob(requestJson, out);
                                break;
                            case "DELETE_SKILL":
                                handleDeleteSkill(requestJson, out);
                                break;
                            case "LOGOUT_CANDIDATE":
                                handleLogout(requestJson, out);
                                break;
                            case "LOGOUT_RECRUITER":
                                handleLogoutCompany(requestJson, out);
                                break;
                            case "LOOKUP_ACCOUNT_CANDIDATE":
                                handleLookup(requestJson, out);
                                break;
                            case "LOOKUP_ACCOUNT_RECRUITER":
                                handleLookupCompany(requestJson, out);
                                break;
                            case "LOOKUP_JOB":
                                handleLookupJob(requestJson, out);
                                break;
                            case "LOOKUP_JOBSET":
                                handleLookupJobSet(requestJson, out);
                                break;
                            case "LOOKUP_SKILL":
                                handleLookupSkill(requestJson, out);
                                break;
                            case "LOOKUP_SKILLSET":
                                handleLookupSkillSet(requestJson, out);
                                break;
                            case "INCLUDE_JOB":
                            	handleIncludeJob(requestJson, out);
                                break;
                            case "INCLUDE_SKILL":
                            	handleIncludeSkill(requestJson, out);
                                break;
                            case "SEARCH_JOB":
                                handleSearchJob(requestJson, out);
                                break;
                                
                        }
                    }
                }

            } catch (IOException e) {
                System.err.println("Erro no servidor: " + e.getMessage());
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

	    private void handleLogin(JsonObject requestJson, PrintWriter out) throws IOException {
	        JsonObject data = requestJson.getAsJsonObject("data");
	        String email = data.get("email").getAsString();
	        String password = data.get("password").getAsString();

	        // Verificação de campos vazios
	        if (email.isEmpty() || password.isEmpty()) {
	            JsonObject responseJson = Utils.createResponse("LOGIN_CANDIDATE", "INVALID_LOGIN", "");
	            System.out.println("Enviado para o Server: " + requestJson);
	            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	            out.println(Utils.toJsonString(responseJson));
	            return;
	        }

	        Connection conn = null;
	        PreparedStatement st = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionBD.connectBD();
	            st = conn.prepareStatement("SELECT * FROM candidate WHERE email = ? AND password = ?");
	            st.setString(1, email);
	            st.setString(2, password);

	            rs = st.executeQuery();

	            if (rs.next()) {
	                int idcandidate = rs.getInt("idcandidate");
	                String token = JWTValidator.generateToken(idcandidate, "CANDIDATE");
	                JsonObject responseJson = Utils.createResponse("LOGIN_CANDIDATE", "SUCCESS", token);
	                System.out.println("Enviado para o Server: " + requestJson);
	                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	                out.println(Utils.toJsonString(responseJson));
	            } else {
	                JsonObject responseJson = Utils.createResponse("LOGIN_CANDIDATE", "INVALID_LOGIN", "");
	                System.out.println("Enviado para o Server: " + requestJson);
	                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	                out.println(Utils.toJsonString(responseJson));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JsonObject responseJson = Utils.createResponse("LOGIN_CANDIDATE", "ERROR", "");
	            System.out.println("Enviado para o Server: " + requestJson);
	            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	            out.println(Utils.toJsonString(responseJson));
	        }
	    }

	    private void handleSignup(JsonObject requestJson, PrintWriter out) throws IOException, SQLException {
	    	
	    	JsonObject data = requestJson.getAsJsonObject("data");
	    	String email = data.get("email").getAsString();
	    	String password = data.get("password").getAsString();
	    	String name = data.get("name").getAsString();
	    	
	    	PreparedStatement st;
	    	ResultSet rs;
	    	st = ConnectionBD.connectBD().prepareStatement("SELECT * FROM candidate WHERE email = ?");
	    	st.setString(1, email);
	    	
	    	rs= st.executeQuery();
	    	
	    	if(rs.next()){
	    		JsonObject responseJson = Utils.createResponse("SIGNUP_CANDIDATE", "USER_EXISTS", "");
	    		out.println(Utils.toJsonString(responseJson));
	    		return;
	    		
	    	} else {
	    		
	    		st = ConnectionBD.connectBD().prepareStatement("INSERT INTO candidate (email, password, name) VALUES (?,?,?)");
	    		st.setString(1,email);
	    		st.setString(2,password);
	    		st.setString(3,name);
	    		st.executeUpdate();
	    		
	    	}
	    	
	    	JsonObject responseData = new JsonObject();
	    	JsonObject responseJson = Utils.createResponse("SIGNUP_CANDIDATE", "SUCCESS", "");
	    	responseJson.remove("data");
	    	responseJson.add("data", responseData);
	    	out.println(Utils.toJsonString(responseJson));
	    	System.out.println("Enviando para o server: " + requestJson);
	    	System.out.println("Recebido do server: " + responseJson);
	    }
     
        private void handleLookup(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token")) {
                String token = requestJson.get("token").getAsString();
                
                int userId = JWTValidator.getIdClaim(token);

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    st = conn.prepareStatement("SELECT email, password, name FROM candidate WHERE idcandidate = ?");
                    st.setInt(1, userId);

                    rs = st.executeQuery();

                    if (rs.next()) {
                        JsonObject accountData = new JsonObject();
                        accountData.addProperty("email", rs.getString("email"));
                        accountData.addProperty("password", rs.getString("password"));
                        accountData.addProperty("name", rs.getString("name"));
                        
                        JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_CANDIDATE", "SUCCESS", "");
                        responseJson.add("data", accountData);
                        
                        out.println(Utils.toJsonString(responseJson));
                        System.out.println("Enviando para o server: " + requestJson);
                        System.out.println("Recebido do server: " + responseJson);
                        out.flush();
                    } else {
                        JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_CANDIDATE", "USER_NOT_FOUND", "");
                        out.println(Utils.toJsonString(responseJson));
                        System.out.println("Enviando para o server: " + requestJson);
                        System.out.println("Recebido do server: " + responseJson);
                        out.flush();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_CANDIDATE", "ERROR", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                    out.flush();
                }
            } else {
                JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_CANDIDATE", "INVALID_REQUEST", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
                out.flush();
            }
        }

        private void handleUpdate(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();

            int userId = JWTValidator.getIdClaim(token);

            Connection conn = null;
            PreparedStatement st = null;

            try {
                conn = ConnectionBD.connectBD();

                StringBuilder query = new StringBuilder("UPDATE candidate SET ");
                boolean anyFieldUpdated = false;

                if (data.has("email")) {
                    String newEmail = data.get("email").getAsString();
                    if (!newEmail.isEmpty()) {
                        query.append("email = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("password")) {
                    String newPassword = data.get("password").getAsString();
                    if (!newPassword.isEmpty()) {
                        query.append("password = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("name")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        query.append("name = ?, ");
                        anyFieldUpdated = true;
                    }
                }

                if (!anyFieldUpdated) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_CANDIDATE", "NO_FIELD_UPDATED", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                    return;
                }

                query.setLength(query.length() - 2); // Remove a última vírgula e espaço
                query.append(" WHERE idcandidate = ?");

                st = conn.prepareStatement(query.toString());

                int index = 1;
                if (data.has("email")) {
                    String newEmail = data.get("email").getAsString();
                    if (!newEmail.isEmpty()) {
                        st.setString(index++, newEmail);
                    }
                }
                if (data.has("password")) {
                    String newPassword = data.get("password").getAsString();
                    if (!newPassword.isEmpty()) {
                        st.setString(index++, newPassword);
                    }
                }
                if (data.has("name")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        st.setString(index++, newName);
                    }
                }
                st.setInt(index, userId);

                int rowsUpdated = st.executeUpdate();

                if (rowsUpdated > 0) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_CANDIDATE", "SUCCESS", "");
                    responseJson.remove("data");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                } else {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_CANDIDATE", "USER_NOT_FOUND", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_CANDIDATE", "ERROR", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            }
        }

        private void handleDelete(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();

            Connection conn = null;
            PreparedStatement st = null;

            try {
                int userId = JWTValidator.getIdClaim(token);

                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("DELETE FROM candidate WHERE idcandidate = ?");
                st.setInt(1, userId);

                int rowsDeleted = st.executeUpdate();

                if (rowsDeleted > 0) {
                    JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_CANDIDATE", "SUCCESS", "");
                    responseJson.remove("data");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                } else {
                    JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_CANDIDATE", "USER_NOT_FOUND", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                }
            } catch (JWTVerificationException e) {
                JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_CANDIDATE", "INVALID_TOKEN", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_CANDIDATE", "ERROR", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            }
        }
    
        public void handleLogout(JsonObject requestJson, PrintWriter out) throws IOException {
            String token = requestJson.get("token").getAsString();
            JsonObject accountData = new JsonObject();
            JsonObject responseJson = Utils.createResponse("LOGOUT_CANDIDATE", "SUCCESS", accountData);

            out.println(Utils.toJsonString(responseJson));
            System.out.println("Enviando para o server: " + requestJson);
            System.out.println("Recebido do server: " + responseJson);
            out.flush();
        }
  
        private void handleSignupCompany(JsonObject requestJson, PrintWriter out) throws IOException, SQLException {
            // Extrair dados do requestJson
            JsonObject data = requestJson.getAsJsonObject("data");
            String email = data.get("email").getAsString();
            String password = data.get("password").getAsString();
            String name = data.get("name").getAsString();
            String industry = data.get("industry").getAsString();
            String description = data.get("description").getAsString();

            PreparedStatement st;
	    	ResultSet rs;
	    	st = ConnectionBD.connectBD().prepareStatement("SELECT * FROM recruiter WHERE email = ?");
	    	st.setString(1, email);
	    	
	    	rs= st.executeQuery();
	    	
	    	if(rs.next()){
	    		JsonObject responseJson = Utils.createResponse("SIGNUP_RECRUITER", "USER_EXISTS", "");
	    		out.println(Utils.toJsonString(responseJson));
	    		return;
	    		
	    	} else {
	    		
	    		st = ConnectionBD.connectBD().prepareStatement("INSERT INTO recruiter (email, password, name, industry, description) VALUES (?,?,?,?,?)");
	    		st.setString(1,email);
	    		st.setString(2,password);
	    		st.setString(3,name);
	    		st.setString(4,industry);
	    		st.setString(5,description);
	    		st.executeUpdate();
	    		
	    	}
	    	
	    	JsonObject responseData = new JsonObject();
	    	JsonObject responseJson = Utils.createResponse("SIGNUP_RECRUITER", "SUCCESS", "");
	    	responseJson.remove("data");
	    	responseJson.add("data", responseData);
	    	out.println(Utils.toJsonString(responseJson));
	    	System.out.println("Enviando para o server: " + requestJson);
	    	System.out.println("Recebido do server: " + responseJson);
	    }
 
        private void handleLoginCompany(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String email = data.get("email").getAsString();
            String password = data.get("password").getAsString();

	        if (email.isEmpty() || password.isEmpty()) {
	            JsonObject responseJson = Utils.createResponse("LOGIN_RECRUITER", "INVALID_LOGIN", "");
	            System.out.println("Enviado para o Server: " + requestJson);
	            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	            out.println(Utils.toJsonString(responseJson));
	            return;
	        }
	        Connection conn = null;
	        PreparedStatement st = null;
	        ResultSet rs = null;

            try {
            	conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("SELECT * FROM recruiter WHERE email = ? AND password = ?");
                st.setString(1, email);
                st.setString(2, password);
                rs = st.executeQuery();

                if (rs.next()) {
                	int idrecruiter = rs.getInt("idrecruiter");
                    String token = JWTValidator.generateToken(idrecruiter, "RECRUITER");
                    JsonObject responseJson = Utils.createResponse("LOGIN_RECRUITER", "SUCCESS", token);
                    System.out.println("Enviado para o Server: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }else {
                    	JsonObject responseJson = Utils.createResponse("LOGIN_RECRUITER", "INVALID_LOGIN", "");
    	                System.out.println("Enviado para o Server: " + requestJson);
    	                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
    	                out.println(Utils.toJsonString(responseJson));
                    }
            } catch (SQLException e) {
                e.printStackTrace();
	            JsonObject responseJson = Utils.createResponse("LOGIN_RECRUITER", "ERROR", "");
	            System.out.println("Enviado para o Server: " + requestJson);
	            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
	            out.println(Utils.toJsonString(responseJson));
            }
        }
        
        public void handleLogoutCompany(JsonObject requestJson, PrintWriter out) throws IOException {
      	  
            String token = requestJson.get("token").getAsString();
            JsonObject accountData = new JsonObject();
            JsonObject responseJson = Utils.createResponse("LOGOUT_RECRUITER", "SUCCESS", accountData);

            out.println(Utils.toJsonString(responseJson));
            System.out.println("Enviando para o server: " + requestJson);
            System.out.println("Recebido do server: " + responseJson);
            out.flush();
        }
        
        private void handleLookupCompany(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token")) {
                String token = requestJson.get("token").getAsString();
                
                int recruiterId = JWTValidator.getIdClaim(token);
                
                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;
                
                try {
                	conn = ConnectionBD.connectBD();
                	st = conn.prepareStatement("SELECT email, password, name, industry, description FROM recruiter WHERE idrecruiter = ?");
                	st.setInt(1, recruiterId);
                	
                	rs = st.executeQuery();

                    if (rs.next()) {
                        JsonObject accountData = new JsonObject();
                        accountData.addProperty("email", rs.getString("email"));
                        accountData.addProperty("password", rs.getString("password"));
                        accountData.addProperty("name", rs.getString("name"));
                        accountData.addProperty("industry", rs.getString("industry"));
                        accountData.addProperty("description", rs.getString("description"));

                        JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_RECRUITER", "SUCCESS", "");
                        responseJson.add("data", accountData);

                        out.println(Utils.toJsonString(responseJson));
                        System.out.println("Enviando para o server: " + requestJson);
                        System.out.println("Recebido do server: " + responseJson);
                        out.flush();
                    } else {
                        JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_RECRUITER", "USER_NOT_FOUND", "");
                        out.println(Utils.toJsonString(responseJson));
                        System.out.println("Enviando para o server: " + requestJson);
                        System.out.println("Recebido do server: " + responseJson);
                        out.flush();
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_RECRUITER", "ERROR", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                    out.flush();
                }
                } else {
                    JsonObject responseJson = Utils.createResponse("LOOKUP_ACCOUNT_RECRUITER", "INVALID_REQUEST", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                    out.flush();
                }
            }
        
        private void handleUpdateCompany(JsonObject requestJson, PrintWriter out) throws IOException {
	        JsonObject data = requestJson.getAsJsonObject("data");
	        String token = requestJson.get("token").getAsString(); 

	        int recruiterId = JWTValidator.getIdClaim(token);
	        
            Connection conn = null;
            PreparedStatement st = null;
            
	        try {
	            conn = ConnectionBD.connectBD();
	        	
	            StringBuilder query = new StringBuilder("UPDATE recruiter SET ");
	            boolean anyFieldUpdated = false;
	            
                if (data.has("email")) {
                    String newEmail = data.get("email").getAsString();
                    if (!newEmail.isEmpty()) {
                        query.append("email = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("password")) {
                    String newPassword = data.get("password").getAsString();
                    if (!newPassword.isEmpty()) {
                        query.append("password = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("name")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        query.append("name = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("industry")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        query.append("name = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                if (data.has("description")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        query.append("name = ?, ");
                        anyFieldUpdated = true;
                    }
                }
                
                if (!anyFieldUpdated) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_RECRUITER", "NO_FIELD_UPDATED", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                    return;
                }
                query.setLength(query.length() - 2); // Remove a última vírgula e espaço
                query.append(" WHERE idrecruiter = ?");
                
                st = conn.prepareStatement(query.toString());
                
                int index = 1;
                
                if (data.has("email")) {
                    String newEmail = data.get("email").getAsString();
                    if (!newEmail.isEmpty()) {
                        st.setString(index++, newEmail);
                    }
                }
                if (data.has("password")) {
                    String newPassword = data.get("password").getAsString();
                    if (!newPassword.isEmpty()) {
                        st.setString(index++, newPassword);
                    }
                }
                if (data.has("name")) {
                    String newName = data.get("name").getAsString();
                    if (!newName.isEmpty()) {
                        st.setString(index++, newName);
                    }
                }
                if (data.has("industry")) {
                    String newIndustry = data.get("industry").getAsString();
                    if (!newIndustry.isEmpty()) {
                        st.setString(index++, newIndustry);
                    }
                }
                if (data.has("description")) {
                    String newDescription = data.get("description").getAsString();
                    if (!newDescription.isEmpty()) {
                        st.setString(index++, newDescription);
                    }
                }
                st.setInt(index, recruiterId);

                int rowsUpdated = st.executeUpdate();

                if (rowsUpdated > 0) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_RECRUITER", "SUCCESS", "");
                    responseJson.remove("data");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                } else {
                    JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_RECRUITER", "USER_NOT_FOUND", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("UPDATE_ACCOUNT_RECRUITER", "ERROR", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            }
        }
        
        private void handleDeleteCompany(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();
            
            Connection conn = null;
            PreparedStatement st = null;

            try {
                int recruiterId = JWTValidator.getIdClaim(token);
                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("DELETE FROM recruiter WHERE idrecruiter = ?");
                st.setInt(1, recruiterId);

                int rowsDeleted = st.executeUpdate();

                if (rowsDeleted > 0) {
                    JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_RECRUITER", "SUCCESS", "");
                    responseJson.remove("data");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                } else {
                    JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_RECRUITER", "USER_NOT_FOUND", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Enviando para o server: " + requestJson);
                    System.out.println("Recebido do server: " + responseJson);
                }
            } catch (JWTVerificationException e) {
                JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_RECRUITER", "INVALID_TOKEN", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("DELETE_ACCOUNT_RECRUITER", "ERROR", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Enviando para o server: " + requestJson);
                System.out.println("Recebido do server: " + responseJson);
            }
        }
        
        //check
        private void handleDeleteJob(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();

            Connection conn = null;
            PreparedStatement st = null;

            try {
                int idrecruiter = JWTValidator.getIdClaim(token);
                int idjob = data.get("id").getAsInt();

                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("DELETE FROM job_dataset WHERE idjob_dataset = ? AND idrecruiter = ?");
                st.setInt(1, idjob);
                st.setInt(2, idrecruiter);

                int rowsDeleted = st.executeUpdate();

                JsonObject responseJson;
                if (rowsDeleted > 0) {
                    responseJson = Utils.createResponse("DELETE_JOB", "SUCCESS", "");
                } else {
                    responseJson = Utils.createResponse("DELETE_JOB", "JOB_NOT_FOUND", "");
                }
                responseJson.remove("data");
                responseJson.add("data", new JsonObject());

                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                out.println(Utils.toJsonString(responseJson));
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("DELETE_JOB", "ERROR", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        //check
        private void handleDeleteSkill(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();

            Connection conn = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            try {
                int userId = JWTValidator.getIdClaim(token);
                String skill = data.get("skill").getAsString();

                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, skill);

                rs = st.executeQuery();

                if (rs.next()) {
                    int idSkillsDataset = rs.getInt("idskills_dataset");

                    st = conn.prepareStatement("SELECT * FROM skills WHERE idcandidate = ? AND idskills_dataset = ?");
                    st.setInt(1, userId);
                    st.setInt(2, idSkillsDataset);
                    rs = st.executeQuery();

                    if (rs.next()) {
                        st = conn.prepareStatement("DELETE FROM skills WHERE idcandidate = ? AND idskills_dataset = ?");
                        st.setInt(1, userId);
                        st.setInt(2, idSkillsDataset);
                        st.executeUpdate();

                        JsonObject responseJson = Utils.createResponse("DELETE_SKILL", "SUCCESS", "");
                        responseJson.remove("data");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    } else {
                        JsonObject responseJson = Utils.createResponse("DELETE_SKILL", "SKILL_NOT_FOUND", "");
                        responseJson.remove("data");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    }
                } else {
                    JsonObject responseJson = Utils.createResponse("DELETE_SKILL", "SKILL_NOT_FOUND", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }
            } catch (JWTVerificationException e) {
                JsonObject responseJson = Utils.createResponse("DELETE_SKILL", "INVALID_TOKEN", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("DELETE_SKILL", "ERROR", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        //check
        private void handleIncludeJob(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();
            int idrecruiter = JWTValidator.getIdClaim(token);
            String skill = data.get("skill").getAsString();
            int experience = data.get("experience").getAsInt();

            Connection conn = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            try {
                if (skill.isEmpty() || data.get("experience").getAsString().isEmpty()) {
                    JsonObject responseJson = Utils.createResponse("INCLUDE_JOB", "INVALID_FIELD", "");
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                    return;
                }

                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, skill);
                rs = st.executeQuery();

                if (rs.next()) {
                    int idskill = rs.getInt("idskills_dataset");
                    st = conn.prepareStatement("INSERT INTO job_dataset (experience, skill, idrecruiter) VALUES (?, ?, ?)");
                    st.setInt(1, experience);
                    st.setInt(2, idskill);
                    st.setInt(3, idrecruiter);
                    st.executeUpdate();

                    JsonObject responseJson = Utils.createResponse("INCLUDE_JOB", "SUCCESS", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                } else {
                    JsonObject responseJson = Utils.createResponse("INCLUDE_JOB", "SKILL_NOT_FOUND", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("INCLUDE_JOB", "ERROR", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }


        //check
        private void handleIncludeSkill(JsonObject requestJson, PrintWriter out) throws IOException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();
            int userId = JWTValidator.getIdClaim(token);
            String skill = data.get("skill").getAsString();
            int experience = data.get("experience").getAsInt();

            Connection conn = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            try {
                if (skill.isEmpty() || data.get("experience").getAsString().isEmpty()) {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "INCLUDE_SKILL");
                    responseJson.addProperty("status", "INVALID_FIELD");
                    responseJson.add("data", new JsonObject());

                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                    return;
                }

                conn = ConnectionBD.connectBD();
                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, skill);
                rs = st.executeQuery();

                if (rs.next()) {
                    int idSkillDataset = rs.getInt("idskills_dataset");

                    st = conn.prepareStatement("SELECT * FROM skills WHERE idcandidate = ? AND idskills_dataset = ?");
                    st.setInt(1, userId);
                    st.setInt(2, idSkillDataset);
                    rs = st.executeQuery();

                    if (rs.next()) {
                        JsonObject responseJson = new JsonObject();
                        responseJson.addProperty("operation", "INCLUDE_SKILL");
                        responseJson.addProperty("status", "SKILL_EXISTS");
                        responseJson.add("data", new JsonObject());

                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    } else {
                        st = conn.prepareStatement("INSERT INTO skills (idcandidate, idskills_dataset, experience) VALUES (?, ?, ?)");
                        st.setInt(1, userId);
                        st.setInt(2, idSkillDataset);
                        st.setInt(3, experience);
                        st.executeUpdate();

                        JsonObject responseJson = new JsonObject();
                        responseJson.addProperty("operation", "INCLUDE_SKILL");
                        responseJson.addProperty("status", "SUCCESS");
                        responseJson.add("data", new JsonObject());

                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    }
                } else {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "INCLUDE_SKILL");
                    responseJson.addProperty("status", "SKILL_NOT_EXISTS");
                    responseJson.add("data", new JsonObject());

                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("operation", "INCLUDE_SKILL");
                responseJson.addProperty("status", "ERROR");
                responseJson.add("data", new JsonObject());

                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        
        //check
        private void handleLookupJob(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token")) {
                String token = requestJson.get("token").getAsString();
                int idrecruiter = JWTValidator.getIdClaim(token);
                int idjob = requestJson.getAsJsonObject("data").get("id").getAsInt();

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    st = conn.prepareStatement("SELECT * FROM job_dataset WHERE idrecruiter = ? AND idjob_dataset = ?");
                    st.setInt(1, idrecruiter);
                    st.setInt(2, idjob);
                    rs = st.executeQuery();

                    if (rs.next()) {
                        int experience = rs.getInt("experience");
                        int idskill = rs.getInt("skill");

                        PreparedStatement stSkill = conn.prepareStatement("SELECT * FROM skills_dataset WHERE idskills_dataset = ?");
                        stSkill.setInt(1, idskill);
                        ResultSet rsSkill = stSkill.executeQuery();

                        if (rsSkill.next()) {
                            String skill = rsSkill.getString("skills_available");

                            JsonObject data = new JsonObject();
                            data.addProperty("experience", experience);
                            data.addProperty("skill", skill);

                            JsonObject responseJson = Utils.createResponse("LOOKUP_JOB", "SUCCESS", "");
                            responseJson.add("data", data);
                            System.out.println("Server recebeu: " + requestJson);
                            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                            out.println(Utils.toJsonString(responseJson));
                        } else {
                            JsonObject responseJson = Utils.createResponse("LOOKUP_JOB", "JOB_NOT_FOUND", "");
                            responseJson.add("data", new JsonObject());
                            System.out.println("Server recebeu: " + requestJson);
                            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                            out.println(Utils.toJsonString(responseJson));
                        }

                        rsSkill.close();
                        stSkill.close();
                    } else {
                        JsonObject responseJson = Utils.createResponse("LOOKUP_JOB", "JOB_NOT_FOUND", "");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = Utils.createResponse("LOOKUP_JOB", "ERROR", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                }
            } else {
                JsonObject responseJson = Utils.createResponse("LOOKUP_JOB", "INVALID_REQUEST", "");
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        //check
        private void handleLookupSkill(JsonObject requestJson, PrintWriter out) throws IOException {
            
        	if (requestJson != null && requestJson.has("token")) {
                String token = requestJson.get("token").getAsString();
                int userId = JWTValidator.getIdClaim(token);
                JsonObject data = requestJson.getAsJsonObject("data");
                String skill = data.get("skill").getAsString();

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                    st.setString(1, skill);
                    rs = st.executeQuery();

                    if (rs.next()) {
                        int idSkillDataset = rs.getInt("idskills_dataset");

                        st = conn.prepareStatement("SELECT * FROM skills WHERE idcandidate = ? AND idskills_dataset = ?");
                        st.setInt(1, userId);
                        st.setInt(2, idSkillDataset);
                        rs = st.executeQuery();

                        if (rs.next()) {
                            int experience = rs.getInt("experience");
                            data.addProperty("experience", experience);
                            JsonObject responseJson = Utils.createResponse("LOOKUP_SKILL", "SUCCESS", "");
                            responseJson.add("data", data);
                            System.out.println("Server recebeu: " + requestJson);
                            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                            out.println(Utils.toJsonString(responseJson));
                        } else {
                            JsonObject responseJson = Utils.createResponse("LOOKUP_SKILL", "SKILL_NOT_FOUND", "");
                            responseJson.add("data", new JsonObject());
                            System.out.println("Server recebeu: " + requestJson);
                            System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                            out.println(Utils.toJsonString(responseJson));
                        }
                    } else {
                        JsonObject responseJson = Utils.createResponse("LOOKUP_SKILL", "SKILL_NOT_FOUND", "");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = Utils.createResponse("LOOKUP_SKILL", "ERROR", "");
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                }
            } else {
                JsonObject responseJson = Utils.createResponse("LOOKUP_SKILL", "INVALID_REQUEST", "");
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                out.println(Utils.toJsonString(responseJson));
            }
        }
        
        //check
        private void handleLookupJobSet(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token") && requestJson.has("data")) {
                String token = requestJson.get("token").getAsString();
                int recruiterId = JWTValidator.getIdClaim(token);

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    String query = "SELECT jd.idjob_dataset, jd.experience, sd.skills_available " +
                                   "FROM job_dataset jd " +
                                   "JOIN skills_dataset sd ON jd.skill = sd.idskills_dataset " +
                                   "WHERE jd.idrecruiter = ?";
                    st = conn.prepareStatement(query);
                    st.setInt(1, recruiterId);

                    rs = st.executeQuery();

                    ArrayList<JsonObject> jobList = new ArrayList<>();

                    while (rs.next()) {
                        int experience = rs.getInt("experience");
                        String skillName = rs.getString("skills_available");
                        int idjob = rs.getInt("idjob_dataset");

                        JsonObject jobJson = new JsonObject();
                        jobJson.addProperty("skill", skillName);
                        jobJson.addProperty("experience", experience);
                        jobJson.addProperty("id", idjob);
                        jobList.add(jobJson);
                    }

                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "LOOKUP_JOBSET");
                    responseJson.addProperty("status", "SUCCESS");

                    JsonObject responseData = new JsonObject();
                    responseData.addProperty("jobset_size", jobList.size());

                    JsonArray jobSetArray = new JsonArray();
                    for (JsonObject jobJson : jobList) {
                        jobSetArray.add(jobJson);
                    }
                    responseData.add("jobset", jobSetArray);

                    responseJson.add("data", responseData);

                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "LOOKUP_JOBSET");
                    responseJson.addProperty("status", "ERROR");
                    responseJson.add("data", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                }
            } else {
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("operation", "LOOKUP_JOBSET");
                responseJson.addProperty("status", "INVALID_REQUEST");
                responseJson.add("data", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }


        
        //check
        private void handleLookupSkillSet(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token")) {
                String token = requestJson.get("token").getAsString();
                int userId = JWTValidator.getIdClaim(token);

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    String query = "SELECT s.experience, sd.skills_available " +
                                   "FROM skills s " +
                                   "JOIN skills_dataset sd ON s.idskills_dataset = sd.idskills_dataset " +
                                   "WHERE s.idcandidate = ?";
                    st = conn.prepareStatement(query);
                    st.setInt(1, userId);

                    rs = st.executeQuery();

                    ArrayList<JsonObject> skillList = new ArrayList<>();

                    while (rs.next()) {
                        int experience = rs.getInt("experience");
                        String skillName = rs.getString("skills_available");

                        JsonObject skillJson = new JsonObject();
                        skillJson.addProperty("skill", skillName);
                        skillJson.addProperty("experience", experience);

                        skillList.add(skillJson);
                    }

                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "LOOKUP_SKILLSET");
                    responseJson.addProperty("status", "SUCCESS");

                    JsonObject responseData = new JsonObject();
                    responseData.addProperty("skillset_size", skillList.size());

                    JsonArray skillsArray = new JsonArray();
                    for (JsonObject skillJson : skillList) {
                        skillsArray.add(skillJson);
                    }
                    responseData.add("skillset", skillsArray);

                    responseJson.add("data", responseData);

                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "LOOKUP_SKILLSET");
                    responseJson.addProperty("status", "ERROR");
                    responseJson.add("data", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                }
            } else {
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("operation", "LOOKUP_SKILLSET");
                responseJson.addProperty("status", "INVALID_REQUEST");
                responseJson.add("data", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        
        private void handleSearchJob(JsonObject requestJson, PrintWriter out) throws IOException {
            if (requestJson != null && requestJson.has("token") && requestJson.has("data")) {
                String token = requestJson.get("token").getAsString();
                int idrecruiter = JWTValidator.getIdClaim(token);
                JsonObject dataObject = requestJson.getAsJsonObject("data");
                JsonArray skillsArray = dataObject.has("skill") ? dataObject.getAsJsonArray("skill") : new JsonArray();
                String experience = dataObject.has("experience") ? dataObject.get("experience").getAsString() : "";
                String filter = dataObject.has("filter") ? dataObject.get("filter").getAsString() : "AND";

                List<String> skillsList = new ArrayList<>();
                skillsArray.forEach(item -> skillsList.add(item.getAsString().toLowerCase()));

                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;

                try {
                    conn = ConnectionBD.connectBD();
                    StringBuilder queryBuilder = new StringBuilder("SELECT * FROM job_dataset WHERE idrecruiter = ?");

                    if (!skillsList.isEmpty()) {
                        queryBuilder.append(" AND (");
                        for (int i = 0; i < skillsList.size(); i++) {
                            queryBuilder.append("LOWER(skill) LIKE ?");
                            if (i < skillsList.size() - 1) {
                                queryBuilder.append(" ").append(filter).append(" ");
                            }
                        }
                        queryBuilder.append(")");
                    }
                    if (!experience.isEmpty()) {
                        queryBuilder.append(" AND experience <= ?");
                    }

                    System.out.println("Constructed Query: " + queryBuilder.toString());

                    st = conn.prepareStatement(queryBuilder.toString());
                    st.setInt(1, idrecruiter);
                    int parameterIndex = 2;
                    for (String skill : skillsList) {
                        st.setString(parameterIndex++, "%" + skill + "%");
                    }
                    if (!experience.isEmpty()) {
                        st.setString(parameterIndex, experience);
                    }

                    rs = st.executeQuery();
                    List<JsonObject> jobList = new ArrayList<>();

                    while (rs.next()) {
                        JsonObject jobJson = new JsonObject();
                        jobJson.addProperty("id", rs.getInt("idjob_dataset"));
                        jobJson.addProperty("skill", rs.getString("skill"));
                        jobJson.addProperty("experience", rs.getString("experience"));
                        jobList.add(jobJson);
                    }

                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "SEARCH_JOB");
                    JsonObject data = new JsonObject();
                    JsonArray jobSetArray = new JsonArray();

                    for (JsonObject jobJson : jobList) {
                        jobSetArray.add(jobJson);
                    }

                    data.addProperty("jobset_size", jobList.size());
                    data.add("jobset", jobSetArray);
                    responseJson.addProperty("status", "SUCCESS");
                    responseJson.add("data", data);

                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                } catch (SQLException e) {
                    e.printStackTrace();
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("operation", "SEARCH_JOB");
                    responseJson.addProperty("status", "ERROR");
                    responseJson.add("data", new JsonObject());
                    out.println(Utils.toJsonString(responseJson));
                    System.out.println("Erro ao consultar o banco de dados: " + e.getMessage());
                }
            } else {
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("operation", "SEARCH_JOB");
                responseJson.addProperty("status", "INVALID_REQUEST");
                responseJson.add("data", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Request inválido.");
            }
        }




        
        //check
        private void handleUpdateJob(JsonObject requestJson, PrintWriter out) throws IOException, SQLException {
            JsonObject data = requestJson.getAsJsonObject("data");
            String token = requestJson.get("token").getAsString();
            int idrecruiter = JWTValidator.getIdClaim(token);
            String skill = data.get("skill").getAsString();
            int experience = data.get("experience").getAsInt();
            int idjob = data.get("id").getAsInt();

            Connection conn = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            try {
                conn = ConnectionBD.connectBD();
                
                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, skill);
                rs = st.executeQuery();

                if (rs.next()) {
                    int idnewSkill = rs.getInt("idskills_dataset");

                    st = conn.prepareStatement("SELECT * FROM job_dataset WHERE idjob_dataset = ? AND idrecruiter = ?");
                    st.setInt(1, idjob);
                    st.setInt(2, idrecruiter);
                    rs = st.executeQuery();

                    if (rs.next()) {
                        st = conn.prepareStatement("UPDATE job_dataset SET skill = ?, experience = ? WHERE idjob_dataset = ? AND idrecruiter = ?");
                        st.setInt(1, idnewSkill);
                        st.setInt(2, experience);
                        st.setInt(3, idjob);
                        st.setInt(4, idrecruiter);
                        st.executeUpdate();

                        JsonObject responseJson = Utils.createResponse("UPDATE_JOB", "SUCCESS", "");
                        responseJson.remove("data");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    } else {
                        JsonObject responseJson = Utils.createResponse("UPDATE_JOB", "JOB_NOT_FOUND", "");
                        responseJson.remove("data");
                        responseJson.add("data", new JsonObject());
                        System.out.println("Server recebeu: " + requestJson);
                        System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                        out.println(Utils.toJsonString(responseJson));
                    }
                } else {
                    JsonObject responseJson = Utils.createResponse("UPDATE_JOB", "SKILL_NOT_FOUND", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("UPDATE_JOB", "ERROR", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }

        //check        
        private void handleUpdateSkill(JsonObject requestJson, PrintWriter out) throws IOException, SQLException {
            JsonObject data = requestJson.getAsJsonObject("data");

            if ((data.get("skill").getAsString() == null || data.get("skill").getAsString().isEmpty()) ||
                (data.get("newSkill").getAsString() == null || data.get("newSkill").getAsString().isEmpty()) ||
                (data.get("experience").getAsString() == null || data.get("experience").getAsString().isEmpty())) {
                JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "INVALID_FIELD", "");
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                out.println(Utils.toJsonString(responseJson));
                return;
            }

            String experienceStr = data.get("experience").getAsString();
            int experience;
            try {
                experience = Integer.parseInt(experienceStr);
            } catch (NumberFormatException e) {
                JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "INVALID_EXPERIENCE", "");
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                out.println(Utils.toJsonString(responseJson));
                return;
            }

            String token = requestJson.get("token").getAsString();
            int userId = JWTValidator.getIdClaim(token);
            String skill = data.get("skill").getAsString();
            String newSkill = data.get("newSkill").getAsString();

            Connection conn = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            try {
                conn = ConnectionBD.connectBD();

                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, skill);
                rs = st.executeQuery();

                if (!rs.next()) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "SKILL_NOT_FOUND", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                    return;
                }

                int idSkill = rs.getInt("idskills_dataset");
                rs.close();
                st.close();

                st = conn.prepareStatement("SELECT * FROM skills_dataset WHERE skills_available = ?");
                st.setString(1, newSkill);
                rs = st.executeQuery();

                if (!rs.next()) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "SKILL_NOT_FOUND", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                    return;
                }

                int idNewSkill = rs.getInt("idskills_dataset");
                rs.close();
                st.close();

                st = conn.prepareStatement("SELECT * FROM skills WHERE idskills_dataset = ? AND idcandidate = ?");
                st.setInt(1, idNewSkill);
                st.setInt(2, userId);
                rs = st.executeQuery();

                if (rs.next()) {
                    JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "SKILL_EXISTS", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                } else {
                    st = conn.prepareStatement("UPDATE skills SET idskills_dataset = ?, experience = ? WHERE idcandidate = ? AND idskills_dataset = ?");
                    st.setInt(1, idNewSkill);
                    st.setInt(2, experience);
                    st.setInt(3, userId);
                    st.setInt(4, idSkill);
                    st.executeUpdate();

                    JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "SUCCESS", "");
                    responseJson.remove("data");
                    responseJson.add("data", new JsonObject());
                    System.out.println("Server recebeu: " + requestJson);
                    System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
                    out.println(Utils.toJsonString(responseJson));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JsonObject responseJson = Utils.createResponse("UPDATE_SKILL", "ERROR", new JsonObject());
                out.println(Utils.toJsonString(responseJson));
                System.out.println("Server recebeu: " + requestJson);
                System.out.println("Server enviou: " + Utils.toJsonString(responseJson));
            }
        }




              
        
	    public static void main(String[] args) {
	        int serverPort = 21234;

	        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("serverTest_log.txt", true));
	             ServerSocket serverSocket = new ServerSocket(serverPort)) {

	            System.out.println("Servidor iniciado na porta " + serverPort);

	            while (true) {
	                System.out.println("Aguardando conexão...");
	                Socket clientSocket = serverSocket.accept();
	                System.out.println("Cliente conectado: " + clientSocket);	         
	                
	                new Server(clientSocket, fileWriter);
	            }
	        } catch (IOException e) {
	            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
	        }
	    }
	
}