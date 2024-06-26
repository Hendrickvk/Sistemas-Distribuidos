package teste;

import java.io.*;
import java.net.Socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Client {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String serverIP = enterIP(reader);

            Socket socket = new Socket(serverIP, 21234);
            System.out.println("Conectado ao servidor: " + serverIP);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String token = null;
            
            System.out.println("Você é 1 - Candidato ou 2 - Recrutador");
            
            int userType = Integer.parseInt(reader.readLine());

            while (true) {
                displayMenu();

                int option;
                
                try {
                    option = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida. Por favor, digite novamente.");
                    continue;
                }

                switch (option) {
                case 1:
                    if (userType == 1) {
                        token = LoginClient.handleLogin(reader, out, in);
                    } else {
                        token = LoginCompany.handleLoginCompany(reader, out, in);
                    }
                    break;
                case 2:
                    if (userType == 1) {
                        SignupClient.handleSignup(reader, out, in);
                    } else {
                        SignupCompany.handleSignupCompany(reader, out, in);
                    }
                    break;
                case 3:
                	if (userType == 1) {
                		LookUpClient.handleLookUp(in, out, token);
                	} else {
                		LookUpCompany.handleLookUpCompany(in, out, token);
                	}
                	break;
                case 4:
                    if (userType == 1) {
                        UpdateClient.handleUpdateAccount(reader, out, in, token);
                    } else {
                        UpdateCompany.handleUpdateAccountCompany(reader, out, in, token);
                    }
                    break;
                case 5:
                    if (userType == 1) {
                        DeleteClient.handleDeleteAccount(reader, out, in, token);
                    } else {
                        DeleteCompany.handleDeleteAccountCompany(reader, out, in, token);
                    }
                    break;
                case 6:
                    if (userType == 1) {
                        token = LogoutClient.handleLogout(in, out, token);
                    } else {
                        token = LogoutCompany.handleLogoutCompany(in, out, token);
                    }
                    break;
                    case 7:
                        System.out.println("Fechando client...");
                        System.exit(1);
                    case 8:
                        if (userType == 1) {
                            IncludeSkill.handleIncludeSkill(reader, out, in, token);                       
                        } else {
                        	System.out.println("Nao tem acesso");
                        }
                        break;
                    case 9:
                        if (userType == 1) {
                        	LookUpSkill.handleLookUpSkill(reader, in, out, token);
                        } else {
                        	System.out.println("Nao tem acesso");                        
                        	}
                        break;
                    case 10:
                        if (userType == 1) {
                        	LookUpSkillSet.handleLookUpSkillSet(in, out, token);
                        } else {
                        	System.out.println("Nao tem acesso");
                        }
                        break;
                    case 11:
                    	if (userType == 1) {
                    		DeleteSkill.handleDeleteSkill(reader, out, in, token);
                    	} else {
                    		System.out.println("Nao tem acesso");
                    	}
                    	break;
                    case 12:
                        if (userType == 1) {
                        	UpdateSkill.handleUpdateSkill(reader, out, in, token);
                        } else {
                        	 
                        	System.out.println("Nao tem acesso");
                        }
                        break;
                    case 13:
                        if (userType == 1) {
                        	SearchJob.handleSearchJob(reader, out, in, token);
                        } else {
                        	System.out.println("Nao tem acesso");
                        	
                        }
                        break;
                    case 14:
                        if (userType == 1) {
                        	System.out.println("Nao tem acesso");
                        	
                        } else {
                        	LookUpJob.handleLookUpJob(reader, in, out, token);
                        }
                        break;
                    case 15:
                        if (userType == 1) {
                        	System.out.println("Nao tem acesso");
                        	
                        } else {
                        	IncludeJob.handleIncludeJob(reader, out, in, token);
                        }
                        break;
                    case 16:
                        if (userType == 1) {
                        	System.out.println("Nao tem acesso");
                        	
                        } else {
                        	LookUpJobSet.handleLookUpJobSet(in, out, token);
                        }
                        break;
                    case 17:
                        if (userType == 1) {
                        	System.out.println("Nao tem acesso");
                        	
                        } else {
                        	UpdateJob.handleUpdateJob(reader, out, in, token);
                        }
                        break;
                    case 18:
                        if (userType == 1) {
                        	System.out.println("Nao tem acesso");
                        } else {
                        	DeleteJob.handleDeleteJob(reader, out, in, token);
                        }
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, digite novamente.");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }

    public static String enterIP(BufferedReader reader) throws IOException {
        System.out.println("Digite o endereço IP do servidor:");
        return reader.readLine();
    }

    public static void displayMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Login");
        System.out.println("2. Cadastro");
        System.out.println("3. Ver dados");
        System.out.println("4. Atualização de dados");
        System.out.println("5. Exclusão de conta");
        System.out.println("6. Logout");
        System.out.println("7. Sair");
        System.out.println("8. Inserir Habilidade"); 
        System.out.println("9. Olhar Habilidade"); 
        System.out.println("10. Olhar todas Habilidade"); 
        System.out.println("11. Deletar Habilidade"); 
        System.out.println("12. Atualizar Habilidade"); 
        System.out.println("13. Buscar Emprego - Nao Implementado"); 
        System.out.println("14. Ver Emprego");
        System.out.println("15. Inserir Emprego"); 
        System.out.println("16. Ver todos os Empregos");
        System.out.println("17. Atualizar Emprego"); 
       System.out.println("18. Deletar Emprego"); 
    }
    
    
}
