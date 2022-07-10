import java.io.*;
import java.net.*;
import java.util.Random;

public class server {
    public static void main(String[] args){
        final String[] hand = {null,"グー","チョキ","パー"};
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true){
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int cpu = cpu();
                int client_hand = in.readInt();
                String judge = judge(client_hand,cpu);
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println("あなた:"+hand[client_hand]+"  cpu:"+hand[cpu]+"  "+judge);
                writer.close();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
    private static int cpu(){
        Random random = new Random();
        int server_hand = random.nextInt(3)+1;
        return server_hand;
    }
    private static String judge(int client_hand,int server_hand){
        if (client_hand == server_hand){
            return "引き分け";
        }else if((client_hand-server_hand+3)%3 == 2){
            return "勝ち";
        }else{
            return "負け";
        }
    }
}
