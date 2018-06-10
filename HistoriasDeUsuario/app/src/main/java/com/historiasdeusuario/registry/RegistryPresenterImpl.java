package com.historiasdeusuario.registry;

import android.content.Context;

import com.historiasdeusuario.database.model.ModelUser;

public class RegistryPresenterImpl implements RegistryPresenter, RegistryInteractor.OnRegistryListener
{
    private RegistryInteractor registryInteractor;
    private RegistryView registryView;

    public RegistryPresenterImpl(RegistryView registryView)
    {
        this.registryView = registryView;
        this.registryInteractor = new RegistryInteractorImpl();
    }

    @Override
    public void onMessage(String message, int duration)
    {
        if(registryView!=null)
        {
            registryView.hideProgress();
            registryView.setMessage(message, duration);
        }
    }

    @Override
    public void moveToLogin()
    {
        if(registryView!=null)
        {
            registryView.hideProgress();
            registryView.navigateToLogin();
        }
    }

    @Override
    public void registry(ModelUser modelUser, Context mContext)
    {
        if(registryView!=null)
        {
            registryView.showProgress();
            registryInteractor.registry(modelUser, this, mContext);
        }
    }
}