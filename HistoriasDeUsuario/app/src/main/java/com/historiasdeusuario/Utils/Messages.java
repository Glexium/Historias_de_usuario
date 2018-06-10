package com.historiasdeusuario.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.StyleUtils.Style;

public class Messages
{
    public void Dialog(final String title, final String message, final Context mContext)
    {
        if(Looper.myLooper()==null)
            Looper.prepare();
        if(!((Activity) mContext).isFinishing())
        {
            float textSize = LayoutSize.getIdealTextSize(12, mContext);
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.dialog_warning, null);
            final Button btnAceptar = promptView.findViewById(R.id.btnOk);
            final TextView tvTitle = promptView.findViewById(R.id.tvTitle);
            final TextView tvMessage = promptView.findViewById(R.id.tvMessage);
            btnAceptar.setTextSize(textSize);
            tvTitle.setTextSize(textSize);
            tvMessage.setTextSize(textSize);
            tvTitle.setText(title);
            tvMessage.setText(message);
            Style.applyTypeface((ViewGroup) promptView.getRootView(), mContext);
            final android.app.Dialog d = new android.app.Dialog(mContext);
            if (d.getWindow() != null)
                d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(promptView);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.setCanceledOnTouchOutside(false);
            d.setOnShowListener(dialog -> btnAceptar.setOnClickListener(view -> d.dismiss()));
            d.show();
        }
    }

    public void SystemToast(final String message, final int duration, final Context mContext)
    {
        if(Looper.myLooper()==null)
            Looper.prepare();
        if(mContext!=null && !((Activity) mContext).isFinishing())
        {
            if(!((Activity) mContext).isFinishing())
            {
                final Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                CountDownTimer toastCountDown;
                toastCountDown = new CountDownTimer(duration, 1000 /*Tick duration*/) {
                    public void onTick(long millisUntilFinished)
                    {
                        toast.show();
                    }
                    public void onFinish() {
                        toast.cancel();
                    }
                };
                if (!((Activity)mContext).isFinishing())
                    toast.show();
                toastCountDown.start();
            }
        }
    }

    public ProgressDialog OpenDialog(Context mContext)
    {
        if(Looper.myLooper()==null)
            Looper.prepare();
        final ProgressDialog[] dialog = new ProgressDialog[1];
        if(mContext!=null && !((Activity) mContext).isFinishing())
            dialog[0] = ProgressDialog.show(mContext, "Procesando Datos", " No toque la pantalla", true);
        return dialog[0];
    }

    public void CloseDialog(final ProgressDialog dialog) {
        if(dialog!=null)  {
            dialog.dismiss();
        }
    }
}
