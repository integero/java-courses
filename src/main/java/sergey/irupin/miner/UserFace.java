package sergey.irupin.miner;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Padre on 29.05.2017.
 */
public class UserFace implements InputFace{
    public void getChois(Uchoise choise,int boardSize) {
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        int x=-1;
        int y=-1;
        boolean bomb=false;
        do {
            try {
                String[] s=reader.readLine().split(" ");
                if (s.length<2) continue;
                x = Integer.parseInt(s[0]);
                y= Integer.parseInt(s[1]);
                bomb = (s.length==2)?false:(Integer.parseInt(s[2]) == 0) ? false : true;
            } catch (IOException e) {}
        } while (x<0||x>=boardSize||y<0||y>=boardSize);
        choise.x=x;
        choise.y=y;
        choise.bomb=bomb;
    }
}