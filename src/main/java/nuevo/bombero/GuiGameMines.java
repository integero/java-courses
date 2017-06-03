package nuevo.bombero;

/**
 * Created by Padre on 03.06.2017.
 */
public class GuiGameMines extends GameMines {
    GuiGameMines(int boardSize, int nuOfMi) {
        super(boardSize, nuOfMi);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                prFi[i][j]="+";
            }
        }
    }
}
