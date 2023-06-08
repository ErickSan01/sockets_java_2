package gui;

import cliente.tcp.ClienteEnviaTCP;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.tcp.ServidorTCP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ajustesScreen extends JFrame{
    private JTextField serverIP;
    private JPanel mainPanel;
    private JLabel label;
    private JButton botonGuardar;
    private JButton botonServer;

    private chatScreen chat;

    ServidorTCP servidorTCP;

    public ajustesScreen() throws Exception{
        setContentPane(mainPanel);
        setTitle("Chat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);


        label.setText("Dirección IP del Server:");

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dir = serverIP.getText();
                int count = dir.length() - dir.replace(".", "").length();

                if(count == 3) {
                    System.out.println("Formato de Dirección IP correcto: " + dir);
                    ClienteEnviaTCP clienteTCP = null;
                    try {
                        clienteTCP = new ClienteEnviaTCP(dir,60000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    chat = new chatScreen(clienteTCP);

                    servidorTCP.setChat(chat);

                    setVisible(false);
                    chat.setVisible(true);

                }
            }
        });

        botonServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servidorTCP=new ServidorTCP(60000);

                try{
                    servidorTCP.inicia();
                    ServerEncendido serverGUI = new ServerEncendido();
                } catch (Exception exp){
                    exp.printStackTrace();
                }
            }
        });
    }
}
