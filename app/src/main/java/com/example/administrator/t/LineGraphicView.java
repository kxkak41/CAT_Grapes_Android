package com.example.administrator.t;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/7/28.
 */
public class LineGraphicView extends View {
            /**
         *
         */
        private static final int CIRCLE_SIZE = 10;

        private static enum Linestyle
        {
            Line, Curve
        }

        private Context mContext;
        private Paint mPaint;
        private Resources res;
        private DisplayMetrics dm;

        /**
         * data
         */
        private Linestyle mStyle = Linestyle.Curve;

        private int canvasHeight;
        private int canvasWidth;
        private int bheight = 0;
        private int blwidh;
        private boolean isMeasure = true;
        /**
         *
         */
        private int maxValue;
        /**
         *
         */
        private int averageValue;
        private int marginTop = 40;
        private int marginBottom = 80;

        /**
         *
         */
        private Point[] mPoints;
        /**
         *
         */
        private ArrayList<Double> yRawData;
        /**
         *
         */
        private ArrayList<String> xRawDatas;
        private ArrayList<Integer> xList = new ArrayList<Integer>();//
        private int spacingHeight;

        public LineGraphicView(Context context)
        {
            this(context, null);
        }

        public LineGraphicView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            this.mContext = context;
            initView();
        }

        private void initView()
        {
            this.res = mContext.getResources();
            this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            if (isMeasure)
            {
                this.canvasHeight = getHeight();
                this.canvasWidth = getWidth();
                if (bheight == 0)
                    bheight = (int) (canvasHeight - marginBottom);
                blwidh = dip2px(30);
                isMeasure = false;
            }
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            mPaint.setColor(res.getColor(R.color.grey));

            drawAllXLine(canvas);
            //
            drawAllYLine(canvas);
            //
            mPoints = getPoints();

            mPaint.setColor(res.getColor(R.color.red));
            mPaint.setStrokeWidth(dip2px(2.5f));
            mPaint.setStyle(Style.STROKE);
            if (mStyle == Linestyle.Curve)
            {
                drawScrollLine(canvas);
            }
            else
            {
                drawLine(canvas);
            }

            mPaint.setStyle(Style.FILL);
            for (int i = 0; i < mPoints.length; i++)
            {
                canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
            }
        }

        /**
         *
         */
        private void drawAllXLine(Canvas canvas)
        {
            for (int i = 0; i < spacingHeight + 1; i++)
            {
                canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop, (canvasWidth - blwidh),
                        bheight - (bheight / spacingHeight) * i + marginTop, mPaint);//
                drawText(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop,
                        canvas);
            }
        }

        /**
         *
         */
        private void drawAllYLine(Canvas canvas)
        {
            int jump = yRawData.size()/6;

            for (int i = 0; i < yRawData.size(); i++)
            {
                xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
                if(i==0){
                    canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawData.size() * i, marginTop, blwidh
                        + (canvasWidth - blwidh) / yRawData.size() * i, bheight + marginTop, mPaint);}
                if((i%jump == 0)&&(i<yRawData.size()-jump)) {
                    drawText(xRawDatas.get(i), blwidh + (canvasWidth - blwidh) / yRawData.size() * (i), bheight + dip2px(32), canvas);}
            }
        }

        private void drawScrollLine(Canvas canvas)
        {
            Point startp = new Point();
            Point endp = new Point();
            for (int i = 0; i < mPoints.length - 1; i++)
            {
                startp = mPoints[i];
                endp = mPoints[i + 1];
                int wt = (startp.x + endp.x) / 2;
                Point p3 = new Point();
                Point p4 = new Point();
                p3.y = startp.y;
                p3.x = wt;
                p4.y = endp.y;
                p4.x = wt;

                Path path = new Path();
                path.moveTo(startp.x, startp.y);
                path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
                canvas.drawPath(path, mPaint);
            }
        }

        private void drawLine(Canvas canvas)
        {
            Point startp = new Point();
            Point endp = new Point();
            for (int i = 0; i < mPoints.length - 1; i++)
            {
                startp = mPoints[i];
                endp = mPoints[i + 1];
                canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
            }
        }

        private void drawText(String text, int x, int y, Canvas canvas)
        {
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setTextSize(dip2px(12));
            p.setColor(res.getColor(R.color.black));
            p.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(text, x, y, p);
        }

        private Point[] getPoints()
        {
            Point[] points = new Point[yRawData.size()];
            for (int i = 0; i < yRawData.size(); i++)
            {
                int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));

                points[i] = new Point(xList.get(i), ph + marginTop);
            }
            return points;
        }

        public void setData(ArrayList<Double> yRawData, ArrayList<String> xRawData, int maxValue, int averageValue)
        {
            this.maxValue = maxValue;
            this.averageValue = averageValue;
            this.mPoints = new Point[yRawData.size()];
            this.xRawDatas = xRawData;
            this.yRawData = yRawData;
            this.spacingHeight = maxValue / averageValue;
        }

        public void setTotalvalue(int maxValue)
        {
            this.maxValue = maxValue;
        }

        public void setPjvalue(int averageValue)
        {
            this.averageValue = averageValue;
        }

        public void setMargint(int marginTop)
        {
            this.marginTop = marginTop;
        }

        public void setMarginb(int marginBottom)
        {
            this.marginBottom = marginBottom;
        }

        public void setMstyle(Linestyle mStyle)
        {
            this.mStyle = mStyle;
        }

        public void setBheight(int bheight)
        {
            this.bheight = bheight;
        }

        /**
         *
         */
        private int dip2px(float dpValue)
        {
            return (int) (dpValue * dm.density + 0.5f);
        }

    }

