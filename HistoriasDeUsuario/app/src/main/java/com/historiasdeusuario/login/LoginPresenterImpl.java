package com.historiasdeusuario.login;
import android.content.Context;

import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.objects.ObjLogin;

/**
 * Created by MODL
 */
class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener
{
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    /**
     * Necesario para instanciar la implementacion del interactor y de asignarle su vista
     * @param loginView view
     */
    LoginPresenterImpl(LoginView loginView)
    {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    /**
     * Llama al interactor para ejecutar la rutina de login, ademas abre el dialogo de progreso
     * @param objLogin usuario - password
     * @param mContext contexto para llamar a la DB
     */
    @Override
    public void validateCredentials(ObjLogin objLogin, Context mContext) {
        if (loginView != null) {
            loginView.showProgress();
            loginInteractor.login(objLogin, this, mContext);
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onMessage(String message, int duration)
    {
        if(loginView!=null)
        {
            loginView.hideProgress();
            loginView.setMessage(message, duration);
        }
    }

    /**
     * Manda avisar a la interfaz que cierre el dialogo de progreso y que redireccion a la pantalla principal
     */
    @Override
    public void onSuccess(ModelUser modelUser) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.navigateToHome(modelUser);
        }
    }

}