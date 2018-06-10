package com.historiasdeusuario.Utils.StyleUtils.Adapter;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Clase para cambiar los tipos de fuente
 */
public class CustomFontsLoader
{
    private static final int NUM_OF_CUSTOM_FONTS = 4;
    private static boolean fontsLoaded = false;
    private static Typeface[] fonts = new Typeface[4];

    private static String[] fontPath = {
            "fonts/sf.otf",
            "fonts/sf_bold.otf",
            "fonts/sf_light.otf",
            "fonts/sf_semibold.otf"
    };

    public enum Font
    {
        OpenSans(0), OpenSansBold(1), OpenSansLight(2), OpenSansSemiBold(3);
        private int type;
        Font(int i) { this.type=i;}
        public int getNumericType()
        {
            return type;
        }
    }

    /**
     * Returns a loaded custom font based on it's identifier.
     *
     * @param context - the current context
     * @param fontIdentifier = the identifier of the requested font
     *
     * @return Typeface object of the requested font.
     */
    public static Typeface getTypeface(Context context, Font fontIdentifier) {
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[fontIdentifier.getNumericType()];
    }

    public static Typeface getTypeface(Context context, int fontNumber)
    {
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[fontNumber];
    }

    private static void loadFonts(Context context) {
        for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
        }
        fontsLoaded = true;
    }
}