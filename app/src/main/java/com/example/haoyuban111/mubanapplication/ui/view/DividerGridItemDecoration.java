package com.example.haoyuban111.mubanapplication.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;


/**
 * Created by liszt on 2017/4/19.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    public final static int HORIZONTAL = 1;
    public final static int VERTICAL = 1 << 1;

    public static final int BORDER = 1;//是分割线，不含边界
    public static final int DIVIDER = 0;//是分割线，含边界线


    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int size = 1;//默认为1
    private int orientation = VERTICAL;//默认为竖直方向
    private Paint mPaint;
    private int type = DIVIDER;//默认为分割线


    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     */
    public DividerGridItemDecoration(Context context) {
        if (mDivider == null) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }
    }

    public DividerGridItemDecoration(Context context, int drawableId) {
        mDivider = ContextHelper.getDrawable(drawableId);
        size = mDivider.getIntrinsicHeight();//根据朝向，获取宽或者高
    }

    public DividerGridItemDecoration(Context context, int drawableId, int dividerColor) {
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
        orientation = value;
    }

    public void setType(int t) {
        type = t;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        } else {
            if (orientation == HORIZONTAL) {//水平滑动的，竖直分割线
                drawVertical(c, parent);
            } else if (orientation == VERTICAL) {//竖直滑动的，水平分割线
                drawHorizontal(c, parent);
            }
        }
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    //画水平线
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


    //画竖线
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + size;


            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /*
    * 是否是最后一列
    * */
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.HORIZONTAL) {
                if (pos + spanCount >= childCount) {
                    return true;
                }
            } else if (orientation == GridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                    return true;
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        } else {
            if (pos == childCount - 1) {
                return true;
            }
        }
        return false;
    }

    /*
      * 是否是最后一行
      * */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            if (orientation == GridLayoutManager.HORIZONTAL) {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else if (orientation == GridLayoutManager.VERTICAL) {
                if (pos >= childCount - spanCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount - spanCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            } else {// StaggeredGridLayoutManager 且横向滚动

                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        } else {
            if (pos == childCount - 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (type == DIVIDER) {
            if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
                if (orientation == VERTICAL) {//垂直滚动方向
                    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
                        if (isLastColum(parent, itemPosition, spanCount, childCount)) {//最后一个
                            outRect.set(size / 2, 0, 0, 0);
                        } else {
                            outRect.set(0, 0, size / 2, 0);
                        }

                    } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {// 如果是最后一列，则不需要绘制右边
                        outRect.set(size / 2, 0, 0, size);
                    } else {
                        outRect.set(0, 0, size / 2, size);
                    }
                } else {//水平滚动方向
                    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
                        outRect.set(0, 0, 0, 0);
                    } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {// 如果是最后一列，则不需要绘制右边
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, 0, size, size);
                    }
                }
            } else {
                if (orientation == VERTICAL) {
                    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制右边
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, 0, 0, size);
                    }
                } else if (orientation == HORIZONTAL) {
                    if (isLastColum(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制右边
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, 0, size, 0);
                    }
                }
            }
        } else if (type == BORDER) {
            if (orientation == VERTICAL) {//竖直滚动
                if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
                    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
                        if (isLastColum(parent, itemPosition, spanCount, childCount)) {//最后一个
                            outRect.set(size, size, size, size);
                        } else {
                            outRect.set(size, size, 0, size);
                        }

                    } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {// 如果是最后一列，则不需要绘制右边
                        outRect.set(size, size, size, 0);
                    } else {
                        outRect.set(size, size, 0, 0);
                    }
                } else {
                    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                        outRect.set(size, size, size, size);
                    } else {
                        outRect.set(size, size, size, 0);
                    }
                }
            } else {//水平滚动

            }
        }
    }

}
