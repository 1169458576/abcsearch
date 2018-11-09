package com.zhzh.lib.abcsearch;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * @author yanshu
 * @Inc 杭州中镰网络科技有限公司
 * @email 1169458576@qq.com
 * @desc TODO
 * @createTime 2018/11/9 18:09
 */
public class PopAbcView extends PopupWindow{
    private final TextView popText;
    private float density = 1.0f;
    private Context mContext;
    public PopAbcView(Context context) {
        mContext=context;
        initPopupWindow();
        View view = View.inflate(context, R.layout.item_pop, null);
        popText=view.findViewById(R.id.text_pop);
        setContentView(view);
        setHeight(dp2px(mContext,40));
        setWidth(dp2px(mContext,45));

    }

    //初始化popwindow
    private void initPopupWindow() {
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        density = mContext.getResources().getDisplayMetrics().density;
    }


    /**
     *
     * @param parent 控件
     * @param x 显示点
     * @param y 显示点
     */
    public void showPopupWindow(View parent,float x,float y,String text) {
            popText.setText(text);
            showAtLocation(parent, Gravity.START,(int)x, (int)y);
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
