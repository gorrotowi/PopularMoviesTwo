package com.gorrotowi.popularmoviestwo.customElements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gorro on 09/08/15.
 */
public class AwesomeTextView extends TextView {
    Typeface tf;

    public AwesomeTextView(Context context) {
        super(context);
        initStyle();
    }

    public AwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle();
    }

    public AwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle();
    }

    private void initStyle() {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/FontAwesome.otf");
        this.setTypeface(tf);
    }
}
