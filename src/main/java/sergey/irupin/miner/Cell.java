package sergey.irupin.miner;

/**
 * Created by Padre on 26.05.2017.
 */
public class Cell {
    private boolean isMine;
    private boolean isOpen;
//    private boolean isBlown;
    private boolean isMarktAsBomb;
    private int countNeighbors;

    public boolean isMarktAsBomb() {
        return isMarktAsBomb;
    }

//    public void setMarktAsBomb(boolean marktAsBomb) {
//        isMarktAsBomb = marktAsBomb;
//    }

//    public boolean isBlown() {
//        return isBlown;
//    }

//    public void setBlown(boolean exploid) {
//        isBlown = exploid;
//    }

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

    public boolean makeJob(boolean markAsBomb) {
        if (markAsBomb) {       //помечаем-распомечаем ячейку - бомба
            this.isMarktAsBomb=!this.isMarktAsBomb;
            return false;
        }
//        открываем ячейку
        if (!this.isMine) { // если не бомба, говорим,что надо открыть её и окружение
            return true;
        } else {            // открываем бомбу, обрабоку передаём наверх
            this.isOpen = true;
//            this.isBlown=true;
            return false;
        }
    }
}
