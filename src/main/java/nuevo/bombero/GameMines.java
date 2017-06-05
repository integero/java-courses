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

    //  консольная версия
    void run() {
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
        int choisResult = ce.makeJob(thinkBombChois);
        if (choisResult == 0) return continueGame;
        //        если не бомба,
        if (choisResult == 1) {    // не бомба, открываем всё, что возможно
            if (ce.getCountNeighbors() == 0)
                cellsOpen(xChois, yChois);
            else {
                ce.setOpen(true);
                openedCells++;
            }
        } else {    // БОМБА или пометка.
            if (ce.isMine()) continueGame = -1;
        }
        if (openedCells == cellsWithoutBombs) continueGame = 1;
        return continueGame;
    }

    //  открытие ячеек
    private void cellsOpen(int x, int y) {
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

    //  установка мин и подсчет мин в соседних ячейках
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

    //  находится ли координаты (x,y) в поле игры
    private boolean isIn(int x, int y) {
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) return false;
        return true;
    }

    //  подготовка информации для вывода на консоль(GUI) в зависимости от ситуации (-1- взрыв,0- продолжение, 1- победа)
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
    }

    //  выбор изображениябомбы в зависимости от ситуации
    private String mineSign(Cell ce, int loseStepWin) {
        String s;
        if (ce.isOpen()) {
            s = (loseStepWin == -1) ? "Z" : (loseStepWin == 0) ? "b" : "B";
        } else {
            s = (loseStepWin == 1) ? "b" : (ce.isMarktAsBomb()) ? "b" : "B";
        }
        return s;
    }

    //  ввод координат и действия с консоли, если третьего параметра нет или он равен 0 - открываем ячейку
//  иаче помечаем-распомечаем
    private void getChois() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                String[] s = reader.readLine().split(" ");
                if (s.length < 2) continue;
                xChois = Integer.parseInt(s[1]);
                yChois = Integer.parseInt(s[0]);
                thinkBombChois = (s.length == 2) ? false : (Integer.parseInt(s[2]) == 0) ? false : true;
            } catch (IOException e) {
            }catch(NumberFormatException e){
                xChois = -1;
                yChois = -1;
            }
        } while (!isIn(xChois,yChois));
    }

    //  вывод в консоль
    private void print() {
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
