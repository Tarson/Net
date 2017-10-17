
import java.io.*;
import java.net.*;
import java.util.ArrayList;


import java.util.List;

/**
 * Created by m on 19.03.2017.
 */
public class Net {

    public static  byte theBytes[];
    public static String host="";
    public static String file_name="";
    public static  int port  = 40000; // for the helping TCP client port = port +1
    public static boolean EstablishingConnection = false;
    public static String path="c://tcp_data.txt";
    public static void main(String args[]) {


  /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UploaderGUI().setVisible(true);
            }
        });




        //   try (ServerSocket ss = new ServerSocket(44441)) {
        //      System.out.println(" устанавливаем соединение");
        //        new Http_server(ss.accept());
        //     } catch (Exception e) {
        //       System.out.println("Что-то пошло не так при создании сокета");
        //    }





    }
}


class MakingDots extends Thread{

    MakingDots()
    {start();}

   public void run ()
   {

        int i =0;
       while (!Net.EstablishingConnection)

       {
           try {Thread.sleep(1000);}

           catch(Exception e)
           {}
          if (i==0)
          {i++;}
           else {UploaderGUI.jTextArea1.append(".");}


       }

       Net.EstablishingConnection=false;
       i=0;
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


        String string_fromHexFile  = "";
        String string_final = "";

        try(BufferedReader br = new BufferedReader(new FileReader(Net.file_name)))
        {


            while ((string_fromHexFile = br.readLine()) != null){


                string_fromHexFile = new StringBuilder(string_fromHexFile).delete(0,9).toString();
                int stringLenth = string_fromHexFile.length();
                string_fromHexFile = new StringBuilder(string_fromHexFile).delete(stringLenth-2,stringLenth).toString();
                string_final = string_final+string_fromHexFile;

            }

        }

        catch (Exception e) {


            UploaderGUI.jTextArea1.append("File not found or bad file\r\n");
            UploaderGUI.jTextArea1.append("Browse right file\r\n");
            Button_Handler.upload.setEnabled(true);
            return;

        }


        int nLength = string_final.length();
        Net.theBytes = new byte[nLength / 2];
        for (int i = 0; i < nLength; i += 2) {
            Net.theBytes[i/2] = (byte) ((Character.digit(string_final.charAt(i), 16) << 4) + Character.digit(string_final.charAt(i+1), 16));
        }


        UploaderGUI.jTextArea1.append("File length in bytes "+ Net.theBytes.length+"\r\n");







        UploaderGUI.jTextArea1.append("Trying to establish TCP-connection with " + Net.host+"\r\n");
        new MakingDots();



        try (Socket socket = new Socket(Net.host, port)) {

            Net.EstablishingConnection=true;

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            pw.println("program");// Greetings with SERVER

            UploaderGUI.jTextArea1.append("Program\r\n");



            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Greetings_from_S = br.readLine();

            UploaderGUI.jTextArea1.append(Greetings_from_S+" for begin\r\n");



            if(Greetings_from_S.equals("ready")) {





                byte[] data_buffer = new byte[1024];


                int frames = Net.theBytes.length / 1024;


                UploaderGUI.jTextArea1.append("Quantity of frames is "+ (frames+1)+ "\r\n");

                int residy = Net.theBytes.length % 1024;


                for (int i = 0; i < frames; i++) {
                    for (int k = 0; k < (1024); k++) {
                        data_buffer[k] = Net.theBytes[k + 1024 * (i)];
                    }

                    sendingChunk(data_buffer);

                }
                byte[] data_buffer2 = new byte[residy];
                for (int i = 0; i < residy; i++) {

                    data_buffer2[i] = Net.theBytes[i + 1024 * (frames)];
                }


                sendingChunk(data_buffer2);



                UploaderGUI.jTextArea1.append("stop program\r\n");
                //pw.println("stop program");/ для совместимости с прошлой версией
                pw.println("stop program");//
                Button_Handler.upload.setEnabled(true);



            }


        } catch (Exception e) {


            Net.EstablishingConnection=true;
            UploaderGUI.jTextArea1.append("Refused connection\r\n");
            UploaderGUI.jTextArea1.append("Cannot find host\r\n");
            Button_Handler.upload.setEnabled(true);

        }

    }




    public void sendingChunk (byte [] data_buffer){
        try (Socket socket = new Socket(Net.host, port+1)){
            BufferedOutputStream bos = new BufferedOutputStream((socket.getOutputStream()));
            bos.write(data_buffer);
            bos.flush();
            UploaderGUI.jTextArea1.append(" "+data_buffer.length+"\r\n");

        }
        catch (Exception e) {

            UploaderGUI.jTextArea1.append("cannot send bytes to ESP\r\n");

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






