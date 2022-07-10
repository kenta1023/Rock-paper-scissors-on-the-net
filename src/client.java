import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client extends JFrame implements ActionListener{
    JPanel cardPanel;
    CardLayout layout;
    public static void main(String[] args){
        client frame = new client();
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public int myhand;
    JButton button1;
    JButton button2;
    JButton button3;
    client(){
        JPanel panel1 = new JPanel();

        button1 = new JButton("グー");
        button1.addActionListener(this);
        panel1.add(button1);
        button2 = new JButton("チョキ");
        button2.addActionListener(this);
        panel1.add(button2);
        button3 = new JButton("パー");
        button3.addActionListener(this);
        panel1.add(button3);

        getContentPane().add(panel1);

        JPanel panel2 = new JPanel();

        JLabel result = new JLabel("message");
        panel2.add(result);


    }
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == button1){
            myhand = 1;
        }else if (ae.getSource() == button2){
            myhand = 2;
        }else if (ae.getSource() == button3){
            myhand = 3;
        }
        try{
            Socket socket = new Socket("127.0.0.1",5000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(myhand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = reader.readLine();
            System.out.println(message);
            out.close();
            reader.close();
            socket.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}