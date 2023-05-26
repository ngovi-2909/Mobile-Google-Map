package tdtu.edu.vn.musicapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import tdtu.edu.vn.musicapp.Activity.login;
import tdtu.edu.vn.musicapp.Model.Endpoint;
import tdtu.edu.vn.musicapp.Model.VolleySingleton;
import tdtu.edu.vn.musicapp.R;

public class Register extends AppCompatActivity {

    EditText eusername, epassword, eemail, ecofirmpassword;
    TextView signIn;
    Button btnlogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eusername = findViewById(R.id.username);
        epassword = findViewById(R.id.password);
        eemail = findViewById(R.id.email);
        ecofirmpassword = findViewById(R.id.confirmpassword);
        btnlogin = findViewById(R.id.btnlogin);
        signIn = findViewById(R.id.google);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eusername.getText().toString();
                String email = eemail.getText().toString();
                String password = epassword.getText().toString();
                String confirmpassword = ecofirmpassword.getText().toString();
                System.out.println(name);
                if(isvalid(name, email, password, confirmpassword)){
                    registerUser(name, email, password, confirmpassword);
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin(view);
            }
        });
    }

    private void registerUser(final String name, final String email, final String password, final String confirmpassword) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoint.register, response -> {
            if(response.equals("success")){
                showMessage("Registration Successfully");
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
            }else{
                showMessage("Username already exists");
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
            }
        }, error -> {
            showMessage("Please check your internet connection");
            Intent intent = new Intent(Register.this, Register.class);
            startActivity(intent);
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequest(stringRequest);
    }

    private boolean isvalid(String name, String email, String password, String confirmpassword) {
        if(name.isEmpty()){
            showMessage("Please enter username");
            return false;
        }
        if(email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if(!password.equals(confirmpassword)){
            showMessage("Password is not match");
            return false;
        }
        if(password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }
        if(confirmpassword.isEmpty()){
            showMessage("Please enter password");
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void signin(View view){
        Intent intent = new Intent(Register.this, login.class);
        startActivity(intent);
    }
}