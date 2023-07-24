package com.chotix.mydiary1.backup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import com.chotix.mydiary1.R;
import com.chotix.mydiary1.shared.ThemeManager;
import com.nononsenseapps.filepicker.AbstractFilePickerFragment;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.FilePickerFragment;

import java.io.File;

public class DirectoryPickerActivity extends FilePickerActivity {
    /**
     * Need access to the fragment
     */
    private DirectoryPickerFragment currentFragment;

    /**
     * Return a copy of the new fragment and set the variable above.
     */
    @Override
    protected AbstractFilePickerFragment<File> getFragment(@Nullable String startPath, int mode, boolean allowMultiple, boolean allowCreateDir, boolean allowExistingFile, boolean singleClick) {
        // startPath is allowed to be null.
        // In that case, default folder should be SD-card and not "/"
        String path = (startPath != null ? startPath
                : Environment.getExternalStorageDirectory().getPath());

        currentFragment = new DirectoryPickerFragment();
        currentFragment.setArgs(path, mode, allowMultiple, allowCreateDir,
                allowExistingFile, singleClick);
        return currentFragment;
    }
    /**
     * Override the back-button.
     */
    @Override
    public void onBackPressed() {
        // If at top most level, normal behaviour
        if (currentFragment.isBackTop()) {
            super.onBackPressed();
        } else {
            // Else go up
            currentFragment.goUp();
        }
    }
}
