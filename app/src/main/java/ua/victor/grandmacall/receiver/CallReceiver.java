package ua.victor.grandmacall.receiver;

import android.content.Context;

import ua.victor.grandmacall.MainActivity;
import ua.victor.grandmacall.receiver.abstracts.AbstractPhoneCallReceiver;

public class CallReceiver extends AbstractPhoneCallReceiver {

    @Override
    protected void onRinging(Context context, String phoneNumber) {
        System.out.println("---------------------------------ringing---------------------------------");
//        Intent intent = new Intent();
//        intent.setClass(context, MainActivity.class);
//        context.startActivity(intent);
    }

    @Override
    protected void onStarted(Context context, String number) {
//        Intent intent = new Intent();
//        intent.setClass(context, MainActivity.class);
//        context.startActivity(intent);
    }

    @Override
    protected void onEnded(Context context, String number) {
        if (MainActivity.getInstance()!=null) MainActivity.getInstance().finish();
    }

    @Override
    protected void onMissed(Context context, String number) {
        if (MainActivity.getInstance()!=null) MainActivity.getInstance().finish();
    }

}