package com.historiasdeusuario.navigation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.historiasdeusuario.R;
import com.historiasdeusuario.menu.Menu;

public class NavigationMain extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_main);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new Menu()).commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top).replace(R.id.content_frame, new Menu()).commitAllowingStateLoss();
    }
}
