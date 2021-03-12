package com.example.e_commerce.otp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.e_commerce.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OTPverificationActivity extends AppCompatActivity {

    private TextView verificationText;
    private EditText otpEditText;
    private Button verifyButton;

    private String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pverification);

        initWidgets();

        mobileNo = getIntent().getStringExtra("mobileNo");

        verificationText.setText("Verification code has been sent to +91 " + mobileNo);

        Random random = new Random();
        final int otp_number = random.nextInt(999999 - 111111) + 111111;

        String SMS_API = "https://www.fast2sms.com/dev/bulk";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_API ,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                verifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String input = otpEditText.getText().toString();

                        if(input.equals(String.valueOf(otp_number))){

                            

                        }

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                finish();
                Toast.makeText(OTPverificationActivity.this, "Failed to send the OTP verification code!", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("authorization", "iRka5lOcy7qFEdJXt18ej2omvsIgpVNQYPAULSMZK4BunhD0Gb5khPlnJE6mBGqHwVs9pdW1NzofOC0t");

                return  headers;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> body = new HashMap<>();
                body.put("sender_id", "FSTSMS");
                body.put("language", "english");
                body.put("route", "qt");
                body.put("numbers", mobileNo);
                body.put("message", "to be done");
                body.put("variables", "{#BB#}");
                body.put("variables_values", String.valueOf(otp_number));

                return  body;

            }
        };

    }

    private void initWidgets(){

        verificationText = findViewById(R.id.verificationText);
        otpEditText = findViewById(R.id.otpEditText);
        verifyButton = findViewById(R.id.verifyOTPButton);

    }
}