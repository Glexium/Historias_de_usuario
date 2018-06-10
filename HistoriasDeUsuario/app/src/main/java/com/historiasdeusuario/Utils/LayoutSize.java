package com.historiasdeusuario.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicInteger;

public class LayoutSize{

    public static int GetSize(Activity activity)
    {
        int measuredWidth=0;
        if(activity!=null && !activity.isFinishing())
        {
            WindowManager w = activity.getWindowManager();
            {
                Point size = new Point();
                w.getDefaultDisplay().getSize(size);
                Double d = size.x * 0.8;
                measuredWidth = d.intValue();
            }
        }
        return measuredWidth;
    }

    public enum Type { Width, Height }

    public static int GetSize(Activity activity, Type type, Double porcent)
    {
        Double d=0.0;
        if(activity!=null && !activity.isFinishing())
        {
            WindowManager w = activity.getWindowManager();
            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            if (type.equals(Type.Width))
                d = size.x * porcent;
            else
                d = size.y * porcent;
        }
        return d.intValue();
    }

    public static int CenterHeight(Context mContext)
    {
        int height = LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.00);
        Double dHeight= (double) (height / 4);
        return dHeight.intValue();
    }

    public static int convertToDP(float originalValue, Context mContext)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, originalValue, mContext.getResources().getDisplayMetrics());
    }

    public static int getIdealTextSize(int baseSize, Context mContext)
    {
        try
        {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            float screenWidth  = dm.widthPixels / dm.xdpi;
            float screenHeight = dm.heightPixels / dm.ydpi;
            double size = Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
            if(size<=4)
                return baseSize;
            if(size<=5)
                return baseSize+4;
            if(size<=6)
                return baseSize+6;
            else
                return baseSize+11;
        }
        catch(Throwable t)
        {
            Log.d("LayoutSize", "Failed to compute screen size", t);
            return  baseSize;
        }
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {id}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}