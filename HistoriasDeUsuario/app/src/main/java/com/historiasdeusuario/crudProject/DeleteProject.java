package com.historiasdeusuario.crudProject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.LayoutSize;
import com.historiasdeusuario.Utils.Messages;
import com.historiasdeusuario.Utils.StyleUtils.Style;
import com.historiasdeusuario.Utils.ValidationUtil;
import com.historiasdeusuario.Utils.adapters.CustomArrayAdapter;
import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.singleton.SingletonUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeleteProject extends Fragment implements CrudProjectView {
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.acProjectName)
    AutoCompleteTextView acProjectName;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.btnDeleteProject)
    RelativeLayout btnDeleteProject;
    private Unbinder unbinder;
    private ProgressDialog dialog;
    private Boolean busy = false;
    private Context mContext;
    private CrudProjectPresenter crudProjectPresenter;
    private ModelProject modelProject = new ModelProject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.section_delete_project, container, false);
        mContext = rootView.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        crudProjectPresenter = new CrudProjectPresenterImpl(this);
        ModelUser modelUser = SingletonUser.getInstance().getModelUser();
        crudProjectPresenter.getTypeList(mContext);
        crudProjectPresenter.getProjectList(String.valueOf(modelUser.getId()), mContext);
        Style.applyTypeface((ViewGroup)rootView, mContext);
        btnDeleteProject.setOnClickListener(view ->
        {
            modelProject.setUserId(modelUser.getId());
            modelProject.setDeleted(true);
            Boolean res = ValidationUtil.stringValidation(modelProject.getStartDate(), modelProject.getFinishDate(), modelProject.getName());
            if (res) {
                crudProjectPresenter.deleteProject(modelProject, mContext);
            } else {
                new Messages().Dialog("Aviso", "Datos incompletos", mContext);
            }
        });
        reSize();
        return rootView;
    }

    private void reSize() {
        Double y = ((double) LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.0));
        Double btnHeight = y * 0.07;
        btnDeleteProject.getLayoutParams().height = btnHeight.intValue();
    }

    private void setProjects(List<ModelProject> modelProjectList) {
        int textSize = LayoutSize.getIdealTextSize(11, mContext); //
        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(textSize, CustomArrayAdapter.Type.ProjectList, mContext, acProjectName.getId(), modelProjectList);
        acProjectName.setAdapter(customArrayAdapter);
        acProjectName.setOnItemClickListener((parent, view, position, id) -> {
            try {
                String name = modelProjectList.get(position).getName();
                modelProject.setName(name);
                modelProject.setStartDate(modelProjectList.get(position).getStartDate());
                modelProject.setFinishDate(modelProjectList.get(position).getFinishDate());
                modelProject.setName(modelProjectList.get(position).getName());
                modelProject.setTypeId(modelProjectList.get(position).getTypeId());
                modelProject.setDeleted(modelProjectList.get(position).getDeleted());
                modelProject.setId(modelProjectList.get(position).getId());
                tvStartDate.setText(modelProjectList.get(position).getStartDate());
                tvEndDate.setText(modelProjectList.get(position).getFinishDate());
                crudProjectPresenter.getTypeName(modelProjectList.get(position).getTypeId(), mContext);
            } catch (NullPointerException ex) {
                Log.e("Error", ex.getMessage());
            }
        });
        acProjectName.setOnClickListener(arg0 -> acProjectName.showDropDown());
    }

    /**
     * Abre el dialogo de progreso de que esta procesando algo
     */
    @Override
    public void showProgress() {
        if (!busy) {
            busy = true;
            dialog = new Messages().OpenDialog(mContext);
        }
    }

    /**
     * Cierra el dialogo de progreso de que esta procesando algo
     */
    @Override
    public void hideProgress() {
        busy = false;
        new Messages().CloseDialog(dialog);
    }

    @Override
    public void setMessage(String message, int duration) {
        new Messages().SystemToast(message, duration, mContext);
    }

    @Override
    public void clean() {
        tvType.setText("");
        tvEndDate.setText("");
        tvStartDate.setText("");
        acProjectName.setText("");
        modelProject = new ModelProject();
    }

    @Override
    public void setProjectList(List<ModelProject> modelProjects) {
        setProjects(modelProjects);
    }

    @Override
    public void setTypeList(List<ModelType> modelTypes) {
    }

    @Override
    public void setTypeName(String name) {
        tvType.setText(name);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }
}