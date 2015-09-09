package com.example.chathura.eartrainer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chathura.eartrainer.dataaccess.DataAccess;


public class  LoginActivity extends ActionBarActivity {

    private Button button1;
    private TextView textView1;
    private String username;
    DataAccess helper = new DataAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        button1 = (Button)findViewById(R.id.button1);
        textView1 = (TextView)findViewById(R.id.textView1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    public void onLoginButtonClicked(View v){
        if(v.getId()==R.id.loginButton){

            EditText username =(EditText)findViewById(R.id.UsernameEditText);
            EditText password = (EditText)findViewById(R.id.PasswordEditText);

            String user = username.getText().toString();
            String pass = password.getText().toString();

            String passCode = helper.searchPassword(user);//search user from data base
            if(user.isEmpty() || pass.isEmpty()){//checks whether fields are empty
                Toast temp1 = Toast.makeText(LoginActivity.this,"Username or Password field is empty",Toast.LENGTH_SHORT);
                temp1.show();
            }
            else if(pass.equals(passCode)){//if password is correct
                Intent i = new Intent(LoginActivity.this,Home.class);
                i.putExtra("user",user);
                startActivity(i);
            }
            else{//else say password mismatch
                Toast temp =  Toast.makeText(LoginActivity.this,"Username or Password incorrect", Toast.LENGTH_SHORT);
                temp.show();
            }
        }
    }

    public void onRegisterButtonClicked(View v){

        if(v.getId()==R.id.RegisterButton){

            Intent i = new Intent(LoginActivity.this,UserRegister.class);
            startActivity(i);
            this.finish();

        }
    }


    public void onClickedbtn1(View view) {

        textView1.setText("Hey How Are You? :)");
        Toast.makeText(this,"Hello There!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            return;
        }
}
