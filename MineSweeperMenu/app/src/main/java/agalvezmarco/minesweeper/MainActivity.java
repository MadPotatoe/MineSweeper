package agalvezmarco.minesweeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private MineButton[][] board;
    private BombMatrix bombMatrix;
    private GridLayout grid;
    private ImageView reset;
    private boolean isEndGame = false;
    private boolean isWinGame = false;
    private EditText rows;
    private EditText cols;
    private EditText bombs;
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //createLoginDialogo().show();

        crearJuego();
    }

    private void crearJuego() {

        grid = (GridLayout) findViewById(R.id.grid);
        grid.removeAllViews();
        grid.setBackgroundColor(Color.GRAY);

        grid.setRowCount(Singleton.sharedInstance().getNumRows());
        grid.setColumnCount(Singleton.sharedInstance().getNumCols());

        menu = (Button) findViewById(R.id.menuButton);

        isEndGame = false;
        isWinGame = false;

        reset = (ImageView) findViewById(R.id.reset);
        reset.setImageDrawable(getResources().getDrawable(R.drawable.smiley));
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("");

        board = new MineButton[Singleton.sharedInstance().getNumRows()][Singleton.sharedInstance().getNumCols()];

        bombMatrix = new BombMatrix();


        for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
            for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
                board[i][j] = new MineButton(getApplicationContext(), i, j);


                FrameLayout frameLayout = new FrameLayout(getApplicationContext());
                BackCell backCell = new BackCell(this);
                frameLayout.addView(backCell);
                if (bombMatrix.getText(i, j) != -1) {
                    frameLayout.addView(bombMatrix.getTextView(this, i, j));
                } else {

                    BombView bomb = new BombView(this);
                    FrameLayout fl = new FrameLayout(this);
                    fl.setBackgroundColor(Color.RED);
                    fl.addView(bomb);
                    frameLayout.addView(fl);
                }
                frameLayout.addView(board[i][j]);
                grid.addView(frameLayout);

                board[i][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        MineButton mineButton = (MineButton) view;
                        if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                            mineButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.boton_pressed));
                        } else {
                            mineButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.boton));
                        }
                        return false;
                    }
                });
                {

                }

                board[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isEndGame && !isWinGame) {
                            MineButton boton = (MineButton) view;
                            if (!isBomb(boton.getRow(), boton.getCol())) {
                                lookForZeroes(boton.getRow(), boton.getCol());
                                lookForWin();
                            }

                            else {
                                TextView texto = (TextView) findViewById(R.id.textView);
                                texto.setText("YOU LOSE.");
                                showAllBombs();
                                reset.setImageDrawable(getResources().getDrawable(R.drawable.muerte));
                                isEndGame = true;

                            }

                        }

                    }
                });

                board[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        if (!isEndGame && !isWinGame) {
                            MineButton mineButton = (MineButton) v;
                            ButtonState state = mineButton.getState();
                            switch (state) {
                                case CLOSED:
                                    Drawable dr2 = getResources().getDrawable(R.drawable.flag);
                                    Bitmap bitmap2 = ((BitmapDrawable) dr2).getBitmap();
                                    Drawable d2 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap2, MineButton.WIDTH, MineButton.HEIGHT, true));
                                    mineButton.setImageDrawable(d2);
                                    mineButton.setState(ButtonState.FLAG);
                                    lookForWin();
                                    break;
                                case OPEN:
                                    break;
                                case FLAG:
                                    Drawable dr = getResources().getDrawable(R.drawable.question);
                                    Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                                    Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, MineButton.WIDTH, MineButton.HEIGHT, true));
                                    mineButton.setImageDrawable(d);
                                    mineButton.setState(ButtonState.QUESTION);
                                    break;
                                case QUESTION:
                                    mineButton.setImageDrawable(null);
                                    mineButton.setState(ButtonState.CLOSED);
                                    break;
                            }


                        }
                        return true;
                    }
                });


            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearJuego();

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createLoginDialogo().show();

            }
        });
    }

    public void lookForZeroes(int row, int col) {
        if ((row >= 0) && (row < Singleton.sharedInstance().getNumRows() && (col >= 0) && (col < Singleton.sharedInstance().getNumCols()))) {

            if ((bombMatrix.getText(row, col) == 0) && (board[row][col].getState() == ButtonState.CLOSED)) {
                board[row][col].setVisibility(View.INVISIBLE);
                board[row][col].setState(ButtonState.OPEN);
                for (int i = -1; i <= 1; i++) {

                    if ((row + i != row) && (col + i != col)) {

                        lookForZeroes(row, col + i);

                        lookForZeroes(row + i, col);

                        lookForZeroes(row + i, col + i);

                        lookForZeroes(row + i, col - i);

                    }
                }

            } else {
                if (bombMatrix.getText(row, col) != -1) {
                    board[row][col].setVisibility(View.INVISIBLE);
                    board[row][col].setState(ButtonState.OPEN);
                }
            }
        }

    }

    private boolean isBomb(int row, int col) {
        return bombMatrix.getText(row, col) == -1;

    }

    private void showAllBombs() {
        for (int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
            for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
                if (bombMatrix.getText(i, j) == -1) {
                    board[i][j].setVisibility(View.INVISIBLE);
                    board[i][j].setState(ButtonState.OPEN);
                }
            }
        }
    }


    private void lookForWin() {
        int count = 0;
        int count2 = 0;
        for(int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
            for(int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
                if(isBomb(i, j) && board[i][j].getState() == ButtonState.FLAG) {
                    count++;
                }

                if(board[i][j].getState() != ButtonState.CLOSED) {
                    count2++;
                }
            }
        }
        if(count == Singleton.sharedInstance().getNumBombs()) {
            isWinGame = true;
            TextView texto = (TextView) findViewById(R.id.textView);
            texto.setText("YOU WIN!");
        }

        if(count2 == (Singleton.sharedInstance().getNumCols() * Singleton.sharedInstance().getNumRows()) - Singleton.sharedInstance().getNumBombs()) {
            isWinGame = true;
            TextView texto = (TextView) findViewById(R.id.textView);
            texto.setText("YOU WIN!");
            ponerBanderasEnBombas();
        }
    }

