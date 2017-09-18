import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by m on 18.09.2017.
 */
public class Button_Handler implements ActionListener{

    JButton b;



    Button_Handler(JButton b){

        this.b = b;

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (b.getText().equals("Upload")) {

            UploaderGUI.jTextArea1.setText("Upload\r\n");
            b.setEnabled(false);
            UploaderGUI.jTextArea1.append("Trying to establish TCP-connection with "+ Net.host);


        }
        if (b.getText().equals("Browse")) {

            System.out.println("Browse");
        }

        if (b.getText().equals("Stop")) {

           UploaderGUI.uploaderGUI.dispose();

            System.out.println("Stop");
        }





    }



}






