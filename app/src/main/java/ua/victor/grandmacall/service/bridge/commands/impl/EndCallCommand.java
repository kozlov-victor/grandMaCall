package ua.victor.grandmacall.service.bridge.commands.impl;

import android.app.Activity;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import ua.victor.grandmacall.service.AppCallService;
import ua.victor.grandmacall.service.bridge.commands.DeviceCommand;
import ua.victor.grandmacall.service.bridge.commands.base.Command;

public class EndCallCommand extends Command {

    @Override
    public DeviceCommand getCommand() {
        return DeviceCommand.endCall;
    }

    @Override
    protected Object execute(String commandId, @Nullable String jsonParams, Activity activity, WebView webView) {
        if (AppCallService.currentCall!=null) AppCallService.currentCall.disconnect();
        return null;
    }
}
