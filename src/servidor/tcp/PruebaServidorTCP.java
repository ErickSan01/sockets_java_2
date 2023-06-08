package servidor.tcp;

import cliente.tcp.ClienteEnviaTCP;
import gui.chatScreen;

public class PruebaServidorTCP{
    public static void main(String args[])throws Exception{

        ClienteEnviaTCP cliente = new ClienteEnviaTCP("192.168.3.12", 60000);

        chatScreen chatScreen = new chatScreen(cliente);

        ServidorTCP servidorTCP=new ServidorTCP(60000);
        
        servidorTCP.inicia();
    }
}
