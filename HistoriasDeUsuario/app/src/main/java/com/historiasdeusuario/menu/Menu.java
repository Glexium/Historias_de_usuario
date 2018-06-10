package com.historiasdeusuario.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.LayoutSize;
import com.historiasdeusuario.Utils.StyleUtils.Style;
import com.historiasdeusuario.crudProject.AddProject;
import com.historiasdeusuario.crudProject.DeleteProject;
import com.historiasdeusuario.crudProject.UpdateProject;
import com.historiasdeusuario.crudProject.ViewProject;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.login.Login;
import com.historiasdeusuario.singleton.SingletonUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Menu extends Fragment
{
    @BindView(R.id.btnAddProject) RelativeLayout btnAddProject;
    @BindView(R.id.btnDeleteProject) RelativeLayout btnDeleteProject;
    @BindView(R.id.btnEditProject) RelativeLayout btnEditProject;
    @BindView(R.id.btnViewProjects) RelativeLayout btnViewProject;
    @BindView(R.id.btnLogOut) RelativeLayout btnLogOut;
    @BindView(R.id.tvUser) TextView tvUser;
    private Context mContext;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.section_menu, container, false);
        mContext = rootView.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        ModelUser modelUser = SingletonUser.getInstance().getModelUser();
        String user=modelUser.getName()+" "+modelUser.getLastName();
        tvUser.setText(user);
        Style.applyTypeface((ViewGroup)rootView, mContext);
        btnAddProject.setOnClickListener(view -> {
            if(getFragmentManager()!=null) getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new AddProject()).commit();
        });
        btnDeleteProject.setOnClickListener(view -> {
            if(getFragmentManager()!=null) getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new DeleteProject()).commit();
        });
        btnEditProject.setOnClickListener(view -> {
            if(getFragmentManager()!=null) getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new UpdateProject()).commit();
        });

        btnViewProject.setOnClickListener(view -> {
            if(getFragmentManager()!=null) getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new ViewProject()).commit();
        });
        btnLogOut.setOnClickListener(view -> {
            if(getFragmentManager()!=null) getFragmentManager().popBackStack();
            startActivity(new Intent(mContext, Login.class));
            SingletonUser.getInstance().setModelUser(new ModelUser());
        });
        ReSize();
        return rootView;
    }

    private void ReSize()
    {
        Double y = ((double) LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.0));
        Double btnHeight = y * 0.07;
        btnAddProject.getLayoutParams().height = btnHeight.intValue();
        btnDeleteProject.getLayoutParams().height = btnHeight.intValue();
        btnEditProject.getLayoutParams().height = btnHeight.intValue();
        btnViewProject.getLayoutParams().height = btnHeight.intValue();
        btnLogOut.getLayoutParams().height = btnHeight.intValue();
    }

    @Override
    public void onDestroy()
    {
        unbinder.unbind();
        unbinder=null;
        mContext=null;
        super.onDestroy();
    }
}
