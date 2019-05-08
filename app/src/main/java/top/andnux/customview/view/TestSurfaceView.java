package top.andnux.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import java.util.Date;

import androidx.annotation.RequiresApi;

public class TestSurfaceView extends SurfaceTemplateView {

    private Paint mBorderPaint;
    private Paint mScalePaint;
    private Paint mTestPaint;
    private Paint mSecondPaint;
    private Paint mMinutesPaint;
    private Paint mHoursPaint;
    private Paint mCenterPaint;
    private RectF mRectF;
    private float LONG_DEGREE_LENGTH = 20;

    public TestSurfaceView(Context context) {
        super(context);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(Color.RED);
        mBorderPaint.setStrokeWidth(10);

        mScalePaint = new Paint();
        mScalePaint.setAntiAlias(true);
        mScalePaint.setStyle(Paint.Style.STROKE);
        mScalePaint.setColor(Color.RED);

        mTestPaint = new Paint();
        mTestPaint.setAntiAlias(true);
        mTestPaint.setTextSize(50);
        mTestPaint.setColor(Color.RED);

        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStrokeWidth(6);
        mSecondPaint.setStyle(Paint.Style.FILL);
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecondPaint.setColor(Color.RED);

        mMinutesPaint = new Paint();
        mMinutesPaint.setAntiAlias(true);
        mMinutesPaint.setStrokeWidth(8);
        mMinutesPaint.setStyle(Paint.Style.FILL);
        mMinutesPaint.setStrokeCap(Paint.Cap.ROUND);
        mMinutesPaint.setColor(Color.RED);

        mHoursPaint = new Paint();
        mHoursPaint.setAntiAlias(true);
        mHoursPaint.setStrokeWidth(10);
        mHoursPaint.setStyle(Paint.Style.FILL);
        mHoursPaint.setStrokeCap(Paint.Cap.ROUND);
        mHoursPaint.setColor(Color.RED);

        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setStyle(Paint.Style.FILL);
        mCenterPaint.setStrokeCap(Paint.Cap.ROUND);
        mCenterPaint.setColor(Color.RED);
    }

    @Override
    protected void onSurfaceDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        float radius = mRectF.width() / 2 - mBorderPaint.getStrokeWidth() / 2;

        //绘制表盘
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), radius, mBorderPaint);

        //绘制刻度
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mScalePaint.setStrokeWidth(4);
                LONG_DEGREE_LENGTH = 40;
            } else {
                mScalePaint.setStrokeWidth(2);
                LONG_DEGREE_LENGTH = 20;
            }
            canvas.drawLine(radius, mRectF.height() / 2 - radius, radius,
                    mRectF.height() / 2 - radius + LONG_DEGREE_LENGTH, mScalePaint);
            canvas.rotate(6, mRectF.width() / 2, mRectF.height() / 2);
        }
        int a = 360;
        for (int i = 0; i < 12; i++) {
            //绘制文字
            String text = "";
            if (i == 0) {
                text = "12";
            } else {
                text = String.valueOf(i);
            }
            int x = (int) (mRectF.centerX() - (radius - 100) * (Math.sin(a * Math.PI / 180)));
            int y = (int) (mRectF.centerX() - (radius - 100) * (Math.cos(a * Math.PI / 180)));
            Rect bounds = new Rect();
            mTestPaint.getTextBounds(text, 0, text.length(), bounds);
            if (a == 360) {
                canvas.drawText(text, x - bounds.width() / 2, y, mTestPaint);
            } else if (a == 330) {
                canvas.drawText(text, x - bounds.width() / 2, y, mTestPaint);
            } else if (a == 300) {
                canvas.drawText(text, x, y, mTestPaint);
            } else if (a == 270) {
                canvas.drawText(text, x, y + bounds.height() / 2, mTestPaint);
            } else if (a == 240) {
                canvas.drawText(text, x, y + bounds.height() / 2, mTestPaint);
            } else if (a == 210) {
                canvas.drawText(text, x, y + bounds.height(), mTestPaint);
            } else if (a == 180) {
                canvas.drawText(text, x - bounds.width() / 2, y + bounds.height(), mTestPaint);
            } else if (a == 150) {
                canvas.drawText(text, x - bounds.width() / 2, y + bounds.height(), mTestPaint);
            } else if (a == 120) {
                canvas.drawText(text, x - bounds.width(), y + bounds.height(), mTestPaint);
            } else if (a == 90) {
                canvas.drawText(text, x - bounds.width(), y + bounds.height() / 2, mTestPaint);
            } else if (a == 60) {
                canvas.drawText(text, x - bounds.width() / 2, y + bounds.height() / 2, mTestPaint);
            } else if (a == 30) {
                canvas.drawText(text, x - bounds.width() / 2, y + bounds.height() / 2, mTestPaint);
            }
            a -= 30;
        }

        Date date = new Date();
        //绘制分针
        int hours = date.getHours() * 5 + date.getMinutes() / 6;
        int hx = (int) (mRectF.centerX() - (radius - 230) * (Math.sin((-hours) * Math.PI / 180)));
        int hy = (int) (mRectF.centerX() - (radius - 230) * (Math.cos((-hours) * Math.PI / 180)));
        canvas.drawLine(mRectF.centerX(), mRectF.centerY(), hx, hy, mHoursPaint);

        //绘制分针
        int minutes = date.getMinutes() * 6;
        int mx = (int) (mRectF.centerX() - (radius - 190) * (Math.sin((-minutes) * Math.PI / 180)));
        int my = (int) (mRectF.centerX() - (radius - 190) * (Math.cos((-minutes) * Math.PI / 180)));
        canvas.drawLine(mRectF.centerX(), mRectF.centerY(), mx, my, mMinutesPaint);

        //绘制秒针
        int seconds = date.getSeconds() * 6;
        int sx = (int) (mRectF.centerX() - (radius - 150) * (Math.sin((-seconds) * Math.PI / 180)));
        int sy = (int) (mRectF.centerX() - (radius - 150) * (Math.cos((-seconds) * Math.PI / 180)));
        canvas.drawLine(mRectF.centerX(), mRectF.centerY(), sx, sy, mSecondPaint);

        RectF f = new RectF(mRectF.centerX() - 15, mRectF.centerX() - 15,
                mRectF.centerX() + 15, mRectF.centerX() + 15);
        canvas.drawOval(f, mCenterPaint);

    }
}
