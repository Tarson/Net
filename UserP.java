import java.io.Serializable;

/**
 * Created by m on 18.09.2017.
 */
public class UserP implements Serializable {

   String file_path;
   int  IP_address;
   int  TCPport;



    UserP(String file_path, int IP_address, int TCPport){


        this.file_path = file_path;
        this.IP_address = IP_address;
        this.TCPport = TCPport;


    };




}



