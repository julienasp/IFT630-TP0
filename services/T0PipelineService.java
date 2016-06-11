/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;
import core.Job;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class T0PipelineService extends ServicePipeline implements Runnable {    
    private ArrayList<Job> resultJob = null;
    private String rawFolderPath = null;
    
    public T0PipelineService(String path) {
        this.rawFolderPath = path;
    }

    public String getRawFolderPath() {
        return rawFolderPath;
    }

    public void setRawFolderPath(String rawFolderPath) {
        this.rawFolderPath = rawFolderPath;
    }

    public ArrayList<Job> getResultJob() {
        return resultJob;
    }

    public void setResultJob(ArrayList<Job> resultJob) {
        this.resultJob = resultJob;
    }    
    
    @Override
    public void run() {
        Log.log("T0 Service Thread: thread running...");        
       
        File folder = new File(this.getRawFolderPath());            
	File[] listOfFiles = folder.listFiles(); 
        
        for (File file : listOfFiles) {            
            if (file.isFile()) {
                Log.log("T0 Service Thread: the file: " + file.getName() + " is being process...");
                byte[] encoded;
                try {
                    encoded = Files.readAllBytes(Paths.get(file.getPath()));
                    String fileContent = new String(encoded);
                    this.nextService.addJob(new Job(fileContent));
                    Log.log("T0 Service Thread: the content of the file:" + file.getName() + " is: " + fileContent);
                    
                    encoded = null; // we empty the byte array
                    Log.log("T0 Service Thread: the file: " + file.getName() + " was added to the T1 queue");
                } catch (IOException ex) {
                    Logger.getLogger(T0PipelineService.class.getName()).log(Level.SEVERE, null, ex);
                }                                   
            }
               
        }
        Log.log("T0 Service Thread: no more file to process.");
        Log.log("T0 Service Thread: is being stop....");    
       

     
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
