package top.andnux.customview.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import top.andnux.customview.R;

public class RoundProgressBar extends View {

    private int borderWidth = 10;
    private int borderColor = Color.RED;
    private int progressColor = Color.BLUE;
    private Paint bgPaint;
    private Paint progressPaint;
    private Paint pointerPaint;
    private Paint centerPaint;
    private int progress = 10;
    private int max = 100;
    private int pointerWidth = 2;
    private int pointerColor = Color.RED;
    private int centerWidth = 2;
    private int centerColor = Color.RED;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        borderWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundProgressBar_borderWidth, borderWidth);
        borderColor = typedArray.getColor(R.styleable.RoundProgressBar_borderColor, borderColor);
        progressColor = typedArray.getColor(R.styleable.RoundProgressBar_progressColor, progressColor);
        progress = typedArray.getInt(R.styleable.RoundProgressBar_progress, progress);
        max = typedArray.getInt(R.styleable.RoundProgressBar_max, max);
        pointerWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundProgressBar_pointerWidth, pointerWidth);
        pointerColor = typedArray.getColor(R.styleable.RoundProgressBar_pointerColor, pointerColor);
        centerWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundProgressBar_centerWidth, centerWidth);
        centerColor = typedArray.getColor(R.styleable.RoundProgressBar_centerColor, centerColor);

        typedArray.recycle();

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setColor(borderColor);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(borderWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(progressColor);

        pointerPaint = new Paint();
        pointerPaint.setStrokeWidth(pointerWidth);
        pointerPaint.setStrokeCap(Paint.Cap.ROUND);
        pointerPaint.setAntiAlias(true);
        pointerPaint.setStyle(Paint.Style.FILL);
        pointerPaint.setColor(pointerColor);

        centerPaint = new Paint();
        centerPaint.setStrokeCap(Paint.Cap.ROUND);
        centerPaint.setAntiAlias(true);
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setColor(centerColor);
    }


    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        progressPaint.setColor(progressColor);
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPointerWidth() {
        return pointerWidth;
    }

    public void setPointerWidth(int pointerWidth) {
        this.pointerWidth = pointerWidth;
    }

    public int getPointerColor() {
        return pointerColor;
    }

    public void setPointerColor(int pointerColor) {
        this.pointerColor = pointerColor;
    }

    public int getCenterWidth() {
        return centerWidth;
    }

    public void setCenterWidth(int centerWidth) {
        this.centerWidth = centerWidth;
    }

    public int getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        RectF rectF = new RectF(borderWidth / 2, borderWidth / 2,
                measuredWidth - borderWidth / 2,
                measuredHeight - borderWidth / 2);
        canvas.drawArc(rectF, -225, 270, false, bgPaint);
        float step = (float) ((double) (progress) / max * 270.0);
        canvas.drawArc(rectF, -225, step, false, progressPaint);
        canvas.save();//保存以下，结合旋转
        canvas.rotate(step - 90, getMeasuredWidth() / 2, getMeasuredHeight() / 2);//旋转，按照指定的角度进行旋转
        float x = (float) ((getMeasuredWidth() / 2 + 100) / Math.sqrt(2) / 2);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                x, x, pointerPaint);
        canvas.restore();
        RectF rectF1 = new RectF(getMeasuredWidth() / 2 - (centerWidth / 2),
                getMeasuredHeight() / 2 - (centerWidth / 2),
                getMeasuredWidth() / 2 + (centerWidth / 2),
                getMeasuredHeight() / 2 + (centerWidth / 2));
        canvas.drawRoundRect(rectF1, getMeasuredWidth() / 2, getMeasuredHeight() / 2, centerPaint);

    }
}
