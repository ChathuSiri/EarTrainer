package com.example.chathura.eartrainer;
        import android.content.Intent;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;


public class UserRegister extends ActionBarActivity {

    DataAccess helper = new DataAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_register, menu);
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

    public void onRegisterButtonClicked(View v){
        if(v.getId()==R.id.RegisterButton){
            EditText userName = (EditText)findViewById(R.id.UsernameEditText);
            EditText password = (EditText)findViewById(R.id.PasswordEditText);
            EditText confirmPassword = (EditText)findViewById(R.id.ConfirmEditText);

            String user_name = userName.getText().toString();
            String pass = password.getText().toString();
            String confirm = confirmPassword.getText().toString();

            if(user_name.isEmpty() || pass.isEmpty()){
                Toast toast = Toast.makeText(UserRegister.this, "A field is empty!",Toast.LENGTH_SHORT );
                toast.show();

            }
            else if(!pass.equals(confirm)){
                Toast notMatch =  Toast.makeText(UserRegister.this,"Password and Confirm password doesn't match!",Toast.LENGTH_SHORT);
                notMatch.show();
            }
            else{
                User user = new User();
                Intent i = new Intent(UserRegister.this,Home.class);
                user.setUserName(user_name);
                user.setPassword(pass);
                Toast toast =Toast.makeText(UserRegister.this, user.getUserName() +" and "+user.getPassword(),Toast.LENGTH_SHORT);
                toast.show();
                helper.insertUser(user);
                i.putExtra("user",user_name);
                startActivity(i);
                this.finish();

            }


        }


    }

    public void onBackButtonClicked(View v){
        if(v.getId()==R.id.BackButton){
            Intent i = new Intent(UserRegister.this,LoginActivity.class);
            startActivity(i);
        }
    }

}
