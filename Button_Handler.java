import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by m on 18.09.2017.
 */
public class Button_Handler implements ActionListener{
    public static JButton upload;

    JButton b;
    UserP user;


    Button_Handler(JButton b){

        this.b = b;


    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (b.getText().equals("Upload")) {



          UserP.user.file_path =UploaderGUI.jTextField3.getText();
          UserP.user.IP_address = UploaderGUI.jTextField1.getText();

            try(ObjectOutputStream objOS = new ObjectOutputStream((new FileOutputStream("d://tcp_data.txt")))){

               objOS.writeObject(UserP.user);

            }

            catch(IOException er){

                System.out.println("хуюшки с cериализацией");

            }





            UploaderGUI.jTextArea1.setText("Upload\r\n");
            b.setEnabled(false);


            Net.host=UserP.user.IP_address;
            Net.file_name=UserP.user.file_path;

            new Http_client(Net.port);

            upload = b;








        }
        if (b.getText().equals("Browse")) {


            JFileChooser fch = new JFileChooser();
            fch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fch.showDialog( null, "Open");
            File file = fch.getSelectedFile();
            String file_name = file.getPath();
            UploaderGUI.jTextField3.setText(file_name);


        }

        if (b.getText().equals("Stop")) {

           UploaderGUI.uploaderGUI.dispose();

            System.out.println("Stop");
        }





    }



}






