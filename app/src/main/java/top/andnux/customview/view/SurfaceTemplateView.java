package top.andnux.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;


public abstract class SurfaceTemplateView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {

    private Thread mThread;
    private SurfaceHolder mHolder;
    private boolean mIsDrawing = true;
    private static final int DRAW_INTERVAL = 30;

    public SurfaceTemplateView(Context context) {
        this(context, null);
    }

    public SurfaceTemplateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceTemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SurfaceTemplateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    protected void init(Context context, AttributeSet attrs) {
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mThread = new Thread(this);
        mIsDrawing = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHolder.removeCallback(this);
        mIsDrawing = false;
    }

    @Override
    public void run() {
        long deltaTime = 0;
        long tickTime = 0;
        tickTime = System.currentTimeMillis();
        while (mIsDrawing) {
            Canvas canvas = null;
            try {
                canvas = mHolder.lockCanvas();
                onSurfaceDraw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
            deltaTime = System.currentTimeMillis() - tickTime;
            if (deltaTime < DRAW_INTERVAL) {
                try {
                    Thread.sleep(DRAW_INTERVAL - deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tickTime = System.currentTimeMillis();
        }
    }

    protected abstract void onSurfaceDraw(Canvas canvas);
}
