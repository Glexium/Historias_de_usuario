package com.historiasdeusuario.login;

import android.content.Context;

import com.historiasdeusuario.objects.ObjLogin;

/**
 * Created by MODL
 */
interface LoginPresenter
{
    void validateCredentials(ObjLogin objLogin, Context mContext);
    void onDestroy();
}
