package sergey.irupin.miner;

/**
 * Created by Padre on 02.06.2017.
 */
public class GuiMain {
    static GrMines guiMines = new GrMines(6,6);
    public static void main(String[] args) {
        guiMines.gm.run();
    }
}
