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

    public static  byte theBytes[];



    public static void main(String args[]) {


  /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new UploaderGUI().setVisible(true);
            }
        });


        new Http_client(4000);

        //   try (ServerSocket ss = new ServerSocket(44441)) {
        //      System.out.println(" устанавливаем соединение");
        //        new Http_server(ss.accept());
        //     } catch (Exception e) {
        //       System.out.println("Что-то пошло не так при создании сокета");
        //    }

        String string_fromHexFile  = "";
        String string_final = "";
        String file_name = "d:BlinkOUT.hex";
      try(BufferedReader br = new BufferedReader(new FileReader(file_name)))
      {


          while ((string_fromHexFile = br.readLine()) != null){


                string_fromHexFile = new StringBuilder(string_fromHexFile).delete(0,9).toString();
                int stringLenth = string_fromHexFile.length();
                string_fromHexFile = new StringBuilder(string_fromHexFile).delete(stringLenth-2,stringLenth).toString();
                string_final = string_final+string_fromHexFile;



          }





      }

      catch (Exception e) {

          System.out.println(e);

      }







        int nLength = string_final.length();
         theBytes = new byte[nLength / 2];
        for (int i = 0; i < nLength; i += 2) {
            theBytes[i/2] = (byte) ((Character.digit(string_final.charAt(i), 16) << 4) + Character.digit(string_final.charAt(i+1), 16));
        }





        System.out.println(theBytes.length);



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

        try (Socket socket = new Socket("192.168.1.113", port)) {

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            pw.println("program");// Greetings with SERVER
            System.out.println("program");


            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Greetings_from_S = br.readLine();
            System.out.println(Greetings_from_S);


            if(Greetings_from_S.equals("ready")) {

                try

                {
                    //BlinkOUT.bin
                    //File file = new File("d:UARTtestingThrowUDP.bin");
                   // BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    //byte [] data = new byte[bis.available()];
                  //  bis.read(data);


                    byte [] data_buffer = new byte[1024];


                    int frames = Net.theBytes.length/1024;
                    System.out.println(frames);
                    int residy = Net.theBytes.length%1024;


                    for (int i = 0; i < frames;i++) {
                        for (int k = 0; k< (1024); k++) {
                            data_buffer[k] = Net.theBytes[k+1024*(i)];
                        }

                       sendingChunk(data_buffer);

                    }
                    byte [] data_buffer2= new byte[residy];
                    for (int i = 0; i < residy;i++) {

                        data_buffer2[i] = Net.theBytes[i+1024*(frames)];
                    }



                    sendingChunk(data_buffer2);

                    pw.println("stop");//
                    System.out.println("stop program");



                } catch (Exception e) {

                    System.out.println(e);

                }


            }


        } catch (Exception e) {

            System.out.println(e);

        }

    }




    public void sendingChunk (byte [] data_buffer){
        try (Socket socket = new Socket("192.168.1.200", 4001)){
            BufferedOutputStream bos = new BufferedOutputStream((socket.getOutputStream()));
            bos.write(data_buffer);
            bos.flush();

            System.out.println(data_buffer.length);
        }
        catch (Exception e) {

            System.out.println(e);

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
               File file = new File("d:arm.bin");


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






}






