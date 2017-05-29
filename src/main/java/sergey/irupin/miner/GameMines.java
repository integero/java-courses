package sergey.irupin.miner;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.xml.internal.ws.wsdl.parser.InaccessibleWSDLException;

import java.io.*;

/**
 * Created by Padre on 26.05.2017.
 */
public class GameMines {
    private Board board;
    private int boardSize;
    private int nuberOfMines;
    private boolean isMine;

    private int xt;
    private int yt;
    private int[] dy={-1,-1,-1,0,0,1,1,1};
    private int[] dx={-1,0,1,-1,1,-1,0,1};

    GameMines(int boardSize,int nuOfMi) {
        this.boardSize=boardSize;
        this.nuberOfMines = nuOfMi;
        board=new Board(boardSize);
        run();

    }

    private void run() {
        initMines(nuberOfMines);
        paint();
        oneStep();
    }

    boolean oneStep() {
        getChois();
        Cell ce = board.getCell(xt, yt);
        if (isMine) {       //помечаем-распомечаем ячейку - бомба
            ce.setMarktAsBomb(!ce.isMarktAsBomb());
            return true;
        }
        if (!ce.isMine()) { //открываем, если не бомба
            cellsOpen(xt,yt);
        }

        return true;
    }

    void cellsOpen(int xt, int yt) {
        if (board.getCell(xt,yt).isOpen()||!isIn(xt,yt)) return;
        for (int i = 0; i < 8; i++) {
            cellsOpen(xt+dx[i],yt+dy[i]);
        }
    }
    void getChois() {
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
//        String[] s;
        try {
            do {
                String[] s=reader.readLine().split(" ");
                xt = Integer.parseInt(s[1]);
                yt = Integer.parseInt(s[2]);
                isMine = (Integer.parseInt(s[3]) == 0) ? false : true;
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
                if (board.getCell(x,y).isMine()) continue;
                board.getCell(x,y).setMine(true);       //при вариации с бомбами добавить в Cell бомбы и их инициацию
                for (int i = 0; i < 8; i++) {
                    int xdx = x + dx[i];
                    int ydy = y + dy[i];
                    if (isIn(xdx,ydy)) board.getCell(xdx,ydy).setCountNeighbors(board.getCell(xdx,ydy).getCountNeighbors()+1);
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
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getCell(i,j).isMine()) System.out.print("* ");
                else
                System.out.print(board.getCell(i,j).getCountNeighbors()+" ");
            }
            System.out.println();
        }
    }
}
