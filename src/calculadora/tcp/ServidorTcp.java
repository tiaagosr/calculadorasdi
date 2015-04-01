package calculadora.tcp;

import calculadora.Conexao;
import calculadora.Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
	
/**
 *
 * @author tiagosr
 */
public class ServidorTcp extends Servidor{
    private ServerSocket socket;
    
    public ServidorTcp() { 
        this.iniciaSocket();
    }
    
    @Override
    protected void iniciaSocket(){
        try {
            this.socket = new ServerSocket(Conexao.porta);
        } catch (IOException ex) {
            Logger.getLogger(ServidorTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void recebeConexao(){
        try {
            Socket tmpSocket = this.socket.accept();
            
            ConexaoTcp novaConexao = new ConexaoTcp(tmpSocket);
            System.out.println("Novo socket com endereco "+tmpSocket.getInetAddress().getHostAddress());
            
            Thread tmpThread = new Thread(novaConexao);   
            tmpThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(true){
            this.recebeConexao();
        }
    }	
}