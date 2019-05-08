package top.andnux.customview.piechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import top.andnux.customview.R;

public class PieChartView extends View {

    private Paint mPiePaint;
    private Paint mLinePaint;
    private int margin = 160;
    private int startOffset = 0;
    private Path path = new Path();
    private Rect rect = new Rect();

    private List<PieChartBean> mList = new ArrayList<>();

    public PieChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PieChartView, defStyle, 0);

        a.recycle();
        mList.add(new PieChartBean(Color.RED, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.GRAY, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.BLUE, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.RED, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.GRAY, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.BLUE, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.RED, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.GRAY, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.BLUE, 10, Color.YELLOW, "10"));
        mList.add(new PieChartBean(Color.YELLOW, 10, Color.YELLOW, "10"));
        mPiePaint = new Paint();
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(margin, margin, getMeasuredWidth() - margin,
                getMeasuredHeight() - margin);
        int start = startOffset;
        int x0 = (int) rectF.centerX();
        int y0 = (int) rectF.centerY();

        for (PieChartBean bean : mList) {
            mPiePaint.setColor(bean.getPieColor());
            float sweepAngle = bean.getPercentage() / 100.0f * 360.0f;
            canvas.drawArc(rectF, start, sweepAngle, true, mPiePaint);
            int lineOffsetX = 0;
            int lineOffsetY = 0;
            int pointOffsetX = 0;
            int pointOffsetY = 0;
            int textOffsetX = 0;
            int textOffsetY1 = 0;
            int textOffsetY2 = 0;
            int dx = 0;
            float ao = start + sweepAngle / 2;
            mLinePaint.setStrokeWidth(4);
            mLinePaint.getTextBounds(bean.getLineText(), 0, bean.getLineText().length(), rect);
            if (start >= 0 && start <= 90) {
                pointOffsetX = 20;
                pointOffsetY = 20;
                lineOffsetX = 20;
                lineOffsetY = 20;
                dx = 80;
                textOffsetX = -rect.width();
                textOffsetY1 = 20;
                textOffsetY2 = 40;
            } else if (start <= 180 && start > 90) {
                pointOffsetX = -20;
                pointOffsetY = 20;
                lineOffsetX = -20;
                lineOffsetY = 20;
                dx = -80;
                textOffsetX = -2 * rect.width();
                textOffsetY1 = 20;
                textOffsetY2 = 40;
            } else if (start <= 270 && start > 180) {
                pointOffsetX = -20;
                pointOffsetY = -20;
                lineOffsetX = -20;
                lineOffsetY = -20;
                dx = -80;
                textOffsetX = -2 * rect.width();
                textOffsetY1 = 0;
                textOffsetY2 = -20;
            } else if (start <= 360 && start > 270) {
                pointOffsetX = +20;
                pointOffsetY = -20;
                lineOffsetX = +20;
                lineOffsetY = -20;
                dx = 80;
                textOffsetX = -rect.width();
                textOffsetY1 = 0;
                textOffsetY2 = -20;
            }

            int x1 = (int) (x0 + rectF.width() / 2 * Math.cos(ao * Math.PI / 180));
            int y1 = (int) (y0 + rectF.width() / 2 * Math.sin(ao * Math.PI / 180));
            mPiePaint.setColor(bean.getLineColor());
            int cx = x1 + pointOffsetX;
            int cy = y1 + pointOffsetY;
            canvas.drawCircle(cx, cy, 6, mPiePaint);

            path.reset();
            path.moveTo(cx, cy);
            path.rLineTo(lineOffsetX, lineOffsetY);
            path.rLineTo(dx, 0);

            int lineX = cx + 20 + dx;
            canvas.drawText(bean.getLineText(), lineX + textOffsetX,
                    cy + textOffsetY1 - mLinePaint.getStrokeWidth(), mLinePaint);
            canvas.drawText(bean.getLineText(), lineX + textOffsetX,
                    cy + textOffsetY2, mLinePaint);
            mLinePaint.setColor(bean.getLineColor());
            canvas.drawPath(path, mLinePaint);
            start += sweepAngle;
        }

//        mPaint.setColor(Color.WHITE);
//        int radius = (int) (rectF.width() / 5);
//        canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius, mPaint);

    }
}
