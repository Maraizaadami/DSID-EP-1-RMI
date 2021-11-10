import java.rmi.*;

public interface Comandos extends Remote{
    public String listp() throws RemoteException;
    public String getp(String codigo)  throws RemoteException;
    public String showp()  throws RemoteException;
    public String clearlist()  throws RemoteException;
    public String[] addsubpart()  throws RemoteException;
    public String addp(String nome, String descricao)  throws RemoteException;
    public String getRepositorio() throws RemoteException;
    public void clearCorrentes() throws RemoteException;
    public void receberSubpeca(String nome, String descricao, String codigo, String quantidade, String adicionarSubcomponentes) throws RemoteException;
    public String[] getPecaCorrente() throws RemoteException;
}
