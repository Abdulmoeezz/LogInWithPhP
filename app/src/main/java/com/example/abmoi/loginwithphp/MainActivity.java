package com.example.abmoi.loginwithphp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {
EditText etemail ,etpassword;
TextView tvRegister;
Button BTnLogin;

final  String Url_login="https://abdulmoiz942.000webhostapp.com/Login_User.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         etemail =(EditText) findViewById(R.id.editText1);
        etpassword=(EditText) findViewById(R.id.editText2);
        BTnLogin=(Button) findViewById(R.id.BTN1);
        tvRegister=(TextView)findViewById(R.id.Tv_Register);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Newi=new Intent(MainActivity.this,Register_Activity.class);
                startActivity(Newi);
            }
        });

        BTnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email=etemail.getText().toString();
                String Password=etpassword.getText().toString();


                new LoginUser().execute(Email,Password);


            }
        });

        }

        public   class LoginUser extends AsyncTask<String,Void,String>{


            @Override
            protected String doInBackground(String... strings) {

               String Email=strings[0];
               String Password=strings[1];


                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody formbody=new FormBody.Builder()
                        .add("user_id",Email)
                        .add("user_password",Password)
                        .build();

                Request request= new Request.Builder()
                        .url(Url_login)
                        .post(formbody)
                        .build();


                Response response=null;

               try{
                   response=okHttpClient.newCall(request).execute();
                   if (response.isSuccessful()){
                       String result =response.body().string();
                       if (result.equalsIgnoreCase("logiin")){
                           Intent newI = new Intent(MainActivity.this,DashboardActivity.class);
                           startActivity(newI);
                           finish();


                       }else {
                           showToast("Email or Password mismatched!"); }


                   }


               }
               catch (Exception ex){
                   ex.printStackTrace();
               }




                return null;
            }
        }


    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
