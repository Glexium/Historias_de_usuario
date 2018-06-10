package com.historiasdeusuario.crudProject;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.historiasdeusuario.database.helper.DatabaseHelper;
import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.objects.ObjResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CrudProjectInteractorImpl extends Fragment implements CrudProjectInteractor
{
    private Subscription addProjectSubscription, deleteProjectSubscription, updateProjectSubscription, projectsSubscription, typesSubscription, typeNameSubscription;

    @Override
    public void addProject(ModelProject modelProject, OnCrudListener listener, Context mContext) {
        {
            addProjectSubscription = addNewProject(modelProject, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                    if (objResponse != null) {
                        if (objResponse.getIdResponse() > 0) {
                            listener.onMessage("Proyecto agregado", 7000);
                            listener.onSuccess();
                        }
                        else
                        {
                            listener.onMessage("Ocurrió un error al agregar el proyecto", 7000);
                        }
                    }
                    },
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
        }
    }

    @Override
    public void deleteProject(ModelProject modelProject, OnCrudListener listener, Context mContext) {
        deleteProjectSubscription = deleteProject(modelProject, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                            if (objResponse != null) {
                                if (objResponse.getIdResponse() > 0) {
                                    listener.onMessage("Proyecto eliminado", 7000);
                                    listener.onSuccess();
                                }
                                else
                                {
                                    listener.onMessage("Ocurrió un error al eliminar el proyecto", 7000);
                                }
                            }
                        },
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    @Override
    public void updateProject(ModelProject modelProject, OnCrudListener listener, Context mContext)
    {
        updateProjectSubscription = updateProject(modelProject, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                            if (objResponse != null) {
                                if (objResponse.getIdResponse() > 0) {
                                    listener.onMessage("Proyecto actualizado", 7000);
                                    listener.onSuccess();
                                }
                                else
                                {
                                    listener.onMessage("Ocurrió un error al actualizar el proyecto", 7000);
                                }
                            }
                        },
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    @Override
    public void getProjectList(String userId, OnGetProjectsListener listener, Context mContext) {
        projectsSubscription  = projectList(userId, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                            if (objResponse != null) {
                                if (objResponse.getIdResponse() > 0) {
                                    listener.onGotProjects(objResponse.getModelProjects());
                                }
                                else
                                {
                                    listener.onMessage("Proyectos no encontrados", 7000);
                                }
                            }
                        },
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    @Override
    public void getTypeList(OnGetTypeListener listener, Context mContext)
    {
        typesSubscription = typeList(mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(objResponse -> {
                            if (objResponse != null) {
                                if (objResponse.getIdResponse() > 0) {
                                    listener.onGotTypes(objResponse.getModelType());
                                }
                                else
                                {
                                    listener.onMessage("No hay proyectos", 7000);
                                }
                            }
                        },
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    @Override
    public void getTypeName(int id, OnGetTypeNameListener listener, Context mContext) {
        typeNameSubscription = typeName(id, mContext).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSetType,
                        error ->
                                listener.onMessage("Ocurrió un error de acceso.", 5000));
    }

    private Observable<ObjResponse> addNewProject(ModelProject modelProject, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).addProject(modelProject));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    private Observable<ObjResponse> deleteProject(ModelProject modelProject, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).deleteProject(modelProject));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    private Observable<ObjResponse> updateProject(ModelProject modelProject, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).updateProject(modelProject));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    private Observable<ObjResponse> projectList(String userId, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).getProjects(userId));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    private Observable<ObjResponse> typeList(Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).getTypeList());
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    private Observable<String> typeName(int id, Context mContext)
    {
        return Observable.unsafeCreate(subscriber ->
        {
            try
            {
                subscriber.onNext(new DatabaseHelper(mContext).getType(id));
                subscriber.onCompleted();
            }
            catch(Exception ex)
            {
                subscriber.onError(ex);
            }
        });
    }

    @Override
    public void onDestroy()
    {
        if(addProjectSubscription!=null) addProjectSubscription.unsubscribe();
        if(deleteProjectSubscription!=null) deleteProjectSubscription.unsubscribe();
        if(updateProjectSubscription!=null) updateProjectSubscription.unsubscribe();
        if(projectsSubscription!=null) projectsSubscription.unsubscribe();
        if(typesSubscription!=null) typesSubscription.unsubscribe();
        if(typeNameSubscription!=null) typeNameSubscription.unsubscribe();
        super.onDestroy();
    }

}
