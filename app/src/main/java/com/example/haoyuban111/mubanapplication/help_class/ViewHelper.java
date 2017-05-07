package com.example.haoyuban111.mubanapplication.help_class;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewHelper {
    protected static final float ALPHA_ENABLED = 1.0f;
    protected static final float ALPHA_DISABLED = 0.4f;

    public static Rect getViewRectOnScreen(View view) {
        Rect result = new Rect();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        result.left = location[0];
        result.top = location[1];
        result.right = result.left + view.getWidth();
        result.bottom = result.top + view.getHeight();
        return result;
    }

    public static void removeFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
    }

    public static Size measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
        return new Size(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    public static void setVisibility(ViewGroup group, int visibility) {
        if (group != null) {
            for (int i = 0; i < group.getChildCount(); i++) {
                View view = group.getChildAt(i);
                if (view != null) {
                    view.setVisibility(visibility);
                }
            }
            group.setVisibility(visibility);
        }
    }

    public static class Size {
        private int _width;
        private int _height;

        public Size() {
        }

        public Size(int width, int height) {
            _width = width;
            _height = height;
        }

        public int getWidth() {
            return _width;
        }

        public void setWidth(int width) {
            _width = width;
        }

        public int getHeight() {
            return _height;
        }

        public void setHeight(int height) {
            _height = height;
        }
    }

    public static void enableView(View view, boolean isEnabled) {
        if (view != null) {
            view.setEnabled(isEnabled);
            view.setAlpha(isEnabled ? ALPHA_ENABLED : ALPHA_DISABLED);
        }
    }
}
