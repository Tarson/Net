import java.io.Serializable;

/**
 * Created by m on 18.09.2017.
 */
public class UserP implements Serializable {


   public  String file_path;
   public  String  IP_address;

   static UserP user;



    UserP(String file_path, String IP_address){


        this.file_path = file_path;
        this.IP_address = IP_address;



    };




}



