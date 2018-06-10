package com.historiasdeusuario.crudProject;

import android.content.Context;
import com.historiasdeusuario.database.model.ModelProject;

public interface CrudProjectPresenter
{
    void addProject(ModelProject modelProject, Context mContext);
    void deleteProject(ModelProject modelProject, Context mContext);
    void updateProject(ModelProject modelProject, Context mContext);
    void getProjectList(String userId, Context mContext);
    void getTypeList(Context mContext);
    void getTypeName(int id, Context mContext);
}