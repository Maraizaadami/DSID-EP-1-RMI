import java.io.*;
import java.rmi.registry.*;
import java.util.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner; // Import the Scanner class

public class Cliente {

  static String comandos =
    "bind: Faz o cliente se conectar a outro servidor e muda o repositorio corrente. Este comando" +
    "recebe o nome de um repositorio e obtem do servico de nomes uma referencia para esse" +
    "repositorio, que passa a ser o repositorio corrente.\n\n" +
    "listp: Lista as pecas do repositorio corrente.\n\n" +
    "getp: Busca uma peca por codigo. A busca e efetuada no repositorio corrente. Se encontrada," +
    "a peca passa a ser a nova peca corrente.\n\n" +
    "showp: Mostra atributos da peca corrente.\n\n" +
    "clearlist: Esvazia a lista de sub-pecas corrente.\n\n" +
    "addsubpart: Adiciona a lista de sub-pecas corrente n unidades da peca corrente.\n\n" +
    "addp: Adiciona uma peca ao repositorio corrente. A lista de sub-pecas corrente e usada como" +
    "lista de subcomponentes diretos da nova peca. (E so para isto que existe a lista de " +
    "sub-pecas corrente.\n\n)" +
    "quit: Encerra a execucao do cliente";

  public static String nome_repositorio_corrente = "";
  public static String[] peca_corrente = new String[3];
  public static ArrayList<String[]> sub_peca_corrente = new ArrayList<String[]>();

  public static void clearConsole() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033\143");
      }
    } catch (IOException | InterruptedException ex) {}
  }

  public static void exibirHeader() {
    System.out.println(
      "Repositorio corrente : " + nome_repositorio_corrente + "\n"
    );
    System.out.println(
      "Peca corrente : " +
      "\nNome - " +
      peca_corrente[0] +
      "\nDescricao - " +
      peca_corrente[1] +
      "\nCodigo - " +
      peca_corrente[2] +
      "\n"
    );
    System.out.println("Sub-peca corrente : ");
    for (int i = 0; i < sub_peca_corrente.size(); i++) {
      System.out.println("Nome - " + sub_peca_corrente.get(i)[0]);
    }
    System.out.println("------------------------------");
    System.out.println("Digite um comando:");
  }

  public static void bind() {
    System.out.println("A ser implementado");
    okParaContinuar();
  }

  public static void okParaContinuar() {
    System.out.println("\nPrecione ENTER para continuar ...");
    Scanner myObj = new Scanner(System.in); // Create a Scanner object
    String aux = myObj.nextLine();
  }

  public static void main(String args[]) {
    while (true) {
      String resposta = "";
      String servidor = "";
      Scanner myObj = new Scanner(System.in); // Create a Scanner object

      System.out.println("Digite o nome do server");
      servidor = myObj.nextLine();

      try {
        // Obtém uma referência para o registro do RMI
        Registry registry = LocateRegistry.getRegistry(null);

        // Obtém a stub do servidor
        Comandos stub = (Comandos) registry.lookup(servidor);
        resposta = "";
        clearConsole();
        nome_repositorio_corrente = stub.getRepositorio();
        while (true) {
          clearConsole();
          exibirHeader();
          resposta = myObj.nextLine();

          // Comando Bind: Sai do Servidor atual e se conecta em outro.

          if (resposta.equals("help")) {
            System.out.print(comandos);
            okParaContinuar();
          } else if (resposta.equals("bind")) {
            stub.clearCorrentes();
            break;
          }
          // Comando listp: Printa as peças do repositório
          else if (resposta.equals("listp")) {
            System.out.println("Chamando listp");
            String lista = stub.listp();
            System.out.println(lista);
            okParaContinuar();
          }
          //Comando getp:
          else if (resposta.equals("getp")) {
            System.out.println("Digite o ID");

            String id = myObj.nextLine();
            System.out.println(stub.getp(id));
            peca_corrente = stub.getPecaCorrente();
            okParaContinuar();
          } else if (resposta.equals("showp")) {
            System.out.println(stub.showp());
            okParaContinuar();
          } else if (resposta.equals("clearlist")) {
            sub_peca_corrente.clear();
            okParaContinuar();
          } else if (resposta.equals("addsubpart")) {
            System.out.println("Quantidade de pecas a serem adicionadas");
            String aux = myObj.nextLine();
            String[] aux_subpeca_resposta = new String[3];
            String[] aux_subpeca = new String[4];
            if (stub.addsubpart() != null) {
              try {
                int qtd = Integer.parseInt(aux);
                aux_subpeca_resposta = stub.addsubpart();
                System.out.println(aux_subpeca_resposta[0]);
                aux_subpeca[0] = aux_subpeca_resposta[0];
                aux_subpeca[1] = aux_subpeca_resposta[1];
                aux_subpeca[2] = aux_subpeca_resposta[2];
                aux_subpeca[3] = String.valueOf(qtd);
                sub_peca_corrente.add(aux_subpeca);
                System.out.println("Subpart criada com sucesso");
              } catch (Exception e) {
                System.out.println(
                  "Digite um numero inteiro. Operacao encerrada."
                );
              }
            } else {
              System.out.println("Nenhuma peca corrente selecionada.");
            }
            okParaContinuar();
          } else if (resposta.equals("addp")) {
            System.out.print("Digite o nome da peca: ");
            String nome = myObj.nextLine();
            System.out.print("Digite a descricao da peca: ");
            String descricao = myObj.nextLine();
            String codigo = stub.addp(nome, descricao);
            for (int i = 0; i < sub_peca_corrente.size(); i++) {
              stub.receberSubpeca(
                sub_peca_corrente.get(i)[0],
                sub_peca_corrente.get(i)[1],
                sub_peca_corrente.get(i)[2],
                sub_peca_corrente.get(i)[3],
                codigo
              );
            }
            okParaContinuar();
          } else if (resposta.equals("quit")) {
            System.exit(0);
          } else {
            System.out.println(
              "Comando não reconhecido.\nDigite help para a lista de comandos"
            );
            okParaContinuar();
          }
        }
      } catch (Exception e) {
        System.out.println("ERRO: " + e);
      }
    }
  }
}
