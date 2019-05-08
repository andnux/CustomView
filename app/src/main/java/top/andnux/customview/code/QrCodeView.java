package top.andnux.customview.code;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import top.andnux.customview.R;

public class QrCodeView extends View {

    private Paint mLinePaint;
    private Paint mRectPaint;
    private Paint mLaserPaint;
    private int lineWidth = 2;
    private Path mPath;
    private int hornColor = Color.YELLOW;
    private int lineColor = Color.YELLOW;
    private int hornWidth = 20;
    private int hornLength = 100;
    private int scanningLineHeight = 10;
    private int laserColor = Color.YELLOW;
    private int scannerStart = 20;
    private int lineMoveDistance = 8;
    private Rect mCodeRect = new Rect();
    private long animationDelay = 10L;
    private int dir;

    public QrCodeView(Context context) {
        this(context, null);
    }

    public QrCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QrCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.QrCodeView, defStyle, 0);
        lineWidth = a.getDimensionPixelSize(R.styleable.QrCodeView_lineWidth,lineWidth);
        hornColor = a.getColor(R.styleable.QrCodeView_hornColor,hornColor);
        lineColor = a.getColor(R.styleable.QrCodeView_lineColor,lineColor);
        hornWidth = a.getDimensionPixelSize(R.styleable.QrCodeView_hornWidth,hornWidth);
        hornLength = a.getDimensionPixelSize(R.styleable.QrCodeView_hornLength,hornLength);
        laserColor = a.getColor(R.styleable.QrCodeView_laserColor,laserColor);
        lineMoveDistance = (int) a.getFloat(R.styleable.QrCodeView_lineMoveDistance,lineMoveDistance);
        animationDelay = (long) a.getFloat(R.styleable.QrCodeView_animationDelay,animationDelay);
        scanningLineHeight = a.getDimensionPixelSize(R.styleable.QrCodeView_scanningLineHeight,scanningLineHeight);
        a.recycle();
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(lineWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(lineColor);

        mRectPaint = new Paint();
        mRectPaint.setStrokeWidth(hornWidth);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(hornColor);

        mLaserPaint = new Paint();
        mLaserPaint.setStrokeWidth(20);
        mLaserPaint.setStyle(Paint.Style.FILL);
        mLaserPaint.setAntiAlias(true);
        mLaserPaint.setColor(Color.BLUE);

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            heightSize = widthSize;
        }
        int widthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制边框
        canvas.drawRect(0, 0, getMeasuredWidth() - lineWidth
                , getMeasuredHeight() - lineWidth, mLinePaint);

        //左上角
        mPath.reset();
        mPath.moveTo(0, hornLength);
        mPath.lineTo(0, 0);
        mPath.lineTo(hornLength, 0);
        canvas.drawPath(mPath, mRectPaint);

        //右上角
        mPath.reset();
        mPath.moveTo(getMeasuredHeight() - lineWidth, hornLength);
        mPath.lineTo(getMeasuredHeight(), 0);
        mPath.lineTo(getMeasuredWidth() - lineWidth - hornLength, 0);
        canvas.drawPath(mPath, mRectPaint);

        //左下角
        mPath.reset();
        mPath.moveTo(0, getMeasuredHeight() - lineWidth - hornLength);
        mPath.lineTo(0, getMeasuredHeight());
        mPath.lineTo(hornLength, getMeasuredWidth() - lineWidth);
        canvas.drawPath(mPath, mRectPaint);


        //右下角
        mPath.reset();
        mPath.moveTo(getMeasuredWidth() - lineWidth, getMeasuredHeight() - lineWidth - hornLength);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(getMeasuredWidth() - lineWidth - hornLength, getMeasuredHeight() - lineWidth);
        canvas.drawPath(mPath, mRectPaint);

        mCodeRect.bottom = getMeasuredHeight();
        mCodeRect.right = getMeasuredWidth();
        drawLaserScanner(canvas, mCodeRect);
    }

    //绘制扫描线
    private void drawLaserScanner(Canvas canvas, Rect frame) {

        RadialGradient radialGradient = new RadialGradient(
                (float) (frame.left + frame.width() / 2),
                (float) (scannerStart + scanningLineHeight / 2),
                360f,
                laserColor,
                shadeColor(laserColor),
                Shader.TileMode.MIRROR);

        mLaserPaint.setShader(radialGradient);
        if (scannerStart <= 20) {
            dir = 1; //往下
        } else if (scannerStart >= (getMeasuredHeight() - 40)) {
            dir = 0; //往上
        }
        if (dir == 0){
            scannerStart -= lineMoveDistance;
        }else {
            scannerStart += lineMoveDistance;
        }
        RectF rectF = new RectF(frame.left + 3 * scanningLineHeight, scannerStart,
                frame.right - 3 * scanningLineHeight, scannerStart + scanningLineHeight);
        canvas.drawOval(rectF, mLaserPaint);
        postInvalidateDelayed(animationDelay, frame.left, frame.top, frame.right, frame.bottom);
    }

    //处理颜色模糊
    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        String result = "20" + hax.substring(2);
        return Integer.valueOf(result, 16);
    }
}
