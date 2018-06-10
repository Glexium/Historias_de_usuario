package com.historiasdeusuario.database.model;

public class ModelType
{
    private int id;
    private String name="";

    public ModelType(){}

    public ModelType(String name)
    {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return name;
    }

    public void setTypeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
