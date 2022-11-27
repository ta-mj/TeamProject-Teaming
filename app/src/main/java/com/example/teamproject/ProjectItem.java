package com.example.teamproject;

public class ProjectItem {
    private int image;
    private TeamProject myProject;

    public ProjectItem(int image, TeamProject pr){
        this.image = Integer.parseInt(String.valueOf(image));
        this.myProject = pr;
    }
    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public TeamProject getProject() { return myProject; }

    public void setProject(TeamProject pr) { this.myProject = pr; }
}