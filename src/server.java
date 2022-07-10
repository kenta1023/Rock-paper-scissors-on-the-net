import java.io.*;
import java.net.*;
import java.util.Random;

public class server {
    static String tempName="";
    static int temphand=0;
    public static void main(String[] args){
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(5000);
            while(true){
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                String serverName;
                int server_hand;
                int hand = in.readInt();
                String name = in.readUTF();
                if(name.equals(tempName) || tempName.equals("")){
                    server_hand = cpu();
                    serverName = "CPU";
                }else{
                    server_hand = temphand;
                    serverName = tempName;
                }
                tempName = name;
                temphand = hand;
                String judge = judge(hand,server_hand);
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                final String[] hands = {null,"グー","チョキ","パー"};
                String message =name+"("+hands[hand]+")  VS  "+serverName+"("+hands[server_hand]+")/"+judge;
                writer.println(message);
                System.out.println(socket.getRemoteSocketAddress()+ " 送信:" +message);
                writer.close();
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    private static int cpu(){
        Random random = new Random();
        int server_hand = random.nextInt(3)+1;
        return server_hand;
    }
    private static String judge(int hand,int server_hand){
        if (hand == server_hand){
            return "引き分け";
        }else if((hand-server_hand+3)%3 == 2){
            return "勝ち";
        }else{
            return "負け";
        }
    }
}