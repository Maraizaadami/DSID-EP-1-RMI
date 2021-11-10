import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;
import java.util.Scanner;

public class Servidor implements Comandos, java.io.Serializable {

  static Repositorio repositorio_corrente = new Repositorio("", "");
  static Peca peca_corrente = new Peca("", "", "");
  static String nomeServidor = "";

  public String getRepositorio() throws RemoteException {
    try {
      return repositorio_corrente.nome;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  // Main do servidor
  
  //////////////////////////// METODOS QUE O CLIENTE VAI MANDAR EXECUTAR //////////////////////////////////////////////
  
  // Comando retorna String da lista de partes
  public String listp() throws RemoteException {
      // repositorio_corrente.listarTodasAsPecas();
      return repositorio_corrente.listarTodasAsPecas();
    }
    
    public String getp(String codigo) {
    if (repositorio_corrente.buscarCodigo(codigo)) {
        peca_corrente = repositorio_corrente.getPeca(codigo);
        return "Peca selecionada com sucesso!";
    } else {
        return "Id nao reconhecido";
    }
}

public String showp() {
    String aux = peca_corrente.examinar();
    // okParaContinuar();
    
    return aux;
}

public String clearlist() {
    peca_corrente.clearSubpecas();
    
    // okParaContinuar();
    if (peca_corrente.subcomponentes.size() == 0) {
        return "Lista de sub-pecas esvaziada com sucesso!";
    } else {
        return "Algo deu errado!";
    }
}

public String[] addsubpart() {
    String[] aux = new String[3];
    if (peca_corrente.codigo != "") {
        aux[0] = peca_corrente.nome;
        aux[1] = peca_corrente.descricao;
        aux[2] = peca_corrente.codigo;
        return (aux);
    }
    return (null);
}

public String addp(String nome, String descricao) {
    String codigo_nova_peca = repositorio_corrente.adicionarPeca(
        nome,
        descricao
        );
        return codigo_nova_peca;
    }
    
    public void clearCorrentes() {
        peca_corrente = new Peca("", "", "");
    }
    
    public void receberSubpeca(
        String nome,
        String descricao,
        String codigo,
        String quantidade,
        String adicionarSubcomponentes
        ) {
            repositorio_corrente.adicionarSubcomponentes(
                nome,
                descricao,
                codigo,
                Integer.parseInt(quantidade),
                adicionarSubcomponentes
                );
            }
            
            public String[] getPecaCorrente() {
                String[] aux = new String[3];
                aux[0] = peca_corrente.nome;
                aux[1] = peca_corrente.descricao;
                aux[2] = peca_corrente.codigo;
                
                return (aux);
            }
            
            ///////////////////////// INICIALIZAR REPOSITORIO ///////////////////////////////////////////////////////////////////
            public static void inicializar() {
                System.out.println("Inicializando Repositorio");
                try {
      if (nomeServidor.equals("servidor1")) {
          repositorio_corrente = new Repositorio("Repositorio 01", "rep1");
        // Adicionando pecas
        repositorio_corrente.adicionarPeca("PECA 01", "DESCRICAO 01");
        repositorio_corrente.adicionarPeca("PECA 02", "DESCRICAO 02");
        repositorio_corrente.adicionarPeca("PECA 03", "DESCRICAO 03");
        // Adicionando subcomponentes:
        repositorio_corrente.adicionarSubcomponentes(
            "SUBPECA 01",
            "DESCRICAO SUBPECA 01",
            "sub_1",
            30,
            "rep1_1"
            );
            repositorio_corrente.adicionarSubcomponentes(
                "SUBPECA 02",
                "DESCRICAO SUBPECA 02",
                "sub_2",
                15,
                "rep1_1"
                );
            }
            if (nomeServidor.equals("servidor2")) {
                repositorio_corrente = new Repositorio("Repositorio 02", "rep2");
                // Adicionando pecas
                repositorio_corrente.adicionarPeca("PECA 04", "DESCRICAO 04");
                repositorio_corrente.adicionarPeca("PECA 05", "DESCRICAO 05");
                repositorio_corrente.adicionarPeca("PECA 06", "DESCRICAO 06");
                repositorio_corrente.adicionarPeca("PECA 07", "DESCRICAO 07");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
      try {
        System.out.println("Digite o nome do seu servidor");
        System.out.println("Servidores com registro:\nservidor1\nservidor2\n");
        Scanner myObj = new Scanner(System.in);
        nomeServidor = myObj.nextLine();
        inicializar();
    
        // Instancia o objeto servidor e a sua stub
        Servidor server = new Servidor();
        Comandos stub = (Comandos) UnicastRemoteObject.exportObject(server, 0);
    
        // Registra a stub no RMI Registry para que ela seja obtida pelos clientes
        Registry registry = LocateRegistry.getRegistry();
    
        registry.bind(nomeServidor, stub);
    
        System.out.println("Servidor pronto");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
}
