package com.historiasdeusuario.crudProject;

import android.content.Context;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;

import java.util.List;

public class CrudProjectPresenterImpl implements CrudProjectPresenter, CrudProjectInteractor.OnCrudListener, CrudProjectInteractor.OnGetProjectsListener, CrudProjectInteractor.OnGetTypeListener, CrudProjectInteractor.OnGetTypeNameListener
{
    private CrudProjectInteractor crudProjectInteractor;
    private CrudProjectView crudProjectView;

    public CrudProjectPresenterImpl(CrudProjectView crudProjectView)
    {
        this.crudProjectView = crudProjectView;
        this.crudProjectInteractor = new CrudProjectInteractorImpl();
    }

    @Override
    public void onMessage(String message, int duration)
    {
        if(crudProjectView !=null)
        {
            crudProjectView.hideProgress();
            crudProjectView.setMessage(message, duration);
        }
    }

    @Override
    public void onSetType(String name) {
        if(crudProjectView !=null)
        {
            crudProjectView.hideProgress();
            crudProjectView.setTypeName(name);
        }
    }

    @Override
    public void onGotTypes(List<ModelType> modelTypes) {
        if(crudProjectView!=null)
        {
            crudProjectView.hideProgress();
            crudProjectView.setTypeList(modelTypes);
        }
    }

    @Override
    public void onGotProjects(List<ModelProject> modelProjects) {
        if(crudProjectView!=null)
        {
            crudProjectView.hideProgress();
            crudProjectView.setProjectList(modelProjects);
        }
    }

    @Override
    public void onSuccess()
    {
        if(crudProjectView !=null)
        {
            crudProjectView.hideProgress();
            crudProjectView.clean();
        }
    }

    @Override
    public void addProject(ModelProject modelProject, Context mContext)
    {
        if(crudProjectView !=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.addProject(modelProject, this, mContext);
        }
    }

    @Override
    public void deleteProject(ModelProject modelProject, Context mContext) {
        if(crudProjectView!=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.deleteProject(modelProject, this, mContext);
        }
    }

    @Override
    public void updateProject(ModelProject modelProject, Context mContext) {
        if(crudProjectView!=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.updateProject(modelProject, this, mContext);
        }
    }

    @Override
    public void getProjectList(String userId, Context mContext) {
        if(crudProjectView!=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.getProjectList(userId, this, mContext);
        }
    }

    @Override
    public void getTypeList(Context mContext) {
        if(crudProjectView!=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.getTypeList(this, mContext);
        }
    }

    @Override
    public void getTypeName(int id, Context mContext) {
        if(crudProjectView !=null)
        {
            crudProjectView.showProgress();
            crudProjectInteractor.getTypeName(id, this, mContext);
        }
    }
}
