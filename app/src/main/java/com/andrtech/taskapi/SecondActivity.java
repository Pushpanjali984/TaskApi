package com.andrtech.taskapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
EditText edtName,edtPhone,edtCity;
Button btnPost;
RequestQueue requestQueue;

String UrlPost="http://www.kritikaassociates.com/app/addUserData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edtName=findViewById(R.id.enterName);
        edtCity=findViewById(R.id.enterCity);
        edtPhone=findViewById(R.id.enterPhone);
        btnPost=findViewById(R.id.btnPost);
        requestQueue = Volley.newRequestQueue(this);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
                finish();
            }
        });

    }


    public void postData(){

        String name=edtName.getText().toString();
        String phone=edtPhone.getText().toString();
        String city=edtCity.getText().toString();

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("name", name);
        params.put("phone", phone);
        params.put("city", city);


        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, UrlPost, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
           Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest1);




    }

    public void refreshActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    public void onBackPressed() {
        refreshActivity();
        super.onBackPressed();
    }
}

