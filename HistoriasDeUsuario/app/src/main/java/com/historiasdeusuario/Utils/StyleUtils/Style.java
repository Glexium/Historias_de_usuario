package com.historiasdeusuario.Utils.StyleUtils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.historiasdeusuario.Utils.StyleUtils.Adapter.CustomFontsLoader;

import java.util.List;

public class Style
{
    public static void applyTypeface(ViewGroup v, Context mContext)
    {
        if(v != null)
        {
            int vgCount = v.getChildCount();
            for(int i=0;i<vgCount;i++)
            {
                if(v.getChildAt(i) == null) continue;
                if(v.getChildAt(i) instanceof ViewGroup)
                {
                    applyTypeface((ViewGroup) v.getChildAt(i), mContext);
                }
                else
                {
                    View view = v.getChildAt(i);
                    setStyle(view,mContext);
                }
            }
        }
    }

    public static void setAutomaticStyle(ViewGroup rootView, Context mContext)
    {
        int childViewCount = rootView.getChildCount();
        for (int i=0; i<childViewCount;i++)
        {
            View v = rootView.getChildAt(i);
            setStyle(v, mContext);
        }
    }

    public static void setStyle(View view, Context mContext)
    {
        try
        {
            if(view instanceof TextView)
            {
                TextView textView = (TextView)view;
                textView.setTypeface((textView.getTag()==null?
                        CustomFontsLoader.getTypeface(mContext, CustomFontsLoader.Font.OpenSansSemiBold):
                        CustomFontsLoader.getTypeface(mContext, Integer.parseInt(textView.getTag().toString()))));
            }
            if(view instanceof EditText)
            {
                ((EditText)view).setTypeface(CustomFontsLoader.getTypeface(mContext, CustomFontsLoader.Font.OpenSans));
            }
            if(view instanceof Button)
            {
                ((Button)view).setTypeface(CustomFontsLoader.getTypeface(mContext, CustomFontsLoader.Font.OpenSansBold));
            }
            if(view instanceof CheckBox)
            {
                ((CheckBox)view).setTypeface(CustomFontsLoader.getTypeface(mContext, CustomFontsLoader.Font.OpenSans));
            }
        }
        catch(Exception ex)
        {
            Log.d("Set Style", ex.getMessage());
        }
    }

    public static void setStyle(List<View> viewList, Boolean defaultFont, CustomFontsLoader.Font font, Context mContext)
    {
        try
        {
            for (View view : viewList)
            {
                if (view instanceof TextView)
                {
                    CustomFontsLoader.Font localFont = (defaultFont ? CustomFontsLoader.Font.OpenSans : font);
                    ((TextView) view).setTypeface(CustomFontsLoader.getTypeface(mContext, localFont));
                }
                if (view instanceof EditText)
                {
                    CustomFontsLoader.Font localFont = (defaultFont ? CustomFontsLoader.Font.OpenSans : font);
                    ((EditText) view).setTypeface(CustomFontsLoader.getTypeface(mContext, localFont));
                }
                if (view instanceof Button)
                {
                    CustomFontsLoader.Font localFont = (defaultFont ? CustomFontsLoader.Font.OpenSansBold : font);
                    ((Button) view).setTypeface(CustomFontsLoader.getTypeface(mContext, localFont));
                }
                if (view instanceof CheckBox)
                {
                    CustomFontsLoader.Font localFont = (defaultFont ? CustomFontsLoader.Font.OpenSans : font);
                    ((CheckBox) view).setTypeface(CustomFontsLoader.getTypeface(mContext, localFont));
                }
            }
        }
        catch(Exception ex)
        {
            Log.d("Set Style", ex.getMessage());
        }
    }

    public static void setTextSize(View view, boolean isTablet, int tabletFontSize)
    {
        if(isTablet) {
            if (view instanceof TextView)
            {
                TextView textView = (TextView)view;
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (textView.getHint()==null?tabletFontSize+2:tabletFontSize+4));
            }
            if (view instanceof EditText)
            {
                ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, tabletFontSize);
            }
            if (view instanceof Button)
            {
                ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, tabletFontSize+4);
            }
            if (view instanceof CheckBox)
            {
                ((CheckBox) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, tabletFontSize);
            }
        }
    }
}