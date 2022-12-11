package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.teamproject.databinding.ActivityBrainstormingBinding;
import com.gyso.treeview.TreeViewEditor;
import com.gyso.treeview.layout.CompactDownTreeLayoutManager;
import com.gyso.treeview.layout.TreeLayoutManager;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.line.StraightLine;
import com.gyso.treeview.listener.TreeViewControlListener;
import com.gyso.treeview.model.NodeModel;
import com.gyso.treeview.model.TreeModel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Brainstorming extends AppCompatActivity {
    public static final String TAG = Brainstorming.class.getSimpleName();
    private ActivityBrainstormingBinding binding;
    private NodeModel<Brain> removeCache;
    private NodeModel<Brain> targetNode;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private Handler handler = new Handler();
    private NodeModel<Brain> parentToRemoveChildren = null;
    private static TreeModel<Brain> treeModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brainstorming);

        binding = ActivityBrainstormingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);

        //툴바 뒤로가기 보이게 하는 코드
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //툴바 로고 글씨 안 보이게 하는 코드
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //demo init
        initWidgets();
    }

    /**
     * To use a tree view, you should do 6 steps as follows:
     * 1 customs adapter
     * <p>
     * 2 configure layout manager. Space unit is dp.
     * You can custom you line by extends {@link BaseLine}
     * <p>
     * 3 view setting
     * <p>
     * 4 nodes data setting
     * <p>
     * 5 if you want to edit the map, then get and use and tree view editor
     * <p>
     * 6 you own others jobs
     */
    private void initWidgets() {
        //1 customs adapter
        BrainTreeViewAdapter adapter = new BrainTreeViewAdapter();

        //2 configure layout manager; unit dp
        TreeLayoutManager treeLayoutManager = getTreeLayoutManager();

        //3 view setting
        binding.baseTreeView.setAdapter(adapter);
        binding.baseTreeView.setTreeLayoutManager(treeLayoutManager);

        //4 nodes data setting
        setData(adapter);

        //5 get an editor. Note: an adapter must set before get an editor.
        final TreeViewEditor editor = binding.baseTreeView.getEditor();

        //6 you own others jobs
        doYourOwnJobs(editor, adapter);
    }

    void doYourOwnJobs(TreeViewEditor editor, BrainTreeViewAdapter adapter) {

        //drag to move node
        binding.dragEditModeRd.setOnCheckedChangeListener((v, isChecked)->{
            editor.requestMoveNodeByDragging(isChecked);
        });




        //focus, means that tree view fill center in your window viewport
        binding.viewCenterBt.setOnClickListener(v -> editor.focusMidLocation());

        //add some nodes
        binding.addNodesBt.setOnClickListener(v -> {
            if (targetNode == null) {
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                return;
            }
            NodeModel<Brain> a = new NodeModel<>(new Brain("키워드 추가"));
            editor.addChildNodes(targetNode, a);
            hideKeyboard();

            targetNode = null;
            removeCache = null;
        });

        //remove node
        binding.removeNodeBt.setOnClickListener(v -> {
            if (removeCache == null) {
                //Toast.makeText(this, "Ohs, demo removeCache is empty now!! Try to add some nodes firstly!!", Toast.LENGTH_SHORT).show();
                return;
            }
            NodeModel<Brain> toRemoveNode = removeCache;
            targetNode = null;
            removeCache = null;
            editor.removeNode(toRemoveNode);
            hideKeyboard();
        });

        adapter.setOnItemListener((item, node) -> {
            Brain brain = node.getValue();
            Toast.makeText(this,  "선택되었습니다.", Toast.LENGTH_SHORT).show();
            targetNode = node;
            removeCache = node;
        });
    }

    private TreeLayoutManager getTreeLayoutManager() {
        int space_50dp = 30;
        int space_20dp = 20;
        BaseLine line = getLine();
        return new CompactDownTreeLayoutManager(this, space_50dp, space_20dp, line);
    }

    private BaseLine getLine() {
        return new StraightLine(Color.parseColor("#000000"), 2);
    }

    private void setData(BrainTreeViewAdapter adapter) {

        if(treeModel == null) {
            //root
            NodeModel<Brain> root = new NodeModel<>(new Brain("프로젝트"));
            treeModel = new TreeModel<>(root);
            treeModel.addNode(root);
        }

        //set data
        adapter.setTreeModel(treeModel);
    }

    //키보드 숨기는 메소드
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //뒤로가기 버튼 클릭 시 이벤트 처리
                finish();
                break;
        }
        return true;
    }
}
