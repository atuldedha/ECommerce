package com.example.e_commerce.login_and_register.forget_password;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.login_and_register.SigninFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment {

    private EditText forget_PasswordEditText;

    private Button resetPasswordButton;

    private ImageView recoveryImageView;

    private TextView recoveryTextView;

    private ProgressBar recoveryProgressBar;

    private TextView backButton;

    private FrameLayout loginFrameLayout;

    private FirebaseAuth firebaseAuth;

    private ViewGroup linearLayout;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password, container, false);

        forget_PasswordEditText=view.findViewById(R.id.forgot_PasswordEditText);

        resetPasswordButton =view.findViewById(R.id.resetPasswordButton);

        recoveryImageView=view.findViewById(R.id.recoveryImageView);

        recoveryTextView=view.findViewById(R.id.recoveryTextView);

        recoveryProgressBar=view.findViewById(R.id.recoveryProgressBar);

        backButton=view.findViewById(R.id.backButton);

        loginFrameLayout=getActivity().findViewById(R.id.loginFrameLayout);

        linearLayout=view.findViewById(R.id.linearLayout);

        firebaseAuth=FirebaseAuth.getInstance();



        return view;


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forget_PasswordEditText.addTextChangedListener(new TextWatcher() {
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

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(linearLayout);
                recoveryTextView.setVisibility(view.GONE);

                TransitionManager.beginDelayedTransition(linearLayout);
                recoveryImageView.setVisibility(view.VISIBLE);
                recoveryProgressBar.setVisibility(view.VISIBLE);

                resetPasswordButton.setEnabled(false);
                resetPasswordButton.setTextColor(Color.argb(70,255,255,255));

                if(isEmailValid(forget_PasswordEditText.getText().toString())){

                    firebaseAuth.sendPasswordResetEmail(forget_PasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                ScaleAnimation scaleAnimation=new ScaleAnimation(1,0,1,0,recoveryImageView.getWidth()/2,recoveryImageView.getHeight()/2);
                                scaleAnimation.setDuration(100);
                                scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                scaleAnimation.setRepeatMode(Animation.REVERSE);
                                scaleAnimation.setRepeatCount(1);
                                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        recoveryTextView.setText("E-mail has been sent Successfullt! Check your Inbox.");
                                        recoveryTextView.setTextColor(Color.rgb(0,170,0));
                                        TransitionManager.beginDelayedTransition(linearLayout);
                                        recoveryTextView.setVisibility(view.VISIBLE);

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                        recoveryImageView.setImageResource(R.drawable.mail);

                                    }
                                });

                                recoveryImageView.startAnimation(scaleAnimation);

                                //Toast.makeText(getActivity(), "E-mail sent successfully", Toast.LENGTH_SHORT).show();

                            }else{

                                resetPasswordButton.setEnabled(true);
                                resetPasswordButton.setTextColor(Color.rgb(255,255,255));

                                String error=task.getException().getMessage();
                                recoveryProgressBar.setVisibility(view.GONE);
                                TransitionManager.beginDelayedTransition(linearLayout);
                                recoveryTextView.setText(error);
                                recoveryTextView.setTextColor(Color.rgb(170,0,0));
                                recoveryTextView.setVisibility(view.VISIBLE);
                                //Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                            }

                            recoveryProgressBar.setVisibility(view.GONE);



                        }
                    });


                }else{

                    Toast.makeText(getActivity(), "E-mail is not valid", Toast.LENGTH_SHORT).show();

                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });


    }

    private void checkInput(){

        if(!TextUtils.isEmpty(forget_PasswordEditText.getText())){

            resetPasswordButton.setEnabled(true);
            resetPasswordButton.setTextColor(Color.rgb(255,255,255));

        }else{

            resetPasswordButton.setEnabled(false);
            resetPasswordButton.setTextColor(Color.argb(70,255,255,255));

        }

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.sliding,R.anim.sliding4);
        fragmentTransaction.replace(loginFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
