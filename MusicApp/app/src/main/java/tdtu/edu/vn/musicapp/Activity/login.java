package tdtu.edu.vn.musicapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import tdtu.edu.vn.musicapp.Model.Endpoint;
import tdtu.edu.vn.musicapp.Model.VolleySingleton;
import tdtu.edu.vn.musicapp.R;

public class login extends AppCompatActivity {
    static String stn;
    EditText eusername, epassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eusername = findViewById(R.id.username);
        epassword = findViewById(R.id.password);


    }
    public void signup(View view){
        Intent intent = new Intent(login.this, Register.class);
        startActivity(intent);
    }

    public void login(View view){
        String name = eusername.getText().toString();
        String password = epassword.getText().toString();
        if(isvalid(name, password)){
            signIn(name, password);
        }
    }

    private void signIn(final String name, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoint.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stn = eusername.getText().toString();
                if (response.equals("success")) {
                    Intent intent = new Intent(login.this, Homepage.class);
                    intent.putExtra("username", stn);
                    startActivity(intent);
                    eusername.setText(null);
                    epassword.setText(null);
                }
                if (response.equals("INVALID")) {
                    showMessage("Invalid usrename or password");
                    Intent intent = new Intent(login.this, login.class);
                    startActivity(intent);
                    eusername.setText(null);
                    epassword.setText(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessage("Please check your internet connection");
                Intent intent = new Intent(login.this, login.class);
                startActivity(intent);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequest(stringRequest);

    }

    private boolean isvalid(String name, String password) {
        if(name.isEmpty()){
            showMessage("Please enter username");
            eusername.setText(null);
            return false;
        }
        if(password.isEmpty()){
            showMessage("Please enter password");
            epassword.setText(null);
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}