package com.historiasdeusuario.crudProject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.historiasdeusuario.R;
import com.historiasdeusuario.Utils.LayoutSize;
import com.historiasdeusuario.Utils.Messages;
import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.singleton.SingletonUser;

import java.util.List;

public class ViewProject extends Fragment implements CrudProjectView
{

    private View root;
    private Context mContext;
    private CrudProjectPresenter presenter;
    private ProgressDialog dialog;
    private Boolean busy = false;
    private LinearLayout llMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.section_user_projects, container, false);
        mContext = root.getContext();
        llMain = root.findViewById(R.id.llMain);
        presenter = new CrudProjectPresenterImpl(this);
        ModelUser modelUser = SingletonUser.getInstance().getModelUser();
        presenter.getProjectList(String.valueOf(modelUser.getId()), mContext);
        return root;
    }

    @Override
    public void showProgress()
    {
        if(!busy)
        {
            busy=true;
            dialog = new Messages().OpenDialog(mContext);
        }
    }

    @Override
    public void hideProgress()
    {
        busy=false;
        new Messages().CloseDialog(dialog);
    }

    @Override
    public void setMessage(String message, int duration) {
        new Messages().SystemToast(message, duration, mContext);
    }

    @Override
    public void clean() {

    }

    @Override
    public void setProjectList(List<ModelProject> modelProjects) {
        Create(modelProjects);
    }

    @Override
    public void setTypeList(List<ModelType> modelTypes) {

    }

    @Override
    public void setTypeName(String name) {

    }

    private void Create(List<ModelProject> modelProjects)
    {
        if(mContext!=null)
        {
            int index =1;
            for (ModelProject objMovimiento : modelProjects) {
                llMain.addView(makeHeader("Proyecto No." + index));
                llMain.addView(makeView());
                llMain.addView(makeTable(objMovimiento));
                llMain.addView(makeView());
                index++;
            }
        }
    }

    private LinearLayout makeTable(ModelProject modelProject)
    {
        //
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//w h
        params.setMargins(LayoutSize.convertToDP(15,mContext), LayoutSize.convertToDP(15,mContext), LayoutSize.convertToDP(15,mContext), LayoutSize.convertToDP(15,mContext));
        linearLayout.setLayoutParams(params);
        //
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsRelative.leftMargin=LayoutSize.convertToDP(15,mContext);
        paramsRelative.rightMargin=LayoutSize.convertToDP(15,mContext);
        relativeLayout.setLayoutParams(paramsRelative);
        linearLayout.addView(relativeLayout);
        //
        TableLayout tableLayout = new TableLayout(mContext);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableLayout.setStretchAllColumns(false);
        tableLayout.setLayoutParams(tableParams);
        relativeLayout.addView(tableLayout);
        //
        tableLayout.addView(makeRow("Proyecto: ", modelProject.getName()));
        tableLayout.addView(makeRow("Tipo: ", modelProject.getTypeName()));
        tableLayout.addView(makeRow("Fecha inicial: ", modelProject.getStartDate()));
        tableLayout.addView(makeRow("Fecha final: ", modelProject.getFinishDate()));
        return linearLayout;
    }

    private LinearLayout makeHeader(String head)
    {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//w h
        params.setMargins(LayoutSize.convertToDP(15, mContext), 0, LayoutSize.convertToDP(1, mContext), 0);
        linearLayout.setLayoutParams(params);
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(LayoutSize.convertToDP(15, mContext), 5, LayoutSize.convertToDP(1, mContext), 0);
        textView.setLayoutParams(textParams);
        textView.setTextColor(Color.parseColor("#3b5998"));
        textView.setGravity(Gravity.CENTER);
        textView.setText(head);
        linearLayout.addView(textView);
        return linearLayout;
    }

    private TableRow makeRow(String textColumnA, String textColumnB)
    {
        TableRow tableRowA = new TableRow(mContext);
        TextView textViewA = new TextView(mContext);
        int pd = LayoutSize.convertToDP(3,mContext);
        textViewA.setPadding(pd,pd,pd,pd);
        textViewA.setText(textColumnA);
        textViewA.setTextColor(Color.parseColor("#3a393b"));
        tableRowA.addView(textViewA);
        TextView textViewB = new TextView(mContext);
        textViewB.setPadding(pd,pd,pd,pd);
        textViewB.setText(textColumnB);
        textViewB.setTextColor(Color.parseColor("#3a393b"));
        tableRowA.addView(textViewB);
        return  tableRowA;
    }

    private View makeView()
    {
        View viewA = new View(mContext);
        viewA.setBackgroundColor(Color.parseColor("#9B9B9B"));//"#F6BF00"));
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewParams.leftMargin=LayoutSize.convertToDP(15,mContext);
        viewParams.rightMargin=LayoutSize.convertToDP(15,mContext);
        viewParams.height=LayoutSize.convertToDP(0.9f, mContext);
        viewA.setLayoutParams(viewParams);
        return viewA;
    }

    @Override
    public void onDestroy()
    {
        mContext=null;
        presenter=null;
        dialog=null;
        root=null;
        super.onDestroy();
    }
}