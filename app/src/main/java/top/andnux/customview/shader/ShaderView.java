package top.andnux.customview.shader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import top.andnux.customview.R;

public class ShaderView extends View {

    private Paint mPaint;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ShaderView, defStyle, 0);

        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(80);
//        mPaint.setStrikeThruText(true);
//        mPaint.setFakeBoldText(true);
        mPaint.setUnderlineText(true);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/hwxk.ttf");
        mPaint.setTypeface(typeface);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * //新建一个线性渐变，前两个参数是渐变开始的点坐标，
         * 第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，
         * 玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，
         * 每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
         */
//        Shader shader = new LinearGradient(0, getHeight() / 2, getWidth(), getHeight() / 2,
//                new int[]{Color.WHITE, Color.YELLOW, Color.WHITE}, null, Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
//        int top = 60;
//        canvas.drawRect(20, top, getMeasuredWidth() - 20, top + 20, mPaint);

//        Shader shader = new RadialGradient(getWidth() / 2, getHeight() / 2,
//                360f,
//                new int[]{shadeColor(Color.YELLOW), shadeColor(Color.WHITE)}, null,
//                Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
//        RectF rectF = new RectF(20, getHeight() / 2, getMeasuredWidth() - 20,
//                getHeight() / 2 + 20);
//        canvas.drawOval(rectF, mPaint);

//        Shader shader = new SweepGradient(getWidth() / 2, getHeight() / 2,
//                shadeColor(Color.BLUE),shadeColor(Color.WHITE));
//        mPaint.setShader(shader);
//        RectF rectF = new RectF(20, getHeight() / 2, getMeasuredWidth() - 20,
//                getHeight() / 2 + 20);
//        canvas.drawOval(rectF, mPaint);

        String text = "Hello,安卓!";
        Rect bounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        int dx = bounds.bottom / 2 - bounds.top / 2;
        canvas.drawText(text,0,bounds.height(),mPaint);
    }

    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        String result = "20" + hax.substring(2);
        return Integer.valueOf(result, 16);
    }
}
