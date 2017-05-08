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

            System.out.println("���-�� ����� �� ��� ��� �������� ������");
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

            System.out.println(s+"   ���������");

            String ss = br.readLine();

            System.out.println(ss+"   ���� ���������");

        } catch (Exception e) {

            System.out.println("�� ������� ������� ������ �� �������");
        }


        // System.out.println("����-����");
    }


}








    class Http_client{
    int port;

    Http_client(int port){

        this.port = port;

        try (Socket socket = new Socket("192.168.1.35", port)) {

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("������ ������");

            pw.println("������ ������ ����� �������");
            pw.flush();



        } catch (Exception e) {

            System.out.println("�� ������� �� ������ �� �������");

        }


    }


    }






