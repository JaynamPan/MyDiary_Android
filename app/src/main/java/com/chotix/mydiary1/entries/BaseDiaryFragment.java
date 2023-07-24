package com.chotix.mydiary1.entries;


import androidx.fragment.app.Fragment;

import com.chotix.mydiary1.entries.entry.EntriesEntity;

import java.util.List;

public class BaseDiaryFragment extends Fragment {
    public long getTopicId() {

        return ((DiaryActivity) getActivity()).getTopicId();

    }
    public List<EntriesEntity> getEntriesList() {
        return ((DiaryActivity) getActivity()).getEntriesList();
    }

    public void updateEntriesList() {
        ((DiaryActivity) getActivity()).loadEntries();
    }
}
