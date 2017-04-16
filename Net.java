import sun.management.Sensor;

import java.io.*;
import java.net.*;

/**
 * Created by m on 19.03.2017.
 */
public class Net {
    public static void main(String args[]) {


        try (ServerSocket ss = new ServerSocket(44442)) {

           //new Http_client(44442);

            System.out.println(" устаневливаем соединение");
             new Http_server(ss.accept());



        } catch (Exception e) {

            System.out.println("Что-то пошло не так при создании сокета");
        }



//hhh
    }
}




class Http_server extends Thread {
    Socket socket;
    String ss;

    Http_server(Socket s) {

        socket =s;
        setPriority(MAX_PRIORITY);
        start();

    }
    public  void run() {
        System.out.println(" ждем  данные");

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);



            ss = br.readLine();

            System.out.println(ss);


            if(ss.equals("hello")){

                try

                {// здесь адрес файла для выгрузки

                    pw.println("10203040506070809");
                    System.out.println("печатаем");



                }

                catch (Exception e) {

                    System.out.println("Не удалось отправить файл");
                }



            }







        } catch (Exception e) {

            System.out.println("Не удалось считать строку из клиента");
        }











    }


}








    class Http_client{
    int port;

    Http_client(int port){

        this.port = port;

        try (Socket socket = new Socket("192.168.1.203", port)) {

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            pw.println("hello");



        } catch (Exception e) {

            System.out.println("Не отправить строку из клиента");

        }


    }


    }






