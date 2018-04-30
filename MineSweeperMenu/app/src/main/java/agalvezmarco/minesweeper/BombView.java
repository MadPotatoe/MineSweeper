package agalvezmarco.minesweeper;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by alu20911095r on 03/11/16.
 */

public class BombView extends ImageView {

    public BombView(Context c) {
        super(c);
        setImageDrawable(getResources().getDrawable(R.drawable.bomb));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MineButton.WIDTH, MineButton.HEIGHT);
        setLayoutParams(layoutParams);

    }

}
