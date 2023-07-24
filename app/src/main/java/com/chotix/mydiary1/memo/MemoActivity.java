package com.chotix.mydiary1.memo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chotix.mydiary1.R;
import com.chotix.mydiary1.db.DBManager;
import com.chotix.mydiary1.shared.ThemeManager;
import com.chotix.mydiary1.shared.ViewTools;
import com.chotix.mydiary1.shared.statusbar.ChinaPhoneHelper;

import java.util.ArrayList;
import java.util.List;

public class MemoActivity extends FragmentActivity implements
        View.OnClickListener, EditMemoDialogFragment.MemoCallback, OnStartDragListener {
    /**
     * getId
     */
    private long topicId;

    /**
     * UI
     */
    private RelativeLayout RL_memo_topbar_content;
    private TextView TV_memo_topbar_title;
    private ImageView IV_memo_edit;
    private View rootView;
    private TextView TV_memo_item_add;

    /**
     * DB
     */
    private DBManager dbManager;
    /**
     * RecyclerView
     */
    private RelativeLayout RL_memo_content_bg;
    private RecyclerView RecyclerView_memo;
    private MemoAdapter memoAdapter;
    private List<MemoEntity> memoList;
    private ItemTouchHelper touchHelper;

    @Override
    public void onBackPressed() {
        if (memoAdapter.isEditMode()) {
            setEditModeUI(memoAdapter.isEditMode());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        //For set status bar
        ChinaPhoneHelper.setStatusBar(this, true);

        topicId = getIntent().getLongExtra("topicId", -1);
        if (topicId == -1) {
            finish();
        }
        /**
         * init UI
         */
        RL_memo_topbar_content = findViewById(R.id.RL_memo_topbar_content);
        RL_memo_topbar_content.setBackgroundColor(ThemeManager.getInstance().getThemeMainColor(this));

        RL_memo_content_bg = findViewById(R.id.RL_memo_content_bg);
        RL_memo_content_bg.setBackground(ThemeManager.getInstance().getMemoBgDrawable(this, topicId));

        TV_memo_topbar_title = findViewById(R.id.TV_memo_topbar_title);
        IV_memo_edit = findViewById(R.id.IV_memo_edit);
        IV_memo_edit.setOnClickListener(this);
        String diaryTitle = getIntent().getStringExtra("diaryTitle");
        if (diaryTitle == null) {
            diaryTitle = "Memo";
        }
        TV_memo_topbar_title.setText(diaryTitle);

        RecyclerView_memo = findViewById(R.id.RecyclerView_memo);
        rootView = findViewById(R.id.Layout_memo_item_add);
        TV_memo_item_add = rootView.findViewById(R.id.TV_memo_item_add);
        TV_memo_item_add.setTextColor(ThemeManager.getInstance().getThemeDarkColor(this));
        TV_memo_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMemoDialogFragment editMemoDialogFragment = EditMemoDialogFragment.newInstance(
                        topicId, -1, true, "");

                editMemoDialogFragment.show(getSupportFragmentManager(), "editMemoDialogFragment");
            }
        });
        memoList = new ArrayList<>();
        dbManager = new DBManager(MemoActivity.this);

        loadMemo(true);
        initTopicAdapter();
    }
    private void loadMemo(boolean openDB) {
        memoList.clear();
        if (openDB) {
            dbManager.openDB();
        }
        Cursor memoCursor = dbManager.selectMemoAndMemoOrder(topicId);
        for (int i = 0; i < memoCursor.getCount(); i++) {
            memoList.add(
                    new MemoEntity(memoCursor.getLong(0), memoCursor.getString(2), memoCursor.getInt(3) > 0 ? true : false));
            memoCursor.moveToNext();
        }
        memoCursor.close();
        if (openDB) {
            dbManager.closeDB();
        }
    }
    private void initTopicAdapter() {
        //Init topic adapter
        LinearLayoutManager lmr = new LinearLayoutManager(this);
        RecyclerView_memo.setLayoutManager(lmr);
        RecyclerView_memo.setHasFixedSize(true);
        memoAdapter = new MemoAdapter(MemoActivity.this, topicId, memoList, dbManager,
                this, this);
        RecyclerView_memo.setAdapter(memoAdapter);
        //Set ItemTouchHelper
        ItemTouchHelper.Callback callback =
                new MemoItemTouchHelperCallback(memoAdapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(RecyclerView_memo);
    }
    public void setEditModeUI(boolean isEditMode) {
        if (isEditMode) {
            //Cancel edit
            IV_memo_edit.setImageDrawable(ViewTools.getDrawable(MemoActivity.this, R.drawable.ic_mode_edit_white_24dp));
            rootView.setVisibility(View.GONE);
        } else {
            //Start edit
            IV_memo_edit.setImageDrawable(ViewTools.getDrawable(MemoActivity.this, R.drawable.ic_mode_edit_cancel_white_24dp));
            rootView.setVisibility(View.VISIBLE);
        }
        memoAdapter.setEditMode(!isEditMode);
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IV_memo_edit:
                setEditModeUI(memoAdapter.isEditMode());
                break;
        }
    }

    @Override
    public void addMemo(String memoContent) {
        dbManager.openDB();

        //Create newMemoEntity into List first
        MemoEntity newMemoEntity = new MemoEntity(dbManager.insertMemo(memoContent, false, topicId)
                , memoContent, false);
        memoList.add(0, newMemoEntity);
        //Get size
        int orderNumber = memoList.size();
        //Remove this topic's all memo order
        dbManager.deleteAllCurrentMemoOrder(topicId);
        //sort the memo order
        for (MemoEntity memoEntity : memoList) {
            dbManager.insertMemoOrder(topicId, memoEntity.getMemoId(), --orderNumber);
        }
        //Load again
        loadMemo(false);
        dbManager.closeDB();
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateMemo() {
        loadMemo(true);
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}
