package agalvezmarco.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static agalvezmarco.minesweeper.R.drawable.boton_pressed;

/**
 * Created by alu20911095r on 27/10/16.
 */

public class BombMatrix {

    private int[][] bombMatrix;

    public BombMatrix() {
        bombMatrix = new int[Singleton.sharedInstance().getNumRows()][Singleton.sharedInstance().getNumCols()];
        resetMatrix();
        buildMatrix();
    }

    public void resetMatrix() {
        for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
            for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
                bombMatrix[i][j] = 0;
            }
        }
        int numBombs = 0;
        int row, col;
        Random random = new Random();

        while (numBombs < Singleton.sharedInstance().getNumBombs()) {
            row = random.nextInt(Singleton.sharedInstance().getNumRows());
            col = random.nextInt(Singleton.sharedInstance().getNumCols());
            if (bombMatrix[row][col] != -1) {
                bombMatrix[row][col] = -1;
                numBombs++;
            }
        }

    }

    private void buildMatrix() {
        for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
            for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
                int count = checkAdjacentsForBombs(i, j);
                if (bombMatrix[i][j] != -1) {
                    bombMatrix[i][j] = count;
                }

            }
        }
    }

    private int checkAdjacentsForBombs(int i, int j) {
        int count = 0;
        count += checkNorthForBomb(i, j);
        count += checkNorthEastForBomb(i, j);
        count += checkEastForBomb(i, j);
        count += checkSouthEastForBomb(i, j);
        count += checkSouthForBomb(i, j);
        count += checkSouthWestForBomb(i, j);
        count += checkWestForBomb(i, j);
        count += checkNorthWestForBomb(i, j);
        return count;
    }

    private int checkNorthForBomb(int i, int j) {
        return i - 1 >= 0 && bombMatrix[i - 1][j] == -1 ? 1 : 0;
    }

    private int checkNorthEastForBomb(int i, int j) {
        return j + 1 < bombMatrix[0].length && i - 1 >= 0 && bombMatrix[i - 1][j + 1] == -1 ? 1 : 0;
    }

    private int checkEastForBomb(int i, int j) {
        return j + 1 < bombMatrix[0].length && bombMatrix[i][j + 1] == -1 ? 1 : 0;
    }

    private int checkSouthEastForBomb(int i, int j) {
        return j + 1 < bombMatrix[0].length && i + 1 < bombMatrix.length && bombMatrix[i + 1][j + 1] == -1 ? 1 : 0;
    }

    private int checkSouthForBomb(int i, int j) {
        return i + 1 < bombMatrix.length && bombMatrix[i + 1][j] == -1 ? 1 : 0;
    }

    private int checkSouthWestForBomb(int i, int j) {
        return j - 1 >= 0 && i + 1 < bombMatrix.length && bombMatrix[i + 1][j - 1] == -1 ? 1 : 0;
    }

    private int checkWestForBomb(int i, int j) {
        return j - 1 >= 0 && bombMatrix[i][j - 1] == -1 ? 1 : 0;
    }

    private int checkNorthWestForBomb(int i, int j) {
        return j - 1 >= 0 && i - 1 >= 0 && bombMatrix[i - 1][j - 1] == -1 ? 1 : 0;
    }


    public TextView getTextView(Context c, int i, int j) {

        TextView text = new TextView(c);
        final float scale = c.getResources().getDisplayMetrics().density;
        text.setPadding((int) (scale * 10), (int) (scale * 3), (int) (scale * 5), (int) (scale * 5));

        int n = bombMatrix[i][j];
        switch (n) {
            case 0:
                text.setText("");
                break;

            case 1:
                text.setTextColor(Color.GREEN);
                break;

            case 2:
                text.setTextColor(Color.RED);
                break;

            case 3:
                text.setTextColor(Color.BLUE);
                break;

            case 4:
                text.setTextColor(Color.CYAN);
                break;

            case 5:
                text.setTextColor(Color.BLACK);
                break;

            case 6:
                text.setTextColor(Color.MAGENTA);
                break;


            case 7:
                text.setTextColor(Color.BLACK);
                break;


            case 8:
                text.setTextColor(Color.YELLOW);
                break;

            case -1:
                //text.setBackgroundDrawable(R.drawable.bomb);                break;
        }

        if ((n != 0) && (n != -1)) {
           text.setText(Html.fromHtml("<b>" + n + "</b>"));
        }

        return text;

    }

    public int getText(int row, int col) {
        return bombMatrix[row][col];
    }


}

