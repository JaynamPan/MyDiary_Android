package com.chotix.mydiary1.shared;

import android.app.Application;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MyDiaryApplication extends Application {
    boolean hasPassword = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //Use Fresco
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(listeners)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        //To fix bug : spinner bg is dark when mode is night.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Check password
        if (SPFManager.getPassword(this).equals("")) {
            hasPassword = false;
        } else {
            hasPassword = true;
        }

        //init Theme & language
        initTheme();
        setLocaleLanguage();
    }

    private void initTheme() {
        ThemeManager themeManager = ThemeManager.getInstance();
        themeManager.setCurrentTheme(SPFManager.getTheme(this));
    }

    private void setLocaleLanguage() {
        Locale locale;
        locale = Locale.ENGLISH;
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        overwriteConfigurationLocale(config, locale);
    }

    private void overwriteConfigurationLocale(Configuration config, Locale locale) {
        //TODO FIX updateConfiguration on Android N
        config.setLocale(locale);
        getBaseContext().getResources()
                .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}
