package ua.victor.grandmacall.service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import ua.victor.grandmacall.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AppCallService extends InCallService {

    public static Call currentCall;

    public static final String EXTRA_STATE = "app.extra.state";
    public static final String EXTRA_NUMBER = "app.extra.number";



    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        currentCall = call;
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        String phone = call.getDetails().getHandle().getSchemeSpecificPart();
        intent.putExtra(EXTRA_STATE,call.getState());
        intent.putExtra(EXTRA_NUMBER,phone);
        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);
        if (MainActivity.getInstance()!=null) MainActivity.getInstance().finish();
    }
}
