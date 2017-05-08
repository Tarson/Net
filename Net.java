import sun.management.Sensor;

import java.io.*;
import java.net.*;

/**
 * Created by m on 19.03.2017.
 */
public class Net {
    public static void main(String args[]) {


        try (ServerSocket ss = new ServerSocket(44441)) {

             new Http_client(44441);
             new Http_server(ss.accept());



        } catch (Exception e) {

            System.out.println("Что-то пошло не так при создании сокета");
        }



//hhhfdfedd
    }
}




class Http_server extends Thread {
    Socket socket;

    Http_server(Socket s) {

        socket =s;
        setPriority(MAX_PRIORITY);
        start();

    }
    public  void run() {


        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {




            String s = br.readLine();

            System.out.println(s+"   воистенну");

            String ss = br.readLine();

            System.out.println(ss+"   ваще воистенну");

        } catch (Exception e) {

            System.out.println("Не удалось считать строку из клиента");
        }


        // System.out.println("Чупа-чупа");
    }


}








    class Http_client{
    int port;

    Http_client(int port){

        this.port = port;

        try (Socket socket = new Socket("192.168.1.35", port)) {

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("Соседи пидары");

            pw.println("Соседи пидары скоро сдохнут");
            pw.flush();



        } catch (Exception e) {

            System.out.println("Не отправи ть строку из клиента");

        }


    }


    }






