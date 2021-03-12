package com.example.e_commerce.login_and_register;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity {

    private FrameLayout loginFrameLayout;
    public static boolean setSignUpFragment=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFrameLayout=(FrameLayout)findViewById(R.id.loginFrameLayout);
        if(setSignUpFragment){
            setSignUpFragment=false;
            setFragment(new SignupFragment());
        }else {
            setFragment(new SigninFragment());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            SignupFragment.disableCloseButton = false;
            SigninFragment.disableCloseButton = false;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(loginFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}
