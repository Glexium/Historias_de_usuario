package com.historiasdeusuario.login;

import android.content.Context;

import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.objects.ObjLogin;

/**
 * Created by MODL
 */
interface LoginInteractor
{
    interface OnLoginFinishedListener
    {
        void onMessage(String message, int duration);
        void onSuccess(ModelUser modelUser);
    }

    void login(ObjLogin objLogin, OnLoginFinishedListener listener, Context mContext);
}
