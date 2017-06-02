package sergey.irupin.miner;

/**
 * Created by Padre on 30.05.2017.
 */
public class PrintPrepare {
    public PrintPrepare() {
    }
    public PrintPrepare(Cell[][] field, int boardSize, int loseStepWin) {
        paintPrepare(field, boardSize,loseStepWin);
    }

    public void paintPrepare(Cell[][] field, int boardSize, int loseStepWin)
    {
        String[][] prFi = new String[boardSize][boardSize];
        String s;
        Cell ce;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ce = field[i][j];
                if (ce.isOpen()) {
                    if (ce.isMine()) {
                        s = mineSign(ce, loseStepWin);//обработка бомбы
                    }else {
                        s = (ce.getCountNeighbors() == 0) ? " " : String.valueOf(ce.getCountNeighbors());
                    }
                } else {
                    if (!ce.isMine()) {
                        s = (!ce.isMarktAsBomb()) ? "+" : (loseStepWin == 0) ? "F" : "f";
                    }
                    else
                        s = (loseStepWin==0)?((ce.isMarktAsBomb())?"F":"+"):mineSign(ce,loseStepWin);
                }
                prFi[i][j]=s;
            }
        }
        Print print=new Print(prFi,boardSize);
     }

    String mineSign(Cell ce,int loseStepWin) {
        String s;
        if (ce.isOpen()) {
            s = (loseStepWin == -1) ? "Z" : (loseStepWin == 0) ? "b" : "B";
        } else {
            s=(loseStepWin == 1) ? "b" :(ce.isMarktAsBomb())? "b":"B";
        }
        return s;
    }
}
