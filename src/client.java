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
    //ボタン変数の宣言
    public int myhand;
    JLabel label =new JLabel();
    JButton button1;
    JButton button2;
    JButton button3;
    JButton againButton;

    client(){
        //カード１
        JPanel card1 = new JPanel();
        card1.setLayout(new GridLayout(1,3));
        button1 = new JButton("グー");
        button1.addActionListener(this);
        card1.add(button1);
        button2 = new JButton("チョキ");
        button2.addActionListener(this);
        card1.add(button2);
        button3 = new JButton("パー");
        button3.addActionListener(this);
        card1.add(button3);
        //カード２
        JPanel card2 = new JPanel();
        card2.add(label);
        againButton = new JButton("もう一回");
        againButton.addActionListener(this);
        card2.add(againButton);
        //パネルにカードを登録
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        cardPanel.add(card1);
        cardPanel.add(card2);

        getContentPane().add(cardPanel, BorderLayout.CENTER);

    }
    //イベント処理
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == button1){
            myhand = 1;
            layout.next(cardPanel);
        }else if (ae.getSource() == button2){
            myhand = 2;
            layout.next(cardPanel);
        }else if (ae.getSource() == button3){
            myhand = 3;
            layout.next(cardPanel);
        }else if (ae.getSource() == againButton){
            layout.first(cardPanel);
        }
        try{
            Socket socket = new Socket("127.0.0.1",5000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(myhand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = reader.readLine();
            System.out.println(message);
            label.setText(message);
            out.close();
            reader.close();
            socket.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}