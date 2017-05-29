package sergey.irupin.miner;

/**
 * Created by Padre on 26.05.2017.
 */
public class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isBlown;
    private boolean isMarktAsBomb;
    private int countNeighbors;

    public boolean isMarktAsBomb() {
        return isMarktAsBomb;
    }

    public void setMarktAsBomb(boolean marktAsBomb) {
        isMarktAsBomb = marktAsBomb;
    }

    public boolean isBlown() {
        return isBlown;
    }

    public void setBlown(boolean exploid) {
        isBlown = exploid;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getCountNeighbors() {
        return countNeighbors;
    }

    public void setCountNeighbors(int countNeighbors) {
        this.countNeighbors = countNeighbors;
    }


    public Cell() {
    }

    public boolean makeJob(Cell cell,boolean isMine) {
        if (isMine) {       //помечаем-распомечаем ячейку - бомба
            this.isMarktAsBomb=!this.isMarktAsBomb;
//            cell.setMarktAsBomb(!cell.isMarktAsBomb());
            return false;
        }
        if (!this.isMine) { //открываем, если не бомба
//            cellsOpen(xt, yt);
            return true;
        } else {
            this.setBlown(true);
            return false;
        }

    }
}
