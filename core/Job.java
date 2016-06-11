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
    private File jobFile = null;
    private String fileContent = null;
    private boolean isLast = false;

    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/    
    public Job(File jobFile) {
        this.jobFile = jobFile;
    }
    
    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
    public File getJobFile() {
        return jobFile;
    }

    public void setJobFile(File jobFile) {
        this.jobFile = jobFile;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }  
}
