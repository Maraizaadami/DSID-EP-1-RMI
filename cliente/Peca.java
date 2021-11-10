import java.util.HashMap; // import the HashMap class
public class Peca {
  public String nome = "";
  public String descricao = "";
  public String codigo = "";
  HashMap<Peca, Integer> subcomponentes = new HashMap<Peca, Integer>();

  public Peca(String nome, String descricao, String codigo) {
    this.nome = nome;
    this.descricao = descricao;
    this.codigo = codigo;
  }

  public void adicionarSubcomponentes(Peca peca, Integer quantidade){
    subcomponentes.put(peca, quantidade);
  }

  public String listarSubcomponentes(){
    String aux = "";
    for (Peca i : subcomponentes.keySet()) {
      aux +="    " + i.nome + "\n";
      // + " (peca: "+ nome + " codigo: " + codigo + ")\n"
    }
    return (aux);
  }

  public String examinarSubcomponentes(){
    String aux = "Quantidade de subcomponentes: ";
    aux += subcomponentes.size();
    // System.out.println(subcomponentes);
    if(subcomponentes.size() > 0){
      aux += "\nsubcomponentes:";
      System.out.println("subcomponentes:");
      for (Peca i : subcomponentes.keySet()) {
        aux += "\n"+i.nome + " - "+ subcomponentes.get(i) + " unidades";
      }
    }
    return(aux);
  }
  
  public String examinar(){
    String aux = "Nome da peca: " + nome + "\n";
    aux += "Descricao: " + descricao + "\n";
    aux += examinarSubcomponentes();
    return(aux);
  }

  public void clearSubpecas(){
    subcomponentes.clear();
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public void setDescricao(String descricao){
    this.descricao = descricao;
  }

  public String getNome(){
    return(nome);
  }

  public String getDescricao(){
    return(descricao);
  }

  public String getCodigo(){
    return(codigo);
  }
}