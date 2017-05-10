import sun.management.Sensor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

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


            BufferedOutputStream pw = new BufferedOutputStream((socket.getOutputStream()));



            ss = br.readLine();

            System.out.println(ss);


            if(ss.equals("hello")){

                try

                {// здесь адрес файла для выгрузки

                  //  pw.println("10203040506070809");
                    System.out.println("печатаем");

                    File file = new File("d:/Blink.bin");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

                    byte [] data = new byte[bis.available()];
                    bis.read(data);


                    pw.write(data);
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



                int c;

                InputStream is = socket.getInputStream();

                ArrayList <Byte> buf = new ArrayList<>();


                while((c = is.read())!=-1)
                {

                    buf.add((byte)c);
                }

                byte [] data = new byte[buf.size()];
                for(int i=0; i < data.length;i++)
                {

                    data[i]=buf.get(i);

                }




                File file = new File("d:Blink2.bin");


                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));


                 bos.write(data);
                 bos.flush();




            } catch (Exception e) {

                System.out.println("Не отправить строку из клиента");

            }


        }

    }






