package com.historiasdeusuario.registry;

public interface RegistryView
{
    void showProgress();
    void hideProgress();
    void setMessage(String message, int duration);
    void navigateToLogin();
}
