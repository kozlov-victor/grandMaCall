package ua.victor.grandmacall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;

import ua.victor.grandmacall.model.PhoneCallState;
import ua.victor.grandmacall.model.PhoneCallStateInfo;
import ua.victor.grandmacall.provider.PhoneBookProvider;
import ua.victor.grandmacall.service.AppCallService;
import ua.victor.grandmacall.service.bridge.DeviceListener;

public class MainActivity extends Activity {

    private static MainActivity instance;
    private WebView webView;

    private MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void playRingtone() {
        mediaPlayer = MediaPlayer.create(this, R.raw.piano);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    private void stopRingtone() {
        if (mediaPlayer==null) return;
        mediaPlayer.stop();
    }

    private void turnOnScreen(Context activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock  wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "appname::WakeLock");

        //acquire will turn on the display
        wakeLock.acquire();

        //release will release the lock from CPU, in case of that, screen will go back to sleep mode in defined time bt device settings
        wakeLock.release();
    }

    private String clearNumber(String number) {
        if (number==null) return "";
        return number.trim().
                replace("(", "").replace(")", "").
                replace(" ", "").replace("-", "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        webView = this.findViewById(R.id.main_screen);

        WebChromeClient chromeClient = new MyChromeClient();
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setAppCacheEnabled(true);

        DeviceListener deviceListener = new DeviceListener();
        deviceListener.setUpBridge(this,webView);

        String payload = "";
        if (getIntent()!=null) {
            Intent intent = getIntent();
            PhoneCallStateInfo phoneCallStateInfo = new PhoneCallStateInfo();
            String phoneNumber = intent.getStringExtra(AppCallService.EXTRA_NUMBER);
            phoneNumber = clearNumber(phoneNumber);
            int state = intent.getIntExtra(AppCallService.EXTRA_STATE,-1);
            PhoneCallState phoneCallState = null;
            if (state==2) phoneCallState = PhoneCallState.RINGING;
            else if (state==9) phoneCallState = PhoneCallState.STARTED;

            phoneCallStateInfo.setPhoneCallState(phoneCallState);
            phoneCallStateInfo.setPhoneNumber(phoneNumber);
            phoneCallStateInfo.setAddress(new PhoneBookProvider().getContactNameByPhoneNumber(this,phoneNumber));
            try {
                payload = new ObjectMapper().writeValueAsString(phoneCallStateInfo);
                payload = URLEncoder.encode(payload,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        webView.loadUrl("file:///android_asset/index.html?payload="+payload);

        instance = this;
        turnOnScreen(this);
        playRingtone();
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void finish() {
        stopRingtone();
        super.finish();
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("CONSOLE",url);
            return true;
        }
    }

    private static class MyChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d("CONSOLE",consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }

}
