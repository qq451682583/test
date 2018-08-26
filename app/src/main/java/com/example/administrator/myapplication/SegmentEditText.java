package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

public class SegmentEditText extends AppCompatEditText {

    private float itemWidth;
    private float padding;
    private int maxLength;
    private float cursorWidth;
    private boolean cursorState;
    private loopRunnable runnable;

    public SegmentEditText(Context context) {

        super(context);
        init();
    }

    public SegmentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMaxLength(6);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cursorWidth = getResources().getDisplayMetrics().density * 2;
        setCursorVisible(false);
        cursorState = true;

        if (isInEditMode()) {
            setMaxLength(6);
        }
    }

    public void setMaxLength(int length) {
        post(new Runnable() {
            @Override
            public void run() {
                padding = getPaddingWidth();
                itemWidth = padding * 4;
            }
        });

        maxLength = length;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
    }

    /**
     * 绘制文字
     *
     * @param canvas canvas
     */
    private void drawText(Canvas canvas) {
        String text = getText().toString();
        Paint paint = getPaint();
        float height = getHeight();
        float startY = (height - (paint.descent() + paint.ascent())) / 2;

        float cursorStart = 0;
        for (int i = 0; i <= maxLength; i++) {
            //绘制下划线
            float lineStartX = (padding + itemWidth) * i;
            float lineStartY = height - cursorWidth;
            canvas.drawLine(lineStartX, lineStartY, lineStartX + itemWidth, lineStartY, paint);

            if (text.length() <= i) {
                continue;
            }
            //绘制文字
            String item = String.valueOf(text.charAt(i));
            float startX = lineStartX + (itemWidth - paint.measureText(item)) / 2;
            canvas.drawText(item, startX, startY, paint);
            //光标起始位置
            cursorStart = startX + paint.measureText(item);
        }
        //绘制光标
        drawCursor(canvas, cursorStart);
    }

    /**
     * 绘制光标
     *
     * @param canvas      canvas
     * @param cursorStart 光标开始位置
     */
    private void drawCursor(Canvas canvas, float cursorStart) {
        int textLength = getText().length();
        if (textLength == 0) {
            cursorStart = itemWidth / 2;
        }

        Paint paint = getPaint();
        int paintColor = paint.getColor();
        float paintStroke = paint.getStrokeWidth();
        if (!cursorState) {
            paint.setColor(Color.TRANSPARENT);
        }

        paint.setStrokeWidth(cursorWidth);
        canvas.drawLine(cursorStart, getPaddingTop(), cursorStart,
                getHeight() - paint.getStrokeWidth() - getPaddingBottom(), paint);
        paint.setColor(paintColor);
        paint.setStrokeWidth(paintStroke);

        startLoop();
    }

    /**
     * 获取每一个item长度
     */
    private float getPaddingWidth() {
       //这里item之间的间距定为item的1/4长度,maxLength - 2为间距的个数
        int count = maxLength - 2 + maxLength * 4;
        return getWidth() / count;
    }

    /**
     * 开启循环，用于展示光标
     */
    private void startLoop() {
        if (runnable == null) {
            runnable = new loopRunnable();
            delayRunable();
        }
    }

    /**
     * 光标闪烁频率500ms一次
     */
    private class loopRunnable implements Runnable {
        @Override
        public void run() {
            cursorState = !cursorState;
            invalidate();
            delayRunable();
        }
    }

    /**
     * 延时运行runable
     */
    private void delayRunable() {
        postDelayed(runnable, 500);
    }
}
