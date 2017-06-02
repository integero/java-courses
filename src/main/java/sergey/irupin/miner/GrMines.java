package sergey.irupin.miner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Padre on 02.06.2017.
 */
public class GrMines extends JFrame {

    final String TITLE_OF_PROGRAM = "Mines";
    final int BLOCK_SIZE = 30; // size of one block
    private int FIELD_SIZE = 9; // in blocks
    //    final int FIELD_SIZE = 9; // in blocks
    final int FIELD_DX = 6; // determined experimentally
    final int FIELD_DY = 28 + 17;
    final int START_LOCATION = 200;
    final int MOUSE_BUTTON_LEFT = 1; // for mouse listener
    final int MOUSE_BUTTON_RIGHT = 3;
    final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0};
    //    Cell[][] field = new Cell[FIELD_SIZE][FIELD_SIZE];
    int fiSi;
    String[][] gField;
    final Canvas canvas = new Canvas();
    GameMines gm;
    public GrMines(int fSize, int nMines) throws HeadlessException {
        this.fiSi=fSize;

        gField = new String[fiSi][fiSi];
        for (int i = 0; i < fiSi; i++) {
            for (int j = 0; j < fiSi; j++) {
                gField[i][j]="+";
            }
        }
        FIELD_SIZE = fSize;

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_SIZE * BLOCK_SIZE + FIELD_DX, FIELD_SIZE * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
//        final TimerLabel timeLabel = new TimerLabel();
//        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        canvas.setBackground(Color.white);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX() / BLOCK_SIZE;
                int y = e.getY() / BLOCK_SIZE;
//                if (!bangMine && !youWon) {
                if (e.getButton() == MOUSE_BUTTON_LEFT) // left button mouse
//                        if (field[y][x].isNotOpen()) {
//                            openCells(x, y);
//                            youWon = countOpenedCells == FIELD_SIZE*FIELD_SIZE - NUMBER_OF_MINES; // winning check
//                            if (bangMine) {
//                                bangX = x;
//                                bangY = y;
//                            }
//                        }
//                    if (e.getButton() == MOUSE_BUTTON_RIGHT) field[y][x].inverseFlag(); // right button mouse
//                    if (bangMine || youWon) timeLabel.stopTimer(); // game over
                {
                    canvas.repaint();}
                else
                    if (e.getButton() == MOUSE_BUTTON_RIGHT)
                        canvas.repaint();
//                }
            }

        });
        add(BorderLayout.CENTER, canvas);
//        add(BorderLayout.SOUTH, timeLabel);
        setVisible(true);
        gm = new GameMines(fSize, nMines);

//        initField();
    }

    void rep(String[][] field) {
        gField = field;
//        canvas.paint(g,);
//        canvas.repaint();
    }
    void ask(){
        canvas.requestFocus();
    }
    void initField() {}
    class Canvas extends JPanel { // my canvas for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < FIELD_SIZE; x++)
                for (int y = 0; y < FIELD_SIZE; y++){
                    g.setColor(Color.lightGray);
                    g.fill3DRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
                    paintString(g,gField[x][y], x, y,Color.blue);
                }
//                for (int y = 0; y < FIELD_SIZE; y++) field[y][x].paint(g, x, y);
        }

        void paintBomb(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x*BLOCK_SIZE + 7, y*BLOCK_SIZE + 10, 18, 10);
            g.fillRect(x*BLOCK_SIZE + 11, y*BLOCK_SIZE + 6, 10, 18);
            g.fillRect(x*BLOCK_SIZE + 9, y*BLOCK_SIZE + 8, 14, 14);
            g.setColor(Color.white);
            g.fillRect(x*BLOCK_SIZE + 11, y*BLOCK_SIZE + 10, 4, 4);
        }

        void paintString(Graphics g, String str, int x, int y, Color color) {
            g.setColor(color);
            g.setFont(new Font("", Font.BOLD, BLOCK_SIZE));
            if (str!=null)
                g.drawString(str, x * BLOCK_SIZE + 8, y * BLOCK_SIZE + 26);
            else
                g.drawString(" ", x * BLOCK_SIZE + 8, y * BLOCK_SIZE + 26);
        }

        void paint(Graphics g, int x, int y) {
            String bbb="ZBb";
            int k = bbb.indexOf(gField[x][y]);

//            g.setColor(Color.lightGray);
//            g.drawRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
//            if (ss.equals(" "))
//            if (!isOpen) {
//                if ((bangMine || youWon) && isMine) paintBomb(g, x, y, Color.black);
//                else {
//                    g.setColor(Color.lightGray);
//                    g.fill3DRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
//                    if (isFlag) paintString(g, SIGN_OF_FLAG, x, y, Color.red);
//                }
//            } else
//            if (isMine) paintBomb(g, x, y, bangMine? Color.red : Color.black);
//            else
//            if (countBombNear > 0)
//                paintString(g, Integer.toString(countBombNear), x, y, new Color(COLOR_OF_NUMBERS[countBombNear - 1]));
        }

    }

}
