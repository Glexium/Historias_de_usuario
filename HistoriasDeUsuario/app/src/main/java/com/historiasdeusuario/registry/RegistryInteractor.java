package com.historiasdeusuario.registry;

import android.content.Context;
import com.historiasdeusuario.database.model.ModelUser;

public interface RegistryInteractor
{
    interface OnRegistryListener
    {
        void onMessage(String message, int duration);
        void moveToLogin();
    }

    void registry(ModelUser modelUser, OnRegistryListener listener, Context mContext);
}
