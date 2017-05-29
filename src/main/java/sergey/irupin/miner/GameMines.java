package sergey.irupin.miner;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.xml.internal.ws.wsdl.parser.InaccessibleWSDLException;

import java.io.*;

/**
 * Created by Padre on 26.05.2017.
 */
public class GameMines {
    private Cell[][] field;
    private int boardSize;
    private int nuberOfMines;

    private int xt;
    private int yt;
    private boolean markBomb;
    private int[] dy={-1,-1,-1,0,0,1,1,1};
    private int[] dx={-1,0,1,-1,1,-1,0,1};

    GameMines(int boardSize,int nuOfMi) {
        this.boardSize=boardSize;
        this.nuberOfMines = nuOfMi;
        this.field=new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                field[j][i]=new Cell();
            }
        }
        run();
    }

    private void run() {
        initMines(nuberOfMines);
        do{
            paint();
        }while (oneStep());
    }

    boolean oneStep() {
        getChois(); // получаем координаты и действие
        Cell ce = field[yt][xt];
        if (markBomb) {       //помечаем-распомечаем ячейку - бомба
            ce.setMarktAsBomb(!ce.isMarktAsBomb());
            return true;
        }
        if (!ce.isMine()) { //открываем, если не бомба
            cellsOpen(xt, yt);
            return true;
        } else {
            ce.setBlown(true);
            return false;
        }

//        return true;
    }

    void cellsOpen(int xt, int yt) {
        if (!isIn(xt,yt))return;
        if (field[yt][xt].isOpen()) return;
        if (field[yt][xt].isMine()) return;
        field[yt][xt].setOpen(true);
        for (int i = 0; i < 8; i++) {
            cellsOpen(xt+dx[i],yt+dy[i]);
        }
    }

    void getChois() {
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        try {
            do {
                String[] s=reader.readLine().split(" ");
                xt = Integer.parseInt(s[0]);
                yt = Integer.parseInt(s[1]);
                markBomb = (Integer.parseInt(s[2]) == 0) ? false : true;
            } while (!isIn(xt,yt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initMines(int nMines){
        int coM=0;
        while (coM != nMines) {
            boolean secces = false;
            do {
                int x = (int) (Math.random() * boardSize);
                int y = (int) (Math.random() * boardSize);
                if (field[y][x].isMine()) continue;
                field[y][x].setMine(true);       //при вариации с бомбами добавить в Cell бомбы и их инициацию
                for (int i = 0; i < 8; i++) {
                    int xdx = x + dx[i];
                    int ydy = y + dy[i];
                    if (isIn(xdx,ydy)) field[ydy][xdx].setCountNeighbors(field[ydy][xdx].getCountNeighbors()+1);
                }
                secces = true;
                coM++;
            } while (!secces);
        }
    }

    private boolean isIn(int x, int y) {
        if (x<0||x>=boardSize||y<0||y>=boardSize) return false;
        return true;
    }

    public void paint() {
        String sPrint;
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++)
            System.out.print("("+i+")");
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print("("+i+")");
            for (int j = 0; j < boardSize; j++) {
                Cell ce = field[j][i];
                if (ce.isOpen()) {
                    if (ce.getCountNeighbors() == 0) {
                        sPrint = "[ ]";
                    } else {
                        sPrint = "[" + ce.getCountNeighbors() + "]";
                    }
                } else {
                    if (ce.isMarktAsBomb()) {
                        sPrint = "[F]";
                    } else {
                        sPrint="[+]";
                    }
                }
                System.out.print(sPrint);
            }
            System.out.println();
        }
        }
}
