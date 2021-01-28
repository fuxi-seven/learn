package com.hly.learn.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.hly.learn.R;

public class SurfaceViewFragment extends BaseFragment {
    private SurfaceView mSurfaceView1;
    private DrawingThread mDrawingThread;
    private SurfaceHolder mSurfaceHolder1;

    private SurfaceView mSurfaceView2;
    private SurfaceHolder mSurfaceHolder2;
    private Path mPath;
    private float mLastX, mLastY;
    private DrawThread mDrawThread;

    @Override
    public int getLayoutId() {
        return R.layout.surfaceview_layout;
    }

    @Override
    public void initData(View view) {
        mSurfaceView1 = (SurfaceView) view.findViewById(R.id.surface_view1);
        mSurfaceHolder1 = mSurfaceView1.getHolder();
        mSurfaceHolder1.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mDrawingThread = new DrawingThread(holder);
                mDrawingThread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mDrawingThread.mQuit = true;
            }
        });
        mSurfaceView2 = (SurfaceView) view.findViewById(R.id.surface_view2);
        mSurfaceView2.setFocusable(true);
        mSurfaceView2.setFocusableInTouchMode(true);
        mSurfaceView2.setKeepScreenOn(true);
        mSurfaceView2.setOnTouchListener(mTouchListener);
        mSurfaceHolder2 = mSurfaceView2.getHolder();
        mSurfaceHolder2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mPath = new Path();//初始化Path
                mDrawThread = new DrawThread(holder);
                mDrawThread.isDrawing = true;
                mDrawThread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    static final class MovingPoint {
        float x, y, dx, dy;

        void init(int width, int height, float minStep) {
            x = (float) ((width - 1) * Math.random());
            y = (float) ((height - 1) * Math.random());
            dx = (float) (Math.random() * minStep * 2) + 1;
            dy = (float) (Math.random() * minStep * 2) + 1;
        }

        float adjDelta(float cur, float minStep, float maxStep) {
            cur += (Math.random() * minStep) - (minStep / 2);
            if (cur < 0 && cur > -minStep) cur = -minStep;
            if (cur >= 0 && cur < minStep) cur = minStep;
            if (cur > maxStep) cur = maxStep;
            if (cur < -maxStep) cur = -maxStep;
            return cur;
        }

        void step(int width, int height, float minStep, float maxStep) {
            x += dx;
            if (x <= 0 || x >= (width - 1)) {
                if (x <= 0) {
                    x = 0;
                } else if (x >= (width - 1)) x = width - 1;
                dx = adjDelta(-dx, minStep, maxStep);
            }
            y += dy;
            if (y <= 0 || y >= (height - 1)) {
                if (y <= 0) {
                    y = 0;
                } else if (y >= (height - 1)) y = height - 1;
                dy = adjDelta(-dy, minStep, maxStep);
            }
        }
    }

    class DrawingThread extends Thread {
        // These are protected by the Thread's lock
        SurfaceHolder surfaceHolder;
        boolean mRunning;
        boolean mActive;
        boolean mQuit;
        // Internal state
        int mLineWidth;
        float mMinStep;
        float mMaxStep;
        boolean mInitialized = false;
        final MovingPoint mPoint1 = new MovingPoint();
        final MovingPoint mPoint2 = new MovingPoint();
        static final int NUM_OLD = 100;
        int mNumOld = 0;
        final float[] mOld = new float[NUM_OLD * 4];
        final int[] mOldColor = new int[NUM_OLD];
        int mBrightLine = 0;
        // X is red, Y is blue
        final MovingPoint mColorPoint = new MovingPoint();
        final Paint mBackgroundPaint = new Paint();
        final Paint mForegroundPaint = new Paint();

        public DrawingThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        int makeGreen(int index) {
            int dist = Math.abs(mBrightLine - index);
            if (dist > 10) return 0;
            return (255 - (dist * (255 / 10))) << 8;
        }

        @Override
        public void run() {
            mLineWidth = (int) (getResources().getDisplayMetrics().density * 1.5);
            if (mLineWidth < 1) mLineWidth = 1;
            mMinStep = mLineWidth * 2;
            mMaxStep = mMinStep * 3;
            mBackgroundPaint.setColor(0xffffffff);
            mForegroundPaint.setColor(0xff00ffff);
            mForegroundPaint.setAntiAlias(false);
            mForegroundPaint.setStrokeWidth(mLineWidth);
            while (true) {
                if (mQuit) {
                    return;
                }
                // Lock the canvas for drawing
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas == null) {
                    Log.i("WindowSurface", "Failure locking canvas");
                    continue;
                }
                // Update graphics
                if (!mInitialized) {
                    mInitialized = true;
                    mPoint1.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
                    mPoint2.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
                    mColorPoint.init(127, 127, 1);
                } else {
                    mPoint1.step(canvas.getWidth(), canvas.getHeight(), mMinStep, mMaxStep);
                    mPoint2.step(canvas.getWidth(), canvas.getHeight(), mMinStep, mMaxStep);
                    mColorPoint.step(127, 127, 1, 3);
                }
                mBrightLine += 2;
                if (mBrightLine > (NUM_OLD * 2)) {
                    mBrightLine = -2;
                }
                // Clear background
                canvas.drawColor(mBackgroundPaint.getColor());
                // Draw old lines
                for (int i = mNumOld - 1; i >= 0; i--) {
                    mForegroundPaint.setColor(mOldColor[i] | makeGreen(i));
                    mForegroundPaint.setAlpha(((NUM_OLD - i) * 255) / NUM_OLD);
                    int p = i * 4;
                    canvas.drawLine(mOld[p], mOld[p + 1], mOld[p + 2], mOld[p + 3],
                            mForegroundPaint);
                }
                // Draw new line
                int red = (int) mColorPoint.x + 128;
                if (red > 255) red = 255;
                int blue = (int) mColorPoint.y + 128;
                if (blue > 255) blue = 255;
                int color = 0xff000000 | (red << 16) | blue;
                mForegroundPaint.setColor(color | makeGreen(-2));
                canvas.drawLine(mPoint1.x, mPoint1.y, mPoint2.x, mPoint2.y, mForegroundPaint);
                // Add in the new line
                if (mNumOld > 1) {
                    System.arraycopy(mOld, 0, mOld, 4, (mNumOld - 1) * 4);
                    System.arraycopy(mOldColor, 0, mOldColor, 1, mNumOld - 1);
                }
                if (mNumOld < NUM_OLD) mNumOld++;
                mOld[0] = mPoint1.x;
                mOld[1] = mPoint1.y;
                mOld[2] = mPoint2.x;
                mOld[3] = mPoint2.y;
                mOldColor[0] = color;
                // All done
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDrawThread = new DrawThread(mSurfaceHolder2);
                    mDrawThread.isDrawing = true;
                    mDrawThread.start();
                    mLastX = x;
                    mLastY = y;
                    mPath.moveTo(mLastX, mLastY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = Math.abs(x - mLastX);
                    float dy = Math.abs(y - mLastY);
                    if (dx >= 3 || dy >= 3) {
                        mPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                    }
                    mLastX = x;
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    mDrawThread.isDrawing = false;
                    break;
            }
            return true;

        }
    };

    class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private Paint mPaint;
        private Canvas mCanvas;
        boolean isDrawing;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            init();
        }

        private void init() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mPaint.setStrokeWidth(10f);
            mPaint.setColor(Color.parseColor("#FF4081"));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            //mPath = new Path();//如果在此处初始化，每次开启线程会把之前的擦除，原因1
        }

        @Override
        public void run() {
            while (isDrawing) {
                try {
                    mCanvas = surfaceHolder.lockCanvas();
                    mCanvas.drawColor(Color.LTGRAY);//原因1：每次都擦除
                    mCanvas.drawPath(mPath, mPaint);
                } finally {
                    if (mCanvas != null) {
                        surfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
        }
    }
}
