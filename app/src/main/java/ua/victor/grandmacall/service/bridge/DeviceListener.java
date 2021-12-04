package ua.victor.grandmacall.service.bridge;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import ua.victor.grandmacall.service.bridge.commands.CommandExecuter;
import ua.victor.grandmacall.service.bridge.commands.DeviceCommand;
import ua.victor.grandmacall.service.bridge.commands.impl.AcceptCallCommand;
import ua.victor.grandmacall.service.bridge.commands.impl.EndCallCommand;
import ua.victor.grandmacall.service.bridge.commands.impl.TriggerLoudModeCommand;

public class DeviceListener {

    public void setUpBridge(final Activity activity, final WebView webView) {
        final CommandExecuter commandExecuter = new CommandExecuter();
        commandExecuter.registerCommand(new EndCallCommand());
        commandExecuter.registerCommand(new AcceptCallCommand());
        commandExecuter.registerCommand(new TriggerLoudModeCommand());

        webView.addJavascriptInterface(new JsNativeBridge.ClientCommandCallLIstener(){
            @JavascriptInterface
            @Override
            public void callHostCommand(String commandName, String eventId, @Nullable String jsonParams) {
                commandExecuter.executeCommand(DeviceCommand.valueOf(commandName),eventId,jsonParams,activity,webView);
            }
        },"__host__");
    }



}
