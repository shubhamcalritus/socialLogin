package shubham.jain.space.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import shubham.jain.space.splashlib.My_Splash;
import shubham.jain.space.splashlib.TimeExecuteListener;

public class MainActivity extends AppCompatActivity implements TimeExecuteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        My_Splash my_splash = new My_Splash(this);
        setContentView(my_splash);
        my_splash.setImageInImageView(R.drawable.im);
        my_splash.setTimeExecuteListener(this,5000);
    }

    @Override
    public void onExecute() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
