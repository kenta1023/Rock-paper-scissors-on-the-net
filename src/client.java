import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client extends JFrame implements ActionListener{
    JPanel cardPanel;
    CardLayout layout;
    static int width = 600;
    static int height = 400;
    public static void main(String[] args){
        client frame = new client();
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("ジャンケンゲーム");
    }
    //変数の宣言
    JPanel card1;
    JPanel card2;
    public int myhand;
    JLabel handLabel =new JLabel();
    JLabel judgeLabel =new JLabel();
    JButton button1;
    JButton button2;
    JButton button3;
    JButton againButton;

    client(){
        //カード１
        card1 = new JPanel();
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
        card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        handLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
        handLabel.setPreferredSize(new Dimension(width,height/2));
        handLabel.setHorizontalAlignment(JLabel.CENTER);
        card2.add("Center",handLabel);
        judgeLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 40));
        judgeLabel.setPreferredSize(new Dimension(width,height/2));
        judgeLabel.setHorizontalAlignment(JLabel.CENTER);
        card2.add("North",judgeLabel);
        againButton = new JButton("もう一回");
        againButton.addActionListener(this);
        card2.add("South",againButton);
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
            myhand = 0;
            layout.first(cardPanel);
        }
        if(myhand==1 || myhand==2 || myhand==3){
            try{
                Socket socket = new Socket("127.0.0.1",5000);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeInt(myhand);       //選択した手を送信
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message[] = (reader.readLine()).split("/");     //結果を受け取り
                handLabel.setText(message[0]);     //カード２のラベルに結果を与える
                judgeLabel.setText(message[1]);
                if(message[1].equals("勝ち")){
                    card2.setBackground(new Color(255,228,225));
                }else if(message[1].equals("負け")){
                    card2.setBackground(new Color(180,220,255));
                }else if(message[1].equals("引き分け")){
                    card2.setBackground(new Color(240,255,240));
                }
                out.close();
                reader.close();
                socket.close();
            }catch (IOException e){
                System.out.println(e);
            }
        }
    }
}