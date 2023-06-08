package servidor.tcp;

import gui.chatScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
//importar la libreria java.net
 
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
//importar la libreria java.io
// declaramos la clase servidortcp
 
public class ServidorEscuchaTCP extends Thread {
    // declaramos un objeto ServerSocket para realizar la comunicación
    protected ServerSocket socket;
    protected DataInputStream in;
    protected Socket socket_cli;
    protected final int PUERTO_SERVER;

    protected chatScreen chat;
    
    public ServidorEscuchaTCP(int puertoS)throws Exception{
        PUERTO_SERVER=puertoS;
        // Instanciamos un ServerSocket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación

        socket = new ServerSocket(PUERTO_SERVER);
        //this.chat = chat;
    }

    public void setChat(chatScreen chat){
        this.chat = chat;
    }
    // método principal main de la clase
    public void run() {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            // Creamos un socket_cli al que le pasamos el contenido del objeto socket después
            // de ejecutar la función accept que nos permitirá aceptar conexiones de clientes
            socket_cli = socket.accept();

            // Declaramos e instanciamos el objeto DataInputStream
            // que nos valdrá para recibir datos del cliente

            in = new DataInputStream(socket_cli.getInputStream());

            // Creamos un bucle do while en el que recogemos el mensaje
            // que nos ha enviado el cliente y después lo mostramos
            // por consola
            do {
                String mensaje ="";
                int fileNameLength = in.readInt();
                System.out.println(fileNameLength);
                if(fileNameLength == -1){
                    mensaje = in.readUTF();
                    if(mensaje.length() > 0) {
                        System.out.println(mensaje);
                        chat.update(mensaje, Color.BLACK);
                    }
                } else {
                    byte[] fileNameBytes = new byte[fileNameLength];

                    in.readFully(fileNameBytes, 0, fileNameLength);

                    String fileName = new String(fileNameBytes);

                    int fileContentLength = in.readInt();

                    if(fileContentLength > 0){
                        byte[] fileContentBytes = new byte[fileContentLength];
                        in.readFully(fileContentBytes, 0, fileContentLength);

                        JFrame frameDescarga = createFrame(fileName, fileContentBytes);
                        frameDescarga.setVisible(true);

                        chat.updateFile();
                    }
                }


            } while (true);
        }
        // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {

            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static JFrame createFrame(String fileName, byte[] fileData){
        JFrame frame = new JFrame("Descargar archivo");
        frame.setSize(400, 250);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("¿Descargar el archivo?");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel prompt = new JLabel("Desea descargar el archivo " + fileName);
        prompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton yes = new JButton("Si");
        yes.setPreferredSize(new Dimension(150, 75));

        JButton no = new JButton("No");
        no.setPreferredSize(new Dimension(150, 75));

        JPanel botones = new JPanel();
        botones.setBorder(new EmptyBorder(20, 0, 10, 0));
        botones.add(yes);
        botones.add(no);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File archivo = new File(fileName);

                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(archivo);

                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    frame.dispose();
                } catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panel.add(title);
        panel.add(prompt);
        panel.add(botones);

        frame.add(panel);

        return frame;
    }
}
