package nuevo.bombero;

/**
 * Created by Padre on 03.06.2017.
 */
public class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isMarktAsBomb;
    private int countNeighbors;

    public boolean isMarktAsBomb() {
        return isMarktAsBomb;
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

    public boolean makeJob(boolean markAsBomb) {
        if (markAsBomb) {       //помечаем-распомечаем ячейку - бомба
            this.isMarktAsBomb=!this.isMarktAsBomb;
            return true;
//            return false;
        }
//        открываем ячейку
        if (!this.isMine) { // если не бомба, говорим,что надо открыть её и окружение
            return true;
        } else {            // открываем бомбу, обрабоку передаём наверх
            this.isOpen = true;
            return false;
        }
    }
}

