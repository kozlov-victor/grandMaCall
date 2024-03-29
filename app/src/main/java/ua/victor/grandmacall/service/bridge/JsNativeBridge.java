package ua.victor.grandmacall.service.bridge;

import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsNativeBridge {


    public static abstract class ClientCommandCallLIstener {
         @SuppressWarnings("unused") // <- is used by bridge
         public abstract void callHostCommand(String commandName, String eventId, @Nullable String jsonParams);
    }

    public static void sendToWebClient(WebView webView, String eventId, Object payload) {
        Map<String,Object> toSerialize = new HashMap<>();
        toSerialize.put("eventId",eventId);
        toSerialize.put("payload",payload);
        String json = "null";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(toSerialize);
            toSerialize.put("payload",json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView.evaluateJavascript(String.format("window.__cb__ && window.__cb__(%s)",json), new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }

}
