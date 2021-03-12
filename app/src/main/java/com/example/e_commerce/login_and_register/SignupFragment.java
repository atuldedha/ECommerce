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

import com.example.e_commerce.R;
import com.example.e_commerce.main_screen.StarterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private TextView haveAnAccount;
    private FrameLayout loginFrameLayout;
    private EditText sign_Up_EmailEditText;
    private EditText sign_Up_NameEditText;
    private EditText sign_Up_PasswordEditText;
    private EditText sign_Up_ConfirmPasswordEditText;

    private ImageButton sign_Up_CancelImageButton;

    private Button signupButton;

    private ProgressBar sign_Up_ProgressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public static boolean disableCloseButton = false;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        loginFrameLayout=getActivity().findViewById(R.id.loginFrameLayout);
        haveAnAccount=view.findViewById(R.id.signInTextView);

        sign_Up_EmailEditText=view.findViewById(R.id.sign_UP_EmailEditText);
        sign_Up_NameEditText=view.findViewById(R.id.sign_Up_NameEditText);
        sign_Up_PasswordEditText=view.findViewById(R.id.sign_Up_PasswordEditText);
        sign_Up_ConfirmPasswordEditText=view.findViewById(R.id.sign_Up_ConfirmPasswordEditText);

        sign_Up_CancelImageButton=view.findViewById(R.id.cancelImageButton);

        signupButton=view.findViewById(R.id.signupButton);

        sign_Up_ProgressBar=view.findViewById(R.id.sign_Up_ProgressBar);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(disableCloseButton) {
            sign_Up_CancelImageButton.setVisibility(View.GONE);
        }else{
            sign_Up_CancelImageButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });

        sign_Up_EmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign_Up_NameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign_Up_PasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign_Up_ConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEmailValid(sign_Up_EmailEditText.getText().toString())){

                    if(sign_Up_PasswordEditText.getText().toString().equals(sign_Up_ConfirmPasswordEditText.getText().toString())){

                        sign_Up_ProgressBar.setVisibility(View.VISIBLE);
                        signupButton.setEnabled(false);
                        signupButton.setTextColor(Color.argb(50,255,255,255));

                        firebaseAuth.createUserWithEmailAndPassword(sign_Up_EmailEditText.getText().toString(),
                                sign_Up_ConfirmPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d("Login","Successful");

                                    Map<String , Object> user=new HashMap<>();
                                    user.put("Name",sign_Up_NameEditText.getText().toString());
                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                CollectionReference userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                HashMap<String , Object> wishlistMap = new HashMap<>();
                                                wishlistMap.put("list_size", (long) 0);

                                                HashMap<String , Object> ratingsMap = new HashMap<>();
                                                ratingsMap.put("list_size", (long) 0);

                                                HashMap<String , Object> cartsMap = new HashMap<>();
                                                cartsMap.put("list_size", (long) 0);

                                                HashMap<String , Object> myAddressesMap = new HashMap<>();
                                                myAddressesMap.put("list_size", (long) 0);

                                                final List<String> documentNames = new ArrayList<>();
                                                documentNames.add("MY_WISHLIST");
                                                documentNames.add("MY_RATINGS");
                                                documentNames.add("MY_CARTLIST");
                                                documentNames.add("MY_ADDRESSES");

                                                List<HashMap<String , Object>> documentFields = new ArrayList<>();
                                                documentFields.add(wishlistMap);
                                                documentFields.add(ratingsMap);
                                                documentFields.add(cartsMap);
                                                documentFields.add(myAddressesMap);

                                                for (int i=0; i<documentNames.size(); i++){

                                                    final int finalI = i;

                                                    userDataReference.document(documentNames.get(i))
                                                            .set(documentFields.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){

                                                                if(finalI == documentNames.size()-1) {
                                                                    redirect();
                                                                }
                                                            }else{

                                                                sign_Up_ProgressBar.setVisibility(View.INVISIBLE);
                                                                signupButton.setEnabled(true);
                                                                signupButton.setTextColor(Color.rgb(255,255,255));
                                                                String error=task.getException().getMessage();
                                                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                            }

                                                        }
                                                    });

                                                }

                                            }else{

                                                String error=task.getException().getMessage();
                                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                }else{
                                    sign_Up_ProgressBar.setVisibility(View.INVISIBLE);
                                    signupButton.setEnabled(true);
                                    signupButton.setTextColor(Color.rgb(255,255,255));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }else{

                        sign_Up_ConfirmPasswordEditText.setError("Password does not match");

                    }

                }else{

                    sign_Up_EmailEditText.setError("Email is Invalid");

                }

            }
        });

        sign_Up_CancelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
            }
        });



    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.sliding,R.anim.sliding4);
        fragmentTransaction.replace(loginFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    public void checkInputs(){

        if(!TextUtils.isEmpty(sign_Up_EmailEditText.getText())){

            if(!TextUtils.isEmpty(sign_Up_NameEditText.getText())){

                if(!TextUtils.isEmpty(sign_Up_PasswordEditText.getText()) && sign_Up_PasswordEditText.length()>=8){

                    if(!TextUtils.isEmpty(sign_Up_ConfirmPasswordEditText.getText()) && sign_Up_ConfirmPasswordEditText.length()>=8){

                        signupButton.setEnabled(true);
                        signupButton.setTextColor(Color.rgb(255,255,255));

                    }else{

                        signupButton.setEnabled(false);
                        signupButton.setTextColor(Color.argb(50,255,255,255));
                    }

                }else{

                    signupButton.setEnabled(false);
                    signupButton.setTextColor(Color.argb(50,255,255,255));

                }

            }else{

                signupButton.setEnabled(false);
                signupButton.setTextColor(Color.argb(50,255,255,255));

            }

        }else{

            signupButton.setEnabled(false);
            signupButton.setTextColor(Color.argb(50,255,255,255));

        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
