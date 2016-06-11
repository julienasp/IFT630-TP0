package core;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JUASP-G73-Android
 */
public class Job {
    /******************************/
    /****  PRIVATE ATTRIBUTES *****/
    /******************************/    
    private String content = null;
    private boolean isLast = false;

    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/    
    public Job(String c) {
        this.content = c;
    }
    public Job(String c, boolean b) {
        this.isLast = b;
    }  
    
    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }  
}
