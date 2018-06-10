package com.historiasdeusuario.registry;

import android.content.Context;
import com.historiasdeusuario.database.model.ModelUser;

public interface RegistryPresenter
{
    void registry(ModelUser modelUser, Context mContext);
}
