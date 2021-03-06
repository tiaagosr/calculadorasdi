package calculadora.udp;

import calculadora.Conexao;
import calculadora.Servidor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
	
/**
 *
 * @author tiagosr
 */
public class ServidorUdp extends Servidor{
    public DatagramSocket socket;
    private InetAddress peerServer;
    
    public ServidorUdp(String endereco) { 
        this.iniciaSocket();
        try {
            peerServer = InetAddress.getByName(endereco);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void iniciaSocket(){
        try {
            this.socket = new DatagramSocket(Conexao.porta);
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void recebePacote(){
        try {
            byte[] dados = new byte[1024];
            
            DatagramPacket pacote = new DatagramPacket(dados, dados.length);
            socket.receive(pacote);
            
            ConexaoUdp novaConexao = new ConexaoUdp(this.socket, pacote, dados, this.peerServer);
            System.out.printf("Novo Pacote de %s com tamanho %d\n", pacote.getAddress().getHostAddress(), pacote.getLength());
            
            Thread tmpThread = new Thread(novaConexao);   
            tmpThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(true){
            this.recebePacote();
        }
    }	
}