import sun.management.Sensor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by m on 19.03.2017.
 */
public class Net {
    public static void main(String args[]) {
        new Http_client(44442);

        try (ServerSocket ss = new ServerSocket(44442)) {



           System.out.println(" устанавливаем соединение");
             new Http_server(ss.accept());



        } catch (Exception e) {

           System.out.println("Что-то пошло не так при создании сокета");
        }




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
        System.out.println(" Ждём запрос от клиента");

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //  BufferedOutputStream bos = new BufferedOutputStream((socket.getOutputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);


            ss = br.readLine();




            if(ss.equals("program")){

                try

                {  pw.println("ready");// Greetings with CLIENT
                    System.out.println("hello from S");




                    // здесь адрес файла для загрузки

                    int c;

                    InputStream is = socket.getInputStream();

                    List <Byte> buf = new ArrayList<>();


                    while((c = is.read())!=-1)
                    {

                        buf.add((byte)c);
                    }

                    byte [] data = new byte[buf.size()];
                    for(int i=0; i < data.length;i++)
                    {

                        data[i]=buf.get(i);

                    }

                   // pw.println("ok");
               File file = new File("d:BlinkIN.bin");


                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));


                    bos.write(data);
                    bos.flush();



                } catch (Exception e) {

                    System.out.println("Не отправить строку из сервера");

                }









            }



            socket.close();



        } catch (Exception e) {

            System.out.println("Не удалось считать строку из сервера");
        }











    }


}








    class Http_client extends Thread {
    int port;
    String s;
  String Greetings_from_S;


    Http_client(int port){

        this.port = port;
        start();



    }
        public  void run() {

            try (Socket socket = new Socket("192.168.1.35", port)) {

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                pw.println("program");// Greetings with SERVER
                System.out.println("hello from C");


                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Greetings_from_S = br.readLine();
                //System.out.println(Greetings_from_S);


                if(Greetings_from_S.equals("ready")) {

                    try

                    {

                        File file = new File("d:/blinkOUT.bin");
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));



                        byte [] data = new byte[bis.available()];
                        bis.read(data);


                        byte [] data_buffer = new byte[1024];


                        int frames = data.length/1024;
                        System.out.println(frames);
                        int residy = data.length%1024;


                        for (int i = 0; i < frames;i++) {
                            for (int k = 0; k< (1024); k++) {
                                data_buffer[k] = data[k+1024*(i)];
                         }



                         sendingChunk(socket,data_buffer);

                       //     while (br.readLine()!="ok");
                       //     {}


                         }
                        byte [] data_buffer2= new byte[residy];
                        for (int i = 0; i < residy;i++) {

                                data_buffer2[i] = data[i+1024*(frames)];
                        }



                        sendingChunk(socket,data_buffer2);





                        socket.close();


                    } catch (Exception e) {

                        System.out.println(e);

                    }


                }


        } catch (Exception e) {

            System.out.println(e);

        }

     }




         public void sendingChunk (Socket socket,byte [] data_buffer){
            try {
                BufferedOutputStream bos = new BufferedOutputStream((socket.getOutputStream()));
                bos.write(data_buffer);
                bos.flush();


                {System.out.println("1024 байт файла отправлено");}
            }
            catch (Exception e) {

                System.out.println(e);

            }

         }

         public void sendingOK (){}














}






