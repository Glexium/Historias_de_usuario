package com.historiasdeusuario.singleton;

import com.historiasdeusuario.database.model.ModelUser;

public class SingletonUser
{
    private static SingletonUser mInstance = null;

    private ModelUser modelUser;

    public static SingletonUser getInstance()
    {
        if (mInstance == null)
        {
            synchronized (SingletonUser.class)
            {
                mInstance = new SingletonUser();
            }
        }
        return mInstance;
    }

    public ModelUser getModelUser() {
        return modelUser;
    }

    public void setModelUser(ModelUser modelUser) {
        this.modelUser = modelUser;
    }
}