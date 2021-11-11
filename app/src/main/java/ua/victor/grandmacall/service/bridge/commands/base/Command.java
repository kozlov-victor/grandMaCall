package ua.victor.grandmacall.service.bridge.commands.base;

import android.app.Activity;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import ua.victor.grandmacall.service.bridge.JsNativeBridge;
import ua.victor.grandmacall.service.bridge.commands.DeviceCommand;


public abstract class Command {

    public abstract DeviceCommand getCommand();
    protected abstract Object execute(String commandId, @Nullable String jsonParams, Activity activity, WebView webView);

    public void doAction(String commandId, @Nullable String jsonParams, Activity activity, WebView webView) {
        Object payload = execute(commandId,jsonParams,activity,webView);
        sendPayloadToClient(commandId,activity,webView,payload);
    }

    private void sendPayloadToClient(final String commandId, Activity activity, final WebView webView, final Object payload) {
        activity.runOnUiThread(() -> JsNativeBridge.sendToWebClient(webView,commandId,payload));
    }
}
