package com.historiasdeusuario.crudProject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
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

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UpdateProject extends Fragment implements CrudProjectView
{
    @BindView(R.id.acType) AutoCompleteTextView acType;
    @BindView(R.id.acProjectName) AutoCompleteTextView acProjectName;
    @BindView(R.id.etStartDate) EditText etStartDate;
    @BindView(R.id.etEndDate) EditText etEndDate;
    @BindView(R.id.btnEditProject) RelativeLayout btnEditProject;
    private Unbinder unbinder;
    private ProgressDialog dialog;
    private Boolean busy=false;
    private Context mContext;
    private CrudProjectPresenter crudProjectPresenter;
    private ModelProject modelProject = new ModelProject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.section_update_project, container, false);
        mContext = rootView.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        crudProjectPresenter = new CrudProjectPresenterImpl(this);
        ModelUser modelUser = SingletonUser.getInstance().getModelUser();
        Style.applyTypeface((ViewGroup)rootView, mContext);
        FragmentManager fm = getChildFragmentManager();
        crudProjectPresenter.getTypeList(mContext);
        crudProjectPresenter.getProjectList(String.valueOf(modelUser.getId()), mContext);
        etStartDate.setOnClickListener(arg0 -> {
            final Calendar c = Calendar.getInstance();
            int mYear, mMonth, mDay;
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH) + 1;
            mDay = c.get(Calendar.DAY_OF_MONTH);
            Calendar endTime = Calendar.getInstance();
            endTime.set(Calendar.MONTH, mMonth + 2);

            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setPreselectedDate(mYear, mMonth - 1, mDay)
                    .setDoneText("Aceptar").setThemeLight()
                    .setCancelText("Cancelar").setThemeCustom(R.style.MyCustomBetterPickersDialogs);
            cdp.setOnDateSetListener((dialog1, year, monthOfYear, dayOfMonth) -> {
                String month = ((monthOfYear) < 9 ? ("0" + (monthOfYear + 1)) : String.valueOf(monthOfYear + 1));
                String day = ((dayOfMonth) < 10 ? ("0" + (dayOfMonth)) : String.valueOf(dayOfMonth));
                String formatedDate = day + "/" + month + "/" + year;
                etStartDate.setText(formatedDate);
                modelProject.setStartDate(formatedDate);
            });
            cdp.show(fm, "Fecha Cita");
        });

        etEndDate.setOnClickListener(arg0 -> {
            final Calendar c = Calendar.getInstance();
            int mYear, mMonth, mDay;
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH) + 1;
            mDay = c.get(Calendar.DAY_OF_MONTH);
            Calendar endTime = Calendar.getInstance();
            endTime.set(Calendar.MONTH, mMonth + 2);

            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setPreselectedDate(mYear, mMonth - 1, mDay)
                    .setDoneText("Aceptar").setThemeLight()
                    .setCancelText("Cancelar").setThemeCustom(R.style.MyCustomBetterPickersDialogs);
            cdp.setOnDateSetListener((dialog1, year, monthOfYear, dayOfMonth) -> {
                String month = ((monthOfYear) < 9 ? ("0" + (monthOfYear + 1)) : String.valueOf(monthOfYear + 1));
                String day = ((dayOfMonth) < 10 ? ("0" + (dayOfMonth)) : String.valueOf(dayOfMonth));
                String formatedDate = day + "/" + month + "/" + year;
                etEndDate.setText(formatedDate);
                modelProject.setFinishDate(formatedDate);
            });
            cdp.show(fm, "Fecha Cita");
        });

        btnEditProject.setOnClickListener(view ->
        {
            modelProject.setUserId(modelUser.getId());
            modelProject.setDeleted(false);
            Boolean res = ValidationUtil.stringValidation(modelProject.getStartDate(), modelProject.getFinishDate(), modelProject.getName());
            if (res)
            {
                crudProjectPresenter.updateProject(modelProject, mContext);
            } else {
                new Messages().Dialog("Aviso", "Datos incompletos", mContext);
            }
        });
        reSize();
        return rootView;
    }

    private void reSize()
    {
        Double y = ((double) LayoutSize.GetSize((Activity) mContext, LayoutSize.Type.Height, 1.0));
        Double btnHeight = y * 0.07;
        btnEditProject.getLayoutParams().height = btnHeight.intValue();
    }

    private void setTypes(List<ModelType> modelTypeList)
    {
        int textSize= LayoutSize.getIdealTextSize(11, mContext); //
        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(textSize, CustomArrayAdapter.Type.TypeList, mContext, acType.getId(), modelTypeList);
        acType.setAdapter(customArrayAdapter);
        acType.setOnItemClickListener((parent, view, position, id) -> {
            try
            {
                int typeId = modelTypeList.get(position).getId();
                modelProject.setTypeId(typeId);
            }
            catch(NullPointerException ex)
            {
                Log.e("Error", ex.getMessage());
            }
        });
        acType.setOnClickListener(arg0 -> acType.showDropDown());
    }

    private void setProjects(List<ModelProject> modelProjectList)
    {
        int textSize= LayoutSize.getIdealTextSize(11, mContext); //
        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(textSize, CustomArrayAdapter.Type.ProjectList, mContext, acProjectName.getId(), modelProjectList);
        acProjectName.setAdapter(customArrayAdapter);
        acProjectName.setOnItemClickListener((parent, view, position, id) -> {
            try
            {
                String name = modelProjectList.get(position).getName();
                modelProject.setName(name);
                modelProject.setStartDate(modelProjectList.get(position).getStartDate());
                modelProject.setFinishDate(modelProjectList.get(position).getFinishDate());
                modelProject.setName(modelProjectList.get(position).getName());
                modelProject.setTypeId(modelProjectList.get(position).getTypeId());
                modelProject.setDeleted(modelProjectList.get(position).getDeleted());
                modelProject.setId(modelProjectList.get(position).getId());
                etStartDate.setText(modelProjectList.get(position).getStartDate());
                etEndDate.setText(modelProjectList.get(position).getFinishDate());
                crudProjectPresenter.getTypeName(modelProjectList.get(position).getTypeId(), mContext);
            }
            catch(NullPointerException ex)
            {
                Log.e("Error", ex.getMessage());
            }
        });
        acProjectName.setOnClickListener(arg0 -> acProjectName.showDropDown());
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
    public void clean() { }

    @Override
    public void setProjectList(List<ModelProject> modelProjects)
    {
        setProjects(modelProjects);
    }

    @Override
    public void setTypeList(List<ModelType> modelTypes)
    {
        if(modelTypes!=null)
            setTypes(modelTypes);
        else
        {
            new Messages().SystemToast("No hay registros", 7000, mContext);
        }
    }

    @Override
    public void setTypeName(String name) {
        acType.setText(name);
    }

    @Override
    public void onDestroy()
    {
        if(unbinder!=null) unbinder.unbind();
        super.onDestroy();
    }
}