package sergey.irupin.miner;


/**
 * Created by Padre on 26.05.2017.
 */
public class GameMines {
    private Cell[][] field;
    private int boardSize;
    private int nuberOfMines;
    private int openedCells;
    private int cellsWithoutBombs;
    private UserFace userF;
    private Uchoise cho;
    private PrintPrepare pf;

    public int getBoardSize() {
        return boardSize;
    }

    GameMines(int boardSize, int nuOfMi) {
        this.boardSize=boardSize;
        this.nuberOfMines = nuOfMi;
        this.openedCells=0;
        this.cellsWithoutBombs=boardSize*boardSize-nuOfMi;
        this.userF = new UserFace();
        this.cho = new Uchoise();
        this.field=new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                field[j][i]=new Cell();
        pf = new PrintPrepare();
        run();
    }

    public void run() {
        initMines(nuberOfMines);
        do{
            pf.paintPrepare(field,boardSize,0);
//            pf=new PrintPrepare(field,boardSize,0);
        }while (oneStep());
        if (openedCells==cellsWithoutBombs)
            pf.paintPrepare(field,boardSize,1);
//            pf=new PrintPrepare(field,boardSize,1);
        else
            pf.paintPrepare(field, boardSize, -1);
//            pf=new PrintPrepare(field,boardSize,-1);
    }

    boolean oneStep() {
        boolean continueGame=true;
        userF.getChois(cho,boardSize); // получаем координаты и действие xt,yt,markBomb
//        если не бомба,
        if (field[cho.x][cho.y].makeJob(cho.bomb)) {    // не бомба, открываем всё, что возможно
            if (field[cho.x][cho.y].getCountNeighbors() == 0)
                cellsOpen(cho.x, cho.y);
            else {
                field[cho.x][cho.y].setOpen(true);
                openedCells++;
            }
//            continueGame = true;
        } else {    // БОМБА или пометка. Если БОМБА, решаем что делать
            if (field[cho.x][cho.y].isMine())
            continueGame=bombOpen(field[cho.x][cho.y]);
        }
        System.out.println(openedCells+" * "+cellsWithoutBombs);
        if (openedCells==cellsWithoutBombs) continueGame = false;

            return continueGame;
    }

    void cellsOpen(int x, int y) {
        if (!isIn(x,y))return;
        Cell ce = field[x][y];
        if (ce.isOpen()||ce.isMine()) return;
        ce.setOpen(true);
        this.openedCells++;
        if (ce.getCountNeighbors()>0) return;
        for (int i =x-1  ; i < x+2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
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
                ce=field[x][y];
                if (ce.isMine()) continue;
                ce.setMine(true);       //при вариации с бомбами добавить в Cell бомбы и их инициацию
                for (int i =x-1  ; i < x+2; i++) {
                    for (int j = y-1; j < y+2; j++) {
                        if (isIn(i,j)) field[i][j].setCountNeighbors(field[i][j].getCountNeighbors()+1);
                    }
                }
                secces = true;
                coM++;
            } while (!secces);
        }
    }   // похоже надо вынести

    protected boolean isIn(int x, int y) {
        if (x<0||x>=boardSize||y<0||y>=boardSize) return false;
        return true;
    }

    private boolean bombOpen(Cell ce) {
        return !(ce.isMine() && ce.isOpen());
    }
}
