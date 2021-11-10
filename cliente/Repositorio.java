import java.util.ArrayList; // import the ArrayList class

public class Repositorio {

  public ArrayList<Peca> repositorio = new ArrayList<Peca>();
  public String nome = "";
  public String id = "";

  public Repositorio(String nome, String id) {
    this.nome = nome;
    this.id = id;
  }

  public String adicionarPeca(String nome, String descricao) {
    int numero = 1;
    String codigo = this.id + "_" + numero;
    while (buscarCodigo(codigo)) {
      numero += 1;
      codigo = this.id + "_" + numero;
    }
    this.repositorio.add(new Peca(nome, descricao, codigo));
    return (codigo);
  }
  
  public void adicionarPecaCodigoDefinido(String nome, String descricao, String codigo) {
    if(!buscarCodigo(codigo)){
      this.repositorio.add(new Peca(nome, descricao, codigo));
    }
    else{
      System.out.println("Erro ao criar peca com codigo definido");
    }
  }

  public void adicionarSubcomponentes(
    String nome,
    String descricao,
    String codigo,
    Integer quantidade,
    String codigo_peca
  ) {
    for (int i = 0; i < repositorio.size(); i++) {
      if (repositorio.get(i).getCodigo().equals(codigo_peca)) {
        Peca aux = new Peca(nome, descricao, codigo);
        repositorio.get(i).adicionarSubcomponentes(aux, quantidade);
      }
    }
  }
  

  public String listarPecas() {
    String aux = "";
    for (int i = 0; i < repositorio.size(); i++) {
      aux +=
        repositorio.get(i).getNome() +
        " codigo: " +
        repositorio.get(i).getCodigo() +
        "\n";
    }
    return (aux);
  }

  public String listarSubcomponentes() {
    String aux = "";
    for (int i = 0; i < repositorio.size(); i++) {
      aux += repositorio.get(i).listarSubcomponentes();
    }
    return (aux);
  }

  public String listarTodasAsPecas() {
    String aux = "";
    for (int i = 0; i < repositorio.size(); i++) {
      aux +=
        repositorio.get(i).getNome() +
        " codigo: " +
        repositorio.get(i).getCodigo() +
        "\n";
      aux += repositorio.get(i).listarSubcomponentes();
    }
    return (aux);
  }

  public String examinarRepositorio() {
    String aux = "Nome do repositorio: " + nome;
    aux += "Quantidade de pecas: " + repositorio.size();
    return (aux);
  }

  public String examinarPeca(String codigo) {
    String aux = "";
    for (int i = 0; i < repositorio.size(); i++) {
      if (repositorio.get(i).getCodigo().equals(codigo)) {
        aux += repositorio.get(i).examinar();
      }
    }
    return(aux);
  }

  public boolean buscarCodigo(String codigo) {
    for (int i = 0; i < repositorio.size(); i++) {
      if (repositorio.get(i).getCodigo().equals(codigo)) {
        return (true);
      }
    }
    return (false);
  }

  public void removerPeca(String codigo) {
    for (int i = 0; i < repositorio.size(); i++) {
      if (repositorio.get(i).getCodigo().equals(codigo)) {
        repositorio.remove(i);
      }
    }
  }

  public Peca getPeca(String codigo) {
    for (int i = 0; i < repositorio.size(); i++) {
      if (repositorio.get(i).getCodigo().equals(codigo)) {
        return (repositorio.get(i));
      }
    }
    return (null);
  }
}
