package com.hvph.musicplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hvph.musicplay.R;


/**
 * Created by bibo on 21/03/2015.
 */
public class TabIndicator extends LinearLayout {

    private ImageView mImageViewTabIndicatorIcon;
    private TextView mTextViewTabIndicatorText;

    public TabIndicator(Context context) {
        super(context, null);
        init();
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TabIndicator(Context context, String text, int imageResource) {
        this(context);
        setText(text);
        setImage(imageResource);
    }

    private void init() {
        inflate(getContext(), R.layout.custom_tab_indicator, this);
        mImageViewTabIndicatorIcon = (ImageView) findViewById(R.id.image_view_tab_indicator_icon);
        mTextViewTabIndicatorText = (TextView) findViewById(R.id.text_view_tab_indicator_text);
    }

    public void setText(String text) {
        mTextViewTabIndicatorText.setText(text);
    }

    public void setImage(int imageResource) {
        mImageViewTabIndicatorIcon.setImageResource(imageResource);
    }
}
