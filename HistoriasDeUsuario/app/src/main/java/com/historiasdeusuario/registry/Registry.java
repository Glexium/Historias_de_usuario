package com.historiasdeusuario.registry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.LayoutSize;
import com.historiasdeusuario.Utils.Messages;
import com.historiasdeusuario.Utils.StyleUtils.Style;
import com.historiasdeusuario.Utils.ValidationUtil;
import com.historiasdeusuario.database.model.ModelUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Registry extends Activity implements RegistryView
{
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etPasswordConf) EditText etPasswordConf;
    @BindView(R.id.tvLogin) TextView tvLogin;
    @BindView(R.id.btnEmailRegistry) RelativeLayout btnRegistry;
    @BindView(R.id.rlOptions) RelativeLayout btnLogin;
    private Context mContext;
    private ProgressDialog dialog;
    private Boolean busy=false;
    private RegistryPresenter registryPresenter;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section_registry);
        mContext =this;
        registryPresenter = new RegistryPresenterImpl(this);
        // SingletonReSize.getInstance().setXY(mContext);
        unbinder = ButterKnife.bind(this);
        registryPresenter = new RegistryPresenterImpl(this);
        Style.applyTypeface(getWindow().getDecorView().findViewById(android.R.id.content), mContext);
        //tvLogin.setOnClickListener(view -> getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new LoginFragment(), "current").commitAllowingStateLoss());
        btnRegistry.setOnClickListener(view->
        {
            if(etPassword.getText().toString().equals(etPasswordConf.getText().toString()))
            {
                ModelUser modelUser = new ModelUser();
                modelUser.setEmail(etEmail.getText().toString());
                modelUser.setName(etName.getText().toString());
                modelUser.setLastName(etLastName.getText().toString());
                modelUser.setPassword(etPassword.getText().toString());
                Pattern pattern;
                Matcher matcher;
                final String EMAIL_PATTERN = "^[-_A-Za-z0-9]+(\\.[-_A-Za-z0-9]+)*@[-_A-Za-z0-9]+(\\.[-_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(modelUser.getEmail());
                if (!matcher.matches()) {
                    new Messages().Dialog("Aviso","Debes ingresar un correo valido", mContext);
                }
                else {
                    Boolean res = ValidationUtil.stringValidation(modelUser.getEmail(), modelUser.getName(), modelUser.getPassword(), modelUser.getLastName());
                    if (res) {
                        registryPresenter.registry(modelUser, mContext);
                    } else {
                        new Messages().Dialog("Aviso", "Datos incompletos", mContext);
                    }
                }
            }
            else
            {
                new Messages().Dialog("Aviso","El password y su confirmación son diferentes", mContext);
            }
        });
        btnLogin.setOnClickListener(view -> finish());
        ReSize();
    }

    private void ReSize()
    {
        Double y = ((double) LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.0));
        Double btnHeight = y * 0.10;
        btnRegistry.getLayoutParams().height = btnHeight.intValue();
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
    public void navigateToLogin() {
        finish();
    }

    @Override
    public void onDestroy()
    {
        registryPresenter =null;
        super.onDestroy();
    }
}