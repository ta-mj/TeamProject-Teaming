package com.example.teamproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ProjectViewer extends LinearLayout {
    TextView textView;
    ImageView imageView;
    public ProjectViewer(Context context){
        super(context);

        init(context);
    }
    public ProjectViewer(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        init(context);
    }
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.projectitem,this,true);

        textView = (TextView)findViewById(R.id.teamNameView);
        imageView = (ImageView) findViewById(R.id.teamImage);
    }

    public void setItem(ProjectItem projectItem){
        textView.setText(projectItem.getText());
        imageView.setImageResource(projectItem.getImage());
    }
}