package sergey.irupin.miner;

/**
 * Created by Padre on 31.05.2017.
 */
public class Print implements PrintField{

    public Print(String[][] field, int boardSize) {
        print(field,boardSize);
    }

    public void print(String[][] field, int boardSize) {
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++)
            System.out.print("(" + i + ")");
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print("(" + i + ")");
            for (int j = 0; j < boardSize; j++) {

                System.out.printf("[%s]",field[i][j]);
            }
            System.out.print("(" + i + ")");
            System.out.println();
        }
    System.out.print("   ");
    for (int i = 0; i < boardSize; i++)
            System.out.print("(" + i + ")");
        System.out.println();
}
}
