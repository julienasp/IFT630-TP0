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
    private String jobName = null;
    private String content = null;   
    private boolean lastJob = false;

    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/    
    public Job(String n, String c) {
        this.jobName = n;
        this.content = c;
    }
    public Job(String n, String c, boolean b) {
        this.jobName = n;
        this.content = c;
        this.lastJob = b;
    }  

    
    public String getJobName() {
        return jobName;
    }

    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
    public void setJobName(String jobName) {    
        this.jobName = jobName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLastJob() {
        return lastJob;
    }

    public void setLastJob(boolean isLast) {
        this.lastJob = isLast;
    }  
}
