package com.appframe.library.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.appframe.library.R;
import com.appframe.library.component.font.TypefaceHelper;


/**
 * 自定义的字体显示组件
 */

public class AFEditTextView extends EditText {

    public AFEditTextView(Context context) {
        super(context);
        init("");
    }

    public AFEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String fontName = "";
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AFTextView);
        if (typedArray != null) {
            String font = typedArray.getString(R.styleable.AFTextView_fontStype);
            if (!TextUtils.isEmpty(font)) {
                fontName = font;
            }
        }
        init(fontName);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AFEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);

        String fontName = "";
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AFTextView);
        if (typedArray != null) {
            String font = typedArray.getString(R.styleable.AFTextView_fontStype);
            if (!TextUtils.isEmpty(font)) {
                fontName = font;
            }
        }
        init(fontName);
    }

    private void init(String fontName) {
        Typeface tf = null;
        if (TextUtils.isEmpty(fontName)) {
            tf = TypefaceHelper.get(this.getContext(), getResources().getString(R.string.font_华文行楷));
        } else {
            tf = TypefaceHelper.get(this.getContext(), fontName);
        }
        setTypeface(tf);
    }
}
