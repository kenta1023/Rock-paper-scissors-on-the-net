import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class client extends JFrame implements ActionListener{
    public static void main(String[] args){
        new client();
    }
    public int myhand;
    JButton button1;
    JButton button2;
    JButton button3;
    client(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(1,3));
        button1 = new JButton("グー");
        button1.addActionListener(this);
        getContentPane().add(button1);
        button2 = new JButton("チョキ");
        button2.addActionListener(this);
        getContentPane().add(button2);
        button3 = new JButton("パー");
        button3.addActionListener(this);
        getContentPane().add(button3);
        setSize(300,200);
        setVisible(true);
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
