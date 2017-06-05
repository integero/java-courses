package nuevo.bombero;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;

class GuiMain extends JFrame {

    final String TITLE_OF_PROGRAM = "Mines";
    final int BLOCK_SIZE = 30; // size of one block
    final int FIELD_SIZE = 9; // in blocks
    final int FIELD_DX = 6; // determined experimentally
    final int FIELD_DY = 28 + 17;
    final int START_LOCATION = 200;
    final int NUMBER_OF_MINES = 10;
    final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0};
    private boolean youWon, bangMine; // flags for win and bang/fail
    final GuiGameMines gm;

    public static void main(String[] args) {
        new GuiMain();
    }

    GuiMain() {
        gm = new GuiGameMines(FIELD_SIZE, NUMBER_OF_MINES);
        gm.initMines(NUMBER_OF_MINES);

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_SIZE * BLOCK_SIZE + FIELD_DX, FIELD_SIZE * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        final TimerLabel timeLabel = new TimerLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        youWon = false;
        bangMine = false;
        final Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!bangMine && !youWon) {
                    int m = e.getButton() - 1;
                    gm.xChois = e.getX() / BLOCK_SIZE;
                    gm.yChois = e.getY() / BLOCK_SIZE;
                    gm.thinkBombChois = ((m != 0) ? true : false);
                    int cont = gm.oneStep();
                    gm.paintPrepare(cont);
                    youWon = cont > 0;
                    bangMine = cont < 0;
                    canvas.repaint();
                }
                if (bangMine || youWon)
                    timeLabel.stopTimer(); // game over
            }
        });
        add(BorderLayout.CENTER, canvas);
        add(BorderLayout.SOUTH, timeLabel);
        setVisible(true);
    }

    class Celll { // playing field cell

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
            g.drawString(str, x*BLOCK_SIZE + 8, y*BLOCK_SIZE + 26);
        }

        void paint(Graphics g, int x, int y) {
            g.setColor(Color.lightGray);
            g.drawRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            String ss = gm.prFi[x][y];
            if(ss.equals("+")) {
                g.setColor(Color.lightGray);
                g.fill3DRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
                return;
            }
            if(ss.equals("F")) {
                g.setColor(Color.lightGray);
                g.fill3DRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
                paintString(g, ss, x, y, Color.red);
                return;
            }
            if(ss.equals("Z")){paintBomb(g, x, y, Color.red);return;}
            if(ss.equals("B")){paintBomb(g, x, y, Color.black);return;}
            if(ss.equals("b")){paintBomb(g, x, y, Color.gray);return;}
            paintString(g, ss, x, y, Color.red);
        }
    }

    class TimerLabel extends JLabel { // label with stopwatch
        Timer timer = new Timer();

        TimerLabel() { timer.scheduleAtFixedRate(timerTask, 0, 1000); } // TimerTask task, long delay, long period

        TimerTask timerTask = new TimerTask() {
            volatile int time;
            Runnable refresher = new Runnable() {
                public void run() {
                    TimerLabel.this.setText(String.format("%02d:%02d", time / 60, time % 60));
                }
            };
            public void run() {
                time++;
                SwingUtilities.invokeLater(refresher);
            }
        };

        void stopTimer() { timer.cancel(); }
    }

    class Canvas extends JPanel { // my canvas for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < FIELD_SIZE; x++)
                for (int y = 0; y < FIELD_SIZE; y++) new Celll().paint(g, x, y);
        }
    }

}
