package nuevo.bombero;

class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isMarktAsBomb;
    private int countNeighbors;

    boolean isMarktAsBomb() {
        return isMarktAsBomb;
    }

    boolean isMine() {
        return isMine;
    }

    void setMine(boolean mine) {
        isMine = mine;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setOpen(boolean open) {
        isOpen = open;
    }

    int getCountNeighbors() {
        return countNeighbors;
    }

    void setCountNeighbors(int countNeighbors) {
        this.countNeighbors = countNeighbors;
    }


    Cell() {
    }

    int makeJob(boolean markAsBomb) {
        if (markAsBomb) {       //помечаем-распомечаем ячейку - бомба
            this.isMarktAsBomb=!this.isMarktAsBomb;
            return 0;
        }
//        открываем ячейку
        if (!this.isMine) { // если не бомба, говорим,что надо открыть её и окружение
            return 1;
        } else {            // открываем бомбу, обрабоку передаём наверх
            this.isOpen = true;
            return -1;
        }
    }
}

