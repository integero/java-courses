package sergey.irupin.miner;

/**
 * Created by Padre on 26.05.2017.
 */
public class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isExploid;
    private boolean isMarktAsBomb;
    private int countNeighbors;
    private boolean isFlag;

    public boolean isMarktAsBomb() {
        return isMarktAsBomb;
    }

    public void setMarktAsBomb(boolean marktAsBomb) {
        isMarktAsBomb = marktAsBomb;
    }

    public boolean isExploid() {
        return isExploid;
    }

    public void setExploid(boolean exploid) {
        isExploid = exploid;
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

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getCountNeighbors() {
        return countNeighbors;
    }

    public void setCountNeighbors(int countNeighbors) {
        this.countNeighbors = countNeighbors;
    }


    public Cell() {
//        this.isMine = false;
//        this.isOpen = false;
//        this.isFlag = false;
//        this.countNeighbors = 0;
    }
}
