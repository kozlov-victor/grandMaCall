package ua.victor.grandmacall;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class IntroActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Button button = findViewById(R.id.button);
        button.setOnClickListener((arg)->{
            finish();
        });
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_CONTACTS
        }, 1);
    }
}
