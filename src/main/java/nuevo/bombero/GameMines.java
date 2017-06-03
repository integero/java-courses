package nuevo.bombero;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameMines {
    private Cell[][] field;
    private int boardSize;
    private int nuberOfMines;
    private int openedCells;
    private int cellsWithoutBombs;

     String[][] prFi;
     int xChois,yChois;
     boolean thinkBombChois;

    GameMines(int boardSize, int nuOfMi) {
        this.boardSize = boardSize;
        this.nuberOfMines = nuOfMi;
        this.openedCells = 0;
        this.cellsWithoutBombs = boardSize * boardSize - nuOfMi;
        this.field = new Cell[boardSize][boardSize];
        this.prFi = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                field[j][i] = new Cell();
    }


    public void run() {
        initMines(nuberOfMines);
        do {
            paintPrepare(0);
            print();
            getChois(); // получаем координаты и действие xt,yt,markBomb

        } while (oneStep()==0);
        if (openedCells == cellsWithoutBombs)
            paintPrepare(1);
        else
            paintPrepare(-1);
        print();
    }

    int oneStep() {
        int continueGame = 0;
        Cell ce = field[xChois][yChois];
        if (ce.isOpen()) return continueGame;
        //        если не бомба,
        if (ce.makeJob(thinkBombChois)) {    // не бомба, открываем всё, что возможно
            if (!ce.isMarktAsBomb()){
            if (ce.getCountNeighbors() == 0)
                cellsOpen(xChois, yChois);
            else {
                ce.setOpen(true);
                openedCells++;
            }
            }

        } else {    // БОМБА или пометка. Если БОМБА, решаем что делать
            if (ce.isMine())
                continueGame = -1;
//                continueGame = bombOpen(ce);
        }
        if (openedCells == cellsWithoutBombs) continueGame = 1;

        return continueGame;
    }

    void cellsOpen(int x, int y) {
        if (!isIn(x, y)) return;
        Cell ce = field[x][y];
        if (ce.isOpen() || ce.isMine()) return;
        ce.setOpen(true);
        this.openedCells++;
        if (ce.getCountNeighbors() > 0) return;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                cellsOpen(i, j);
            }
        }
    }

    void initMines(int nMines) {
        Cell ce;
        int coM = 0;
        while (coM != nMines) {
            boolean secces = false;
            do {
                int x = (int) (Math.random() * boardSize);
                int y = (int) (Math.random() * boardSize);
                ce = field[x][y];
                if (ce.isMine()) continue;
                ce.setMine(true);       //при вариации с бомбами добавить в Cell бомбы и их инициацию
                for (int i = x - 1; i < x + 2; i++) {
                    for (int j = y - 1; j < y + 2; j++) {
                        if (isIn(i, j)) field[i][j].setCountNeighbors(field[i][j].getCountNeighbors() + 1);
                    }
                }
                secces = true;
                coM++;
            } while (!secces);
        }
    }   // похоже надо вынести

    protected boolean isIn(int x, int y) {
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) return false;
        return true;
    }

//    private int bombOpen(Cell ce) {
//        return !(ce.isMine() && ce.isOpen());
//    }


    public void paintPrepare(int loseStepWin) {

        String s;
        Cell ce;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ce = field[i][j];
                if (ce.isOpen()) {
                    if (ce.isMine()) {
                        s = mineSign(ce, loseStepWin);//обработка бомбы
                    } else {
                        s = (ce.getCountNeighbors() == 0) ? " " : String.valueOf(ce.getCountNeighbors());
                    }
                } else {
                    if (!ce.isMine()) {
                        s = (!ce.isMarktAsBomb()) ? "+" : (loseStepWin == 0) ? "F" : "f";
                    } else
                        s = (loseStepWin == 0) ? ((ce.isMarktAsBomb()) ? "F" : "+") : mineSign(ce, loseStepWin);
                }
                prFi[i][j] = s;
            }
        }
//        print();
    }

    String mineSign(Cell ce, int loseStepWin) {
        String s;
        if (ce.isOpen()) {
            s = (loseStepWin == -1) ? "Z" : (loseStepWin == 0) ? "b" : "B";
        } else {
            s = (loseStepWin == 1) ? "b" : (ce.isMarktAsBomb()) ? "b" : "B";
        }
        return s;
    }

    public void getChois() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                String[] s = reader.readLine().split(" ");
                if (s.length < 2) continue;
                xChois = Integer.parseInt(s[0]);
                yChois = Integer.parseInt(s[1]);
                thinkBombChois = (s.length == 2) ? false : (Integer.parseInt(s[2]) == 0) ? false : true;
            } catch (IOException e) {
            }catch(NumberFormatException e){
                xChois = -1;
                yChois = -1;
            }

        } while (!isIn(xChois,yChois));
    }

    public void print() {
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++)
            System.out.print("(" + i + ")");
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print("(" + i + ")");
            for (int j = 0; j < boardSize; j++) {

                System.out.printf("[%s]",prFi[i][j]);
            }
            System.out.print("(" + i + ")");
            System.out.println();
        }
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++)
            System.out.print("(" + i + ")");
        System.out.println();
    }
}
