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
    private UserFace userF=new UserFace();

    private int xt;
    private int yt;
    private boolean markBomb;

    public void setXt(int xt) {
        this.xt = xt;
    }

    public void setYt(int yt) {
        this.yt = yt;
    }

    public void setMarkBomb(boolean markBomb) {
        this.markBomb = markBomb;
    }

    GameMines(int boardSize, int nuOfMi) {
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
        userF.getChois(boardSize); // получаем координаты и действие
        field[yt][xt].makeJob(markBomb);

//        return true;
    }

    void cellsOpen(int xt, int yt) {
        if (!isIn(xt,yt))return;
        if (field[yt][xt].isOpen()) return;
        if (field[yt][xt].isMine()) return;
        field[yt][xt].setOpen(true);
        for (int i =xt-1  ; i < xt+2; i++) {
            for (int j = yt - 1; j < yt + 2; j++) {
                cellsOpen(i,j);
            }
        }
    }



    void initMines(int nMines){
        Cell ce;
        int coM=0;
        while (coM != nMines) {
            boolean secces = false;
            do {
                int x = (int) (Math.random() * boardSize);
                int y = (int) (Math.random() * boardSize);
                ce=field[y][x];
                if (ce.isMine()) continue;
                ce.setMine(true);       //при вариации с бомбами добавить в Cell бомбы и их инициацию
                for (int i =x-1  ; i < x+2; i++) {
                    for (int j = y-1; j < y+2; j++) {
                        if (isIn(i,j)) ce.setCountNeighbors(ce.getCountNeighbors()+1);
                    }
                }
                secces = true;
                coM++;
            } while (!secces);
        }
    }

//    private boolean isIn(int x, int y) {
    protected boolean isIn(int x, int y) {
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
