import java.io.*;
import java.net.*;
import java.util.Random;

class MyThread extends Thread{
    public MyThread(Socket socket){
        try{
            DataInputStream in = new DataInputStream(socket.getInputStream());
            int cpu = cpu();
            int client_hand = in.readInt();
            String judge = judge(client_hand,cpu);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            final String[] hand = {null,"グー","チョキ","パー"};
            String message ="YOU("+hand[client_hand]+")   vs   CPU("+hand[cpu]+")/"+judge;
            writer.println(message);
            System.out.println(socket.getRemoteSocketAddress()+ " 送信:" +message);
            writer.close();
        }catch(IOException e){
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

public class server {
    public static void main(String[] args){
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(5000);
            while(true){
                Socket socket = serverSocket.accept();
                MyThread thread = new MyThread(socket);
                thread.start();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
