package com.historiasdeusuario.Utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;

import java.util.HashMap;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Object>
{
    public enum  Type{ TypeList, ProjectList}
    private Type type;
    private Context context;
    private HashMap<Object, Integer> mIdMap = new HashMap<>();
    private int textSize;

    public CustomArrayAdapter(int textSize, Type type, Context context, int textViewResourceId, List prod) {
        super(context, textViewResourceId, prod);
        this.context = context;
        this.type=type;
        this.textSize=textSize;
        for (int i = 0; i < prod.size(); i++)
        {
            mIdMap.put(prod.get(i), i);
        }
    }

    @NonNull
    public View getView(int i, View view, @NonNull ViewGroup viewgroup)
    {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(textSize);
        Object object = getItem(i);
        String value = arraySwitch(object);
        txt.setText(value);
        txt.setTextColor(Color.parseColor("#3a393b"));
        RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        txt.setLayoutParams(paramsRelative);
        return  txt;
    }

    @Override
    public long getItemId(int position)
    {
        Object item=null;
        try
        {
            if (position < 0 || position >= mIdMap.size()) {
                return -1;
            }
            item = getItem(position);
        }
        catch (Exception ex)
        {
            Log.d("CustomArrayAdapter", ex.getMessage());
        }
        return (item!=null?mIdMap.get(item):0);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private String arraySwitch(Object item)
    {
        switch (type)
        {
            case TypeList:
                ModelType modelType = (ModelType) item;
                return modelType.getTypeName();
            case ProjectList:
                ModelProject modelProject = (ModelProject) item;
                return modelProject.getName();
        }
        return "";
    }
    //Spinner solamente
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(textSize+2);
        txt.setGravity(Gravity.CENTER);
        Object object = getItem(position);
        String value = arraySwitch(object);
        txt.setText(value);
        txt.setTextColor(Color.parseColor("#3a393b"));
        return  txt;
    }
}