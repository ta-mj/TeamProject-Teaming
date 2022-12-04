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

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
                Toast.makeText(this, "Ohs, your targetNode is null", Toast.LENGTH_SHORT).show();
                return;
            }
            NodeModel<Brain> a = new NodeModel<>(new Brain("add-" + atomicInteger.getAndIncrement()));
            //NodeModel<Animal> b = new NodeModel<>(new Animal("add-"+atomicInteger.getAndIncrement()));
            //NodeModel<Animal> c = new NodeModel<>(new Animal(R.drawable.ic_14,"add-"+atomicInteger.getAndIncrement()));
            //editor.addChildNodes(targetNode,a,b,c);
            editor.addChildNodes(targetNode, a);


            //add to remove demo cache
            //removeCache.push(targetNode);
            //targetNode = b;
            targetNode = null;
            removeCache = null;
        });

        //remove node
        binding.removeNodeBt.setOnClickListener(v -> {
            if (removeCache == null) {
                Toast.makeText(this, "Ohs, demo removeCache is empty now!! Try to add some nodes firstly!!", Toast.LENGTH_SHORT).show();
                return;
            }
            NodeModel<Brain> toRemoveNode = removeCache;
            targetNode = null;
            //removeCache = toRemoveNode.getParentNode();
            removeCache = null;
            editor.removeNode(toRemoveNode);
        });

        adapter.setOnItemListener((item, node) -> {
            Brain brain = node.getValue();
            Toast.makeText(this, "you click the head of " + brain, Toast.LENGTH_SHORT).show();
            targetNode = node;
            removeCache = node;
        });


        //treeView control listener
        final Object token = new Object();
        Runnable dismissRun = () -> {
            binding.scalePercent.setVisibility(View.GONE);
        };

        binding.baseTreeView.setTreeViewControlListener(new TreeViewControlListener() {
            @Override
            public void onScaling(int state, int percent) {
                Log.e(TAG, "onScaling: " + state + "  " + percent);
                binding.scalePercent.setVisibility(View.VISIBLE);
                if (state == TreeViewControlListener.MAX_SCALE) {
                    binding.scalePercent.setText("MAX");
                } else if (state == TreeViewControlListener.MIN_SCALE) {
                    binding.scalePercent.setText("MIN");
                } else {
                    binding.scalePercent.setText(percent + "%");
                }
                handler.removeCallbacksAndMessages(token);
                handler.postAtTime(dismissRun, token, SystemClock.uptimeMillis() + 2000);
            }

            @Override
            public void onDragMoveNodesHit(NodeModel<?> draggingNode, NodeModel<?> hittingNode, View draggingView, View hittingView) {
                Log.e(TAG, "onDragMoveNodesHit: draging[" + draggingNode + "]hittingNode[" + hittingNode + "]");
            }
        });
    }

    private TreeLayoutManager getTreeLayoutManager() {
        int space_50dp = 30;
        int space_20dp = 20;
        BaseLine line = getLine();
        //return new RightTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new LeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactRightTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new HorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new DownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new UpTreeLayoutManager(this,space_50dp,space_20dp,line);
        return new CompactDownTreeLayoutManager(this, space_50dp, space_20dp, line);
        //return new CompactUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);
        //return new VerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactRingTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new ForceDirectedTreeLayoutManager(this,line);
    }

    private BaseLine getLine() {
        //return new SmoothLine();
        return new StraightLine(Color.parseColor("#000000"), 2);
        //return new PointedLine();
        //return new DashLine(Color.parseColor("#F1286C"),3);
        //return new AngledLine();
    }

    private void setData(BrainTreeViewAdapter adapter) {
        //root
        NodeModel<Brain> root = new NodeModel<>(new Brain("Keyword"));
        TreeModel<Brain> treeModel = new TreeModel<>(root);

        //child nodes
//        NodeModel<Brain> sub0 = new NodeModel<>(new Brain("sub00"));
//        NodeModel<Brain> sub1 = new NodeModel<>(new Brain("sub01"));
//        NodeModel<Brain> sub2 = new NodeModel<>(new Brain("sub02"));
//        NodeModel<Brain> sub3 = new NodeModel<>(new Brain("sub03"));
//        NodeModel<Brain> sub4 = new NodeModel<>(new Brain("sub04"));

        treeModel.addNode(root);
        //mark
        //parentToRemoveChildren = sub0;
        //targetNode = sub1;

        //set data
        adapter.setTreeModel(treeModel);
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
