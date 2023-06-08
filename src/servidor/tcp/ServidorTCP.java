package servidor.tcp;

import gui.chatScreen;
import servidor.tcp.ServidorEscuchaTCP;

public class ServidorTCP{
    protected final int PUERTO_SERVER;
    protected chatScreen chat;

    ServidorEscuchaTCP servidorTCP;
    
    public ServidorTCP(int puertoS){
        PUERTO_SERVER=puertoS;
    }
    
    public void inicia()throws Exception{
        servidorTCP=new ServidorEscuchaTCP(PUERTO_SERVER);
        
        servidorTCP.start();
    }

    public void setChat(chatScreen chat){
        servidorTCP.setChat(chat);
    }
}
