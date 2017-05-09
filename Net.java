import sun.management.Sensor;

import java.io.*;
import java.net.*;

/**
 * Created by m on 19.03.2017.
 */
public class Net {
    public static void main(String args[]) {


        try (ServerSocket ss = new ServerSocket(44442)) {

           new Http_client(44442);

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

                  //  pw.println("10203040506070809");
                    System.out.println("печатаем");

                    File file = new File("d:/Blink.bin");
                    BufferedReader bfr = new BufferedReader(new FileReader(file));

                    char [] data = new char[(int)file.length()];
                    bfr.read(data);
                    for(int i=0; i < file.length();i++)
                   {
                        System.out.print(data[i]);
                    }

                    pw.println(data);
                    pw.flush();
                    System.out.println("отослали");

                }

                catch (Exception e) {

                    System.out.println("Не удалось отправить файл");
                }



            }



            socket.close();



        } catch (Exception e) {

            System.out.println("Не удалось считать строку из клиента");
        }











    }


}








    class Http_client extends Thread {
    int port;

    Http_client(int port){

        this.port = port;
        start();



    }
        public  void run() {

            try (Socket socket = new Socket("192.168.1.203", port)) {

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);


                pw.println("hello");



                String line  ;
                BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                File file = new File("d:Blink2.bin");


                PrintWriter pwr = new PrintWriter(new FileWriter(file),true);



                while ((line=bfr.readLine())!=null)

                {

                    pwr.println(line);

                    System.out.println(line);
                }



            } catch (Exception e) {

                System.out.println("Не отправить строку из клиента");

            }


        }

    }






