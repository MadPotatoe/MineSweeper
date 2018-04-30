package agalvezmarco.minesweeper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;


public class MineButton extends ImageButton {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private int col, row;
    private ButtonState state;

    public MineButton(Context c, int row, int col){
        super(c);
        this.row = row;
        this.col = col;
        state = ButtonState.CLOSED;

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int width = (int)(WIDTH * scale);
        int height = (int)(HEIGHT * scale);

        android.view.ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        setLayoutParams(params);

        setBackgroundDrawable(getResources().getDrawable(R.drawable.boton));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setState(ButtonState state) {
        this.state = state;
    }

    public ButtonState getState() {
        return state;
    }
}
