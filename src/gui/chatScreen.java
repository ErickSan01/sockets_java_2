package gui;

import cliente.tcp.ClienteEnviaTCP;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class chatScreen extends JFrame{
    private JTextPane paneChat;
    private JTextPane textPane1;
    private JButton botonEnviar;
    private JPanel mainPanel;
    private JButton botonVideollamada;
    private JButton archivoButton;

    public chatScreen(ClienteEnviaTCP cliente){
        setContentPane(mainPanel);
        setTitle("Chat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        paneChat.setEditable(false);

        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = textPane1.getText();
                if(!Objects.equals(mensaje, "") && !Objects.equals(mensaje, " ")) {
                    try {
                        cliente.update(mensaje);
                        appendToPane(paneChat, "Cliente 1: " + mensaje + "\n", Color.BLACK);
                        textPane1.setText("");
                    } catch (Exception ex){
                        appendToPane(paneChat, "Cliente 1: " + mensaje + "\t (Mensaje no enviado...)\n", Color.RED);
                        textPane1.setText("");
                    }
                }
            }
        });

        archivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());

                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION){
                    try {
                        File file = j.getSelectedFile();

                        cliente.sendFile(file);
                    } catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                    appendToPane(paneChat, "Archivo Enviado\n", Color.BLUE);
                }

            }
        });
    }

    public void update(String mensaje, Color c){
        appendToPane(paneChat, "Cliente 2: " + mensaje + "\n", c);
    }

    public void updateFile(){
        appendToPane(paneChat, "Cliente 2 Ha enviado un archivo\n", Color.BLUE);
    }

    private void appendToPane(JTextPane tp, String mensaje, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        StyledDocument document = (StyledDocument) tp.getDocument();
        try {
            document.insertString(document.getLength(), mensaje, aset);
        } catch(BadLocationException ble){
            ble.printStackTrace();
        }

    }

}
