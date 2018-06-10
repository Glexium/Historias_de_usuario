package com.historiasdeusuario.objects;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;

import java.util.List;

public class ObjResponse
{
    private int idResponse;
    private int userId;

    public int getIdResponse() {
        return idResponse;
    }

    public void setIdResponse(int idResponse) {
        this.idResponse = idResponse;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private List<ModelProject> modelProjects;

    public List<ModelProject> getModelProjects() {
        return modelProjects;
    }

    public void setModelProjects(List<ModelProject> modelProjects) {
        this.modelProjects = modelProjects;
    }

    public List<ModelType> getModelType() {
        return modelType;
    }

    public void setModelType(List<ModelType> modelType) {
        this.modelType = modelType;
    }

    private List<ModelType> modelType;
}
