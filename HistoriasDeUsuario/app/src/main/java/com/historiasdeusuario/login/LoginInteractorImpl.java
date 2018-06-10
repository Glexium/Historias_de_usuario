package com.historiasdeusuario.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.historiasdeusuario.database.helper.DatabaseHelper;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.objects.ObjLogin;
import com.historiasdeusuario.objects.ObjResponse;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MODL
 */
public class LoginInteractorImpl extends Fragment implements LoginInteractor
{
    private Subscription loginSubscription;

    /**
     * Se encarga de procesar el login
     * @param objLogin usuario - password
     * @param listener listener de la rutina
     * @param mContext context de la rutina
     */
    @Override
    public void login(final ObjLogin objLogin, final OnLoginFinishedListener listener, final Context mContext)
    {
        loginSubscription = getLogin(objLogin, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(modelUser -> {
                if (modelUser != null)
                {
                    if(modelUser.getId()>0)
                    {
                        listener.onSuccess(modelUser);
                    }
                    else
                    {
                        listener.onMessage("Usuario o contraseña no validos", 7000);
                    }
                }
            },
                    error ->
                            listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    /**
     * Se encarga de validar las credenciales del usuario
     */
    @NonNull
    private Observable<ModelUser> getLogin(ObjLogin objLogin, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).getUser(objLogin));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    @Override
    public void onDestroy()
    {
        if(loginSubscription!=null) loginSubscription.unsubscribe();
        super.onDestroy();
    }
}