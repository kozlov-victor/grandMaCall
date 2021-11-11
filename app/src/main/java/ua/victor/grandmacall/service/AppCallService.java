package ua.victor.grandmacall.service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;

import androidx.annotation.RequiresApi;

import ua.victor.grandmacall.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AppCallService extends InCallService {

    public static Call currentCall;

    public static final String EXTRA_STATE = "app.extra.state";
    public static final String EXTRA_NUMBER = "app.extra.number";

    public AppCallService() {
        System.out.println("--------------------ctor-------------------------");
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("-----------------------bind-------------------------" + intent);
        return super.onBind(intent);
    }

    @Override
    public void onConnectionEvent(Call call, String event, Bundle extras) {
        System.out.println("-------------------on connection event--- " + event);
        super.onConnectionEvent(call, event, extras);
    }

    @Override
    public void onCallAudioStateChanged(CallAudioState audioState) {
        System.out.println("-------------------onCallAudioStateChanged--------------- " + audioState);
        super.onCallAudioStateChanged(audioState);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("-------------------onUnbind--------------- " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onBringToForeground(boolean showDialpad) {
        System.out.println("-------------------onBringToForeground--------------- " + showDialpad);
        super.onBringToForeground(showDialpad);
    }

    @Override
    public void onCallAdded(Call call) {
        System.out.println("--------------onCallAdded -------------" + call);
        super.onCallAdded(call);
        currentCall = call;
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        intent.putExtra(EXTRA_STATE,call.getState());
        intent.putExtra(EXTRA_NUMBER,call.getDetails().getHandle().getSchemeSpecificPart());
        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onCallRemoved(Call call) {
        System.out.println("--------------onCallRemoved-------------" + call);
        super.onCallRemoved(call);
        if (MainActivity.getInstance()!=null) MainActivity.getInstance().finish();
    }
}