private void ponerBanderasEnBombas() {
    for(int i = 0; i < Singleton.sharedInstance().getNumRows(); i++) {
        for (int j = 0; j < Singleton.sharedInstance().getNumCols(); j++) {
            if(isBomb(i, j)) {
                MineButton mineButton = board[i][j];
                Drawable dr2 = getResources().getDrawable(R.drawable.flag);
                Bitmap bitmap2 = ((BitmapDrawable) dr2).getBitmap();
                Drawable d2 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap2, 30, 30, true));
                mineButton.setImageDrawable(d2);
                mineButton.setState(ButtonState.FLAG);
            }
        }
    } }

    public AlertDialog createLoginDialogo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.menuminesweeper, null);

        rows = (EditText) v.findViewById(R.id.numberRows);
        cols = (EditText) v.findViewById(R.id.numberCols);
        bombs = (EditText) v.findViewById(R.id.numberBombs);
        builder.setView(v);

        builder.setPositiveButton("LET'S GO!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!(rows.getText() == null) && !(rows.getText().toString().equals("")))
                    Singleton.sharedInstance().setNumRows(Integer.parseInt(rows.getText().toString()));
                if (!(cols.getText() == null) && !(cols.getText().toString().equals("")))
                    Singleton.sharedInstance().setNumCols(Integer.parseInt(cols.getText().toString()));
                if (!(bombs.getText() == null) && !(bombs.getText().toString().equals("")))
                    if(Integer.parseInt(bombs.getText().toString()) <= Integer.parseInt(rows.getText().toString())*Integer.parseInt(cols.getText().toString()))
                    Singleton.sharedInstance().setNumBombs(Integer.parseInt(bombs.getText().toString()));

                crearJuego();
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}



