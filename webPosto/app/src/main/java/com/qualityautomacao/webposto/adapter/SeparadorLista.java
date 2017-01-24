package com.qualityautomacao.webposto.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Wiliam
 * @since 13/01/2017
 */
public class SeparadorLista extends RecyclerView.ItemDecoration {
    private final Drawable divider;
    private final int aPartirIndex;

    public SeparadorLista(Drawable divider) {
        this(divider, 0);
    }

    public SeparadorLista(Drawable divider, int aPartirIndex) {
        this.divider = divider;
        this.aPartirIndex = aPartirIndex -1;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) >= aPartirIndex) {
            outRect.bottom = divider.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + divider.getIntrinsicHeight();

            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            divider.draw(canvas);
        }
    }
}
