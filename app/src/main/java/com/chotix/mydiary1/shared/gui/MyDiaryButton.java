package com.chotix.mydiary1.shared.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chotix.mydiary1.R;
import com.chotix.mydiary1.shared.ColorTools;
import com.chotix.mydiary1.shared.ScreenHelper;
import com.chotix.mydiary1.shared.ThemeManager;

public class MyDiaryButton extends androidx.appcompat.widget.AppCompatButton {
    public MyDiaryButton(@NonNull Context context) {
        super(context);
    }

    public MyDiaryButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDiaryButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setAllCaps(false);
        this.setBackground(ThemeManager.getInstance().getButtonBgDrawable(getContext()));
        this.setTextColor(ColorTools.getColorStateList(getContext(), R.color.button_text_color));
        this.setStateListAnimator(null);
        this.setMinimumWidth(ScreenHelper.dpToPixel(getContext().getResources(), 80));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
