
package cinemasystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class CinemaSystem {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        /*admin a = new admin("Ali","42101875412369","ali","ali");
        a.write();
        RandomAccessFile file = new RandomAccessFile("hallDetails.dat","rw");
                file.writeInt(12*21);
                file.writeDouble(500);
                file.writeInt(9*18);
                file.writeDouble(750);
                file.writeInt(7*18);
                file.writeDouble(1000);*/
        //System.out.println("hi" + "\n\n" + "hello");
        //goldHall h = new goldHall(5);
        silverHall h = new silverHall(7);
        new Home().setVisible(true);
    }
    
}
