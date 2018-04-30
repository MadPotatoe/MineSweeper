package agalvezmarco.minesweeper;

import android.content.Context;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by alu20911095r on 27/10/16.
 */

public class BackCell extends ImageView {


    public BackCell(Context context) {
        super(context);
        setImageDrawable(getResources().getDrawable(R.drawable.back));
        final float scale = getContext().getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams((int)(MineButton.WIDTH * scale),
                        (int) (scale * MineButton.HEIGHT));
        setLayoutParams(layoutParams);
    }


}
