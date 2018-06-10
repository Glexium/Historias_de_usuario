package com.historiasdeusuario.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.LayoutSize;
import com.historiasdeusuario.Utils.Messages;
import com.historiasdeusuario.database.helper.DatabaseHelper;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.navigation.NavigationMain;
import com.historiasdeusuario.objects.ObjLogin;
import com.historiasdeusuario.registry.Registry;
import com.historiasdeusuario.singleton.SingletonUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Login extends Activity implements LoginView
{
    @BindView(R.id.etEmail) EditText etMail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.tvRegistry) TextView tvRegistry;
    @BindView(R.id.btnLogin) RelativeLayout btnLogin;
    private LoginPresenter presenter;
    private Unbinder unbinder;
    private ProgressDialog dialog;
    private Boolean busy=false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext =this;
        presenter = new LoginPresenterImpl(this);
       // SingletonReSize.getInstance().setXY(mContext);
        unbinder = ButterKnife.bind(this);
        btnLogin.setOnClickListener(view -> {
            ObjLogin objLogin = new ObjLogin();
            objLogin.setEmail(etMail.getText().toString());
            objLogin.setPassword(etPassword.getText().toString());
            if(objLogin.getPassword().isEmpty() || objLogin.getEmail().isEmpty())
            {
                new Messages().SystemToast("Por favor complete todos los campos", 5000, mContext);
            }
            else
            {
                presenter.validateCredentials(objLogin, mContext);
            }
        });
        tvRegistry.setOnClickListener(view -> moveToRegistry());
        setResize();
        new DatabaseHelper(mContext).existsColumnInTable("id");
    }

    private void setResize()
    {
        Double y = ((double) LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.0));
        Double btnHeight = y * 0.10;
        btnLogin.getLayoutParams().height = btnHeight.intValue();
    }

    private void moveToRegistry()
    {
        startActivity(new Intent(this, Registry.class));
    }

    @Override
    public  void onDestroy()
    {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * Abre el dialogo de progreso de que esta procesando algo
     */
    @Override
    public void showProgress()
    {
        if(!busy)
        {
            busy=true;
            dialog = new Messages().OpenDialog(mContext);
        }
    }

    /**
     * Cierra el dialogo de progreso de que esta procesando algo
     */
    @Override
    public void hideProgress()
    {
        busy=false;
        new Messages().CloseDialog(dialog);
    }

    @Override
    public void setMessage(String message, int duration)
    {
        new Messages().SystemToast(message, duration, mContext);
    }


    @Override
    public void navigateToHome(ModelUser modelUser) {
        SingletonUser.getInstance().setModelUser(modelUser);
        startActivity(new Intent(this, NavigationMain.class));
    }
}
