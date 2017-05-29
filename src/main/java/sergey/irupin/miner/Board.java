package sergey.irupin.miner;

/**
 * Created by Padre on 26.05.2017.
 */
public class Board {
    private Cell[][] cells;
    private int boardSize;
    Board(int boSi){
        this.boardSize = boSi;
        this.cells=new Cell[boSi][boSi];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[j][i]=new Cell();
            }
        }
    }

    public Cell getCell(int i,int j) {
        return cells[j][i];
    }

    public void setCell(Cell cell) {
        this.cells = cells;
    }
}
