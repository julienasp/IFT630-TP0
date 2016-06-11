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
    private File jobFile = null;
    private boolean isLast = false;

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
