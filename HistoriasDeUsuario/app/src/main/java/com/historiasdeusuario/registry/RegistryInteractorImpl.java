package com.historiasdeusuario.registry;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.historiasdeusuario.database.helper.DatabaseHelper;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.objects.ObjResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistryInteractorImpl extends Fragment implements RegistryInteractor
{
    private Subscription registrySubscription;

    /**
     * Se encarga de procesar el registro
     * @param modelUser datos de usuario
     * @param listener listener de la rutina
     * @param mContext context de la rutina
     */
    @Override
    public void registry(final ModelUser modelUser, final OnRegistryListener listener, final Context mContext)
    {
        registrySubscription = setRegistry(modelUser, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                            if (objResponse != null)
                            {
                                if(objResponse.getIdResponse()>0)
                                {
                                    listener.onMessage("Usuario agregado exitosamente", 7000);
                                    listener.moveToLogin();
                                }
                                else if(objResponse.getIdResponse()==-2)
                                {
                                    listener.onMessage("Correo en uso", 7000);
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
    private Observable<ObjResponse> setRegistry(ModelUser modelUser, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).addUser(modelUser));
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
        if(registrySubscription!=null) registrySubscription.unsubscribe();
        super.onDestroy();
    }
}
