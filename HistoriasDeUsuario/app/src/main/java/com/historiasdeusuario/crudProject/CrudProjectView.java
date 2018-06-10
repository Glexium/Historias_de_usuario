package com.historiasdeusuario.crudProject;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;

import java.util.List;

public interface CrudProjectView
{
    void showProgress();
    void hideProgress();
    void setMessage(String message, int duration);
    void clean();
    void setProjectList(List<ModelProject> modelProjects);
    void setTypeList(List<ModelType> modelTypes);
    void setTypeName(String name);
}
