package ua.victor.grandmacall.service.bridge.commands.impl;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import ua.victor.grandmacall.model.LoudMode;
import ua.victor.grandmacall.service.bridge.commands.DeviceCommand;
import ua.victor.grandmacall.service.bridge.commands.base.Command;

public class TriggerLoudModeCommand extends Command {

    @Override
    public DeviceCommand getCommand() {
        return DeviceCommand.triggerLoudMode;
    }

    @Override
    protected Object execute(String commandId, @Nullable String jsonParams, Activity activity, WebView webView) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoudMode loudMode = objectMapper.readValue(jsonParams,LoudMode.class);
            AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager==null) throw new RuntimeException("AudioManager is null");
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(loudMode.isLoudMode());
            audioManager.setMode(AudioManager.MODE_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
