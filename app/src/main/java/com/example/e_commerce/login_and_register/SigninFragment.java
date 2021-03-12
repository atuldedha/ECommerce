package com.example.e_commerce.login_and_register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_commerce.login_and_register.forget_password.ForgetPasswordFragment;
import com.example.e_commerce.R;
import com.example.e_commerce.main_screen.StarterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    private TextView dontHaveAccount;
    private FrameLayout loginFrameLayout;

    private EditText sign_In_EmailEditText;
    private EditText sign_In_PasswordEditText;

    private Button signinButton;
    private ImageButton sign_In_CancelImageButton;

    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;

    private TextView forgotTextView;

    public static boolean disableCloseButton = false;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signin, container, false);

        loginFrameLayout=getActivity().findViewById(R.id.loginFrameLayout);

        dontHaveAccount=view.findViewById(R.id.signUpTextView);

        sign_In_EmailEditText=view.findViewById(R.id.sign_In_EmailEditText);
        sign_In_PasswordEditText=view.findViewById(R.id.sign_In_PasswordEditText);

        sign_In_CancelImageButton=view.findViewById(R.id.sign_In_CancelImageButton);

        signinButton=view.findViewById(R.id.signInButton);

        firebaseAuth=FirebaseAuth.getInstance();

        progressBar=view.findViewById(R.id.sign_In_ProgressBar);

        forgotTextView=view.findViewById(R.id.forgotTextView);

        if(disableCloseButton){
            sign_In_CancelImageButton.setVisibility(View.GONE);
        }else{
            sign_In_CancelImageButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignupFragment());
            }
        });

        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgetPasswordFragment());
            }
        });

        sign_In_EmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign_In_PasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(isEmailValid(sign_In_EmailEditText.getText().toString())){

                    if(sign_In_PasswordEditText.length()>=8){


                        progressBar.setVisibility(view.VISIBLE);

                        signinButton.setEnabled(false);
                        signinButton.setTextColor(Color.argb(50,255,255,255));

                        firebaseAuth.signInWithEmailAndPassword(sign_In_EmailEditText.getText().toString(),
                                sign_In_PasswordEditText.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){

                                            Log.d("Login","Successful");

                                            redirect();
                                        }else{

                                            signinButton.setEnabled(true);
                                            signinButton.setTextColor(Color.rgb(255,255,255));
                                            progressBar.setVisibility(view.INVISIBLE);
                                            String error=task.getException().getMessage();
                                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();


                                        }

                                    }
                                });

                    }else{

                        Toast.makeText(getActivity(), "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(getActivity(), "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();


                }

            }
        });

        sign_In_CancelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
            }
        });


    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.sliding2,R.anim.sliding3);
        fragmentTransaction.replace(loginFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkInput(){

        if(!TextUtils.isEmpty(sign_In_EmailEditText.getText())){
            if(!TextUtils.isEmpty(sign_In_PasswordEditText.getText())){

                signinButton.setEnabled(true);
                signinButton.setTextColor(Color.rgb(255,255,255));

            }else{

                signinButton.setEnabled(false);
                signinButton.setTextColor(Color.argb(50,255,255,255));

            }
        }else{

            signinButton.setEnabled(false);
            signinButton.setTextColor(Color.argb(50,255,255,255));

        }

    }

    public void redirect(){
        if(disableCloseButton){

            disableCloseButton = false;

        }else{

            Intent intent=new Intent(getActivity(), StarterActivity.class);
            startActivity(intent);

        }
        getActivity().finish();
    }

}
