package com.historiasdeusuario.crudProject;

import android.content.Context;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;

import java.util.List;

/**
 * Created by MODL
 */
interface CrudProjectInteractor
{
    interface OnCrudListener
    {
        void onMessage(String message, int duration);
        void onSuccess();
    }

    interface OnGetProjectsListener
    {
        void onMessage(String message, int duration);
        void onGotProjects(List<ModelProject> modelProjects);
    }

    interface OnGetTypeListener
    {
        void onMessage(String message, int duration);
        void onGotTypes(List<ModelType> modelTypes);
    }

    interface OnGetTypeNameListener
    {
        void onMessage(String message, int duration);
        void onSetType(String name);
    }

    void addProject(ModelProject modelProject, OnCrudListener listener, Context mContext);
    void deleteProject(ModelProject modelProject, OnCrudListener listener, Context mContext);
    void updateProject(ModelProject modelProject, OnCrudListener listener, Context mContext);
    void getProjectList(String userId, OnGetProjectsListener listener, Context mContext);
    void getTypeList(OnGetTypeListener listener, Context mContext);
    void getTypeName(int id, OnGetTypeNameListener listener, Context mContext);
}
