package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerEncendido extends JFrame{
    private JLabel labelEncendido;
    private JButton okBtn;
    private JPanel panel;

    public ServerEncendido(){
        setContentPane(panel);
        setTitle("Servidor Encendido");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
