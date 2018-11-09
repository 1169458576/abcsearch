package com.zhzh.lib.abcsearch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author yanshu
 * @Inc 杭州中镰网络科技有限公司
 * @email 1169458576@qq.com
 * @desc TODO
 * @createTime 2018/11/9 14:34
 */
public class AbcSearchView extends ViewGroup {

    private static final String TAG="AbcSearchView";

    private final static String[] DEFAULT_INDEX_ITEMS = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    /**
     * 默认字母大小
     * sp
     */
    private final static int DEFAULT_TEXT_SIZE = 12;

    /**
     * 默认字体颜色
     */
    private final static int DEFAULT_TEXT_COLOR = Color.parseColor("#FF666666");

    /**
     * 默认距离顶部
     * dp
     */
    private final static int DEFAULT_PADDING_TOP = 0;

    /**
     * 默认单个字母所占方格宽高
     * dp
     */
    private final static int DEFAULT_PRE_WIDTH = 18;

    /**
     * 默认每个空格距离顶部底部高度
     * dp
     */
    private final static int DEFAULT_PRE_PADDING = 0;

    /**
     * 选中字体颜色
     */
    private final static int DEFAULT_SELECT_TEXT_COLOR = Color.WHITE;

    /**
     * 选中背景颜色
     */
    private final static int DEFAULT_SELECT_BG_COLOR = Color.parseColor("#FF4D7BFF");

    private final Context mContext;

    /**
     * 字母画笔
     */
    private TextPaint textPaint;

    /**
     * 选中字母画笔
     */
    private TextPaint selectTextPaint;

    /**
     * 背景画笔
     */
    private Paint bgPaint;

    /**
     * 选中字母圆圈背景画笔
     */
    private Paint selectBgPaint;


    private int textSize;
    private int paddingTop;
    private int textColor;
    private int preWidth;
    private int prePadding;
    private int selectTextColor;
    private int selectBgColor;

    private String[] mData;
    private ArrayList<CenterPoint> pointList;
    private int mWidth;
    private int mHeight;

    private int currentPosition=-1;

    private boolean isUsing=false;


    class CenterPoint {
        private float x;
        private float y;

        float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        CenterPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public AbcSearchView(Context context) {
        this(context, null);
    }

    public AbcSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbcSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        textSize = sp2px(mContext, DEFAULT_TEXT_SIZE);
        paddingTop = dp2px(mContext, DEFAULT_PADDING_TOP);
        textColor = DEFAULT_TEXT_COLOR;
        preWidth = dp2px(mContext, DEFAULT_PRE_WIDTH);
        prePadding = dp2px(mContext, DEFAULT_PRE_PADDING);
        selectTextColor = DEFAULT_SELECT_TEXT_COLOR;
        selectBgColor = DEFAULT_SELECT_BG_COLOR;
        mData = DEFAULT_INDEX_ITEMS;
        textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        bgPaint = new Paint();
        bgPaint.setColor(Color.TRANSPARENT);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        selectBgPaint=new Paint();
        selectBgPaint.setColor(selectBgColor);
        selectBgPaint.setStyle(Paint.Style.FILL);
        selectBgPaint.setAntiAlias(true);
        selectTextPaint=new TextPaint();
        selectTextPaint.setTextSize(textSize);
        selectTextPaint.setColor(selectTextColor);
        selectTextPaint.setAntiAlias(true);
        selectTextPaint.setTextAlign(Paint.Align.CENTER);
        setWillNotDraw(false);
    }


    private void computeCenterPoints() {
        if ((preWidth + prePadding * 2) * mData.length < mHeight) {
            prePadding = prePadding + ((mHeight - (preWidth + prePadding * 2) * mData.length) / mData.length / 2);
        }
        pointList = new ArrayList<>();
        for (int i = 0; i < mData.length; i++) {
            float x = mWidth / 2;
            float y = (preWidth + prePadding * 2) * (i + 0.5f) + paddingTop;
            CenterPoint point = new CenterPoint(x, y);
            pointList.add(point);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        mWidth = widthSpecSize;
        mHeight = heightSpecSize;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            mWidth = preWidth;
            mHeight = (preWidth + prePadding * 2) * mData.length;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = preWidth;
            mHeight = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
            mHeight = (preWidth + prePadding * 2) * mData.length;
        }
        setMeasuredDimension(mWidth, mHeight);
        computeCenterPoints();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mData.length; i++) {
            CenterPoint point = pointList.get(i);
            if(isUsing){
                if(currentPosition==i){
                    canvas.drawCircle(point.getX(),point.getY(),preWidth/2,selectBgPaint);
                    canvas.drawText(mData[i], point.getX(),
                            computeBaseLine(selectTextPaint, mData[i], point.getY()),
                            selectTextPaint);
                    canvas.drawPoint(-100,-100,selectBgPaint);
                }else{
                    canvas.drawCircle(point.getX(),point.getY(),preWidth/2,bgPaint);
                    canvas.drawText(mData[i], point.getX(),
                            computeBaseLine(textPaint, mData[i], point.getY()),
                            textPaint);
                }
            }else{
                canvas.drawCircle(point.getX(),point.getY(),preWidth/2,bgPaint);
                canvas.drawText(mData[i], point.getX(),
                        computeBaseLine(textPaint, mData[i], point.getY()),
                        textPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isUsing=false;
                break;
            default:
                isUsing=true;
                currentPosition=getSelectPosition(event.getX(),event.getY());
                break;
        }
        invalidate();
        return true;
    }

    private int getSelectPosition(float x,float y){
        Log.d(TAG," x= "+x+"  y= "+y);
        return (int)(y/(preWidth+prePadding*2));
    }

    private float computeBaseLine(TextPaint paint, String text, float y) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.height();
        return y + height / 2;
    }

    public static int dp2px(Context context, float dpVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);

    }


    public static int sp2px(Context context, float spVal) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }
}
