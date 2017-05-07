package com.example.haoyuban111.mubanapplication.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;
import com.example.haoyuban111.mubanapplication.help_class.FontHelper;
import com.example.haoyuban111.mubanapplication.help_class.StringHelper;
import com.example.haoyuban111.mubanapplication.log.LogWriter;

public class ControlTextView extends TextView {

    public ControlTextView(Context context) {
        this(context, null);
    }

    public ControlTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode()) return;

        setHighlightColor(ContextHelper.getColor(android.R.color.transparent));
        checkAttributes(attrs);
    }

    private void checkAttributes(AttributeSet attrs) {
        if (attrs != null) {
            try {
                TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ControlTextView, 0, 0);
                String text = array.getString(R.styleable.ControlTextView_fontType);
                if (!StringHelper.isEmpty(text)) {
                    setFont(FontHelper.getFont(text, FontHelper.EFont.ROBOTO_MEDIUM));
                } else {
//                    setFont(FontHelper.EFont.ROBOTO_MEDIUM);
                }
            } catch (Exception exc) {
//                setFont(FontHelper.EFont.ROBOTO_MEDIUM);
                LogWriter.e(exc);
            }
        } else {
//            setFont(FontHelper.EFont.ROBOTO_MEDIUM);
        }
    }

    public void setFont(FontHelper.EFont font) {
        setTypeface(FontHelper.getFont(font));
    }
}
