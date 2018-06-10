package com.historiasdeusuario.login;

import com.historiasdeusuario.database.model.ModelUser;

/**
 * Created by MODL
 */
interface LoginView
{
    /****************************Metodos disponibles para la vista*********************************/
    void showProgress();
    void hideProgress();
    void setMessage(String message, int duration);
    void navigateToHome(ModelUser modelUser);
}
