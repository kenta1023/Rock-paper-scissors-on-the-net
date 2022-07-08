import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.GridLayout;

class MyFrame extends JFrame{
    MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(1,3));
        getContentPane().add(new JButton("グー"));
        getContentPane().add(new JButton("チョキ"));
        getContentPane().add(new JButton("パー"));
        setSize(300,200);
        setVisible(true);
    }
}

public class client {
    public static void main(String[] args){
        new MyFrame();
        Scanner scanner = new Scanner(System.in);
        System.out.print("1 or 2 or 3 >>>");
        int myhand = scanner.nextInt();
        scanner.close();
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
