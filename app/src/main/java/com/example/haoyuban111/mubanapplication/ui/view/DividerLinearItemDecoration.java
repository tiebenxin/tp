package com.example.haoyuban111.mubanapplication.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;


/**
 * Created by haoyuban111 on 2017/4/19.
 */

public class DividerLinearItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int size;
    private Paint mPaint;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     */
    public DividerLinearItemDecoration(Context context) {
        if (mDivider == null) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }
    }

    public DividerLinearItemDecoration(Context context, int drawableId) {
        mDivider = ContextHelper.getDrawable(drawableId);
        size = mDivider.getIntrinsicHeight();//根据朝向，获取宽或者高
    }

    public DividerLinearItemDecoration(Context context, int drawableId, int dividerColor) {
        mDivider = ContextHelper.getDrawable(drawableId);
        size = mDivider.getIntrinsicHeight();//根据朝向，获取宽或者高
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextHelper.getColor(dividerColor));
        mPaint.setStyle(Paint.Style.FILL);
    }

    /*
    * @param size:The width or height of divider
    * */
    public void setSize(int value) {
        size = value;
    }

    public void setOrientation(int value) {
        size = value;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);

    }


    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + size;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + size;

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }


    private boolean isLastRaw(int pos, int childCount) {

        if (pos == childCount - 1) {
            return true;
        }

        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if (isLastRaw(itemPosition, childCount)) {// 如果是最后一行，则不需要绘制右边
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, size);
        }
    }
}
