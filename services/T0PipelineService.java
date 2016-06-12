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
import java.nio.charset.StandardCharsets;
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
    /******************************/
    /****  PRIVATE ATTRIBUTES *****/
    /******************************/
    private ArrayList<Job> resultJob = null;
    private String rawFolderPath = null;
    
    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/
    public T0PipelineService(String path) {
        this.rawFolderPath = path;
    }

    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
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
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    @Override
    public void run() {
        Log.log("T0 Service Thread: thread running...");        
        try {
            File folder = new File(this.getRawFolderPath());            
            File[] listOfFiles = folder.listFiles(); 

            for (int i = 0; i < listOfFiles.length ; i++) {
                File file = listOfFiles[i];
                if (file.isFile()) {
                    Log.log("T0 Service Thread: the file: " + file.getName() + " is being process...");
                    byte[] encoded;

                    encoded = Files.readAllBytes(Paths.get(file.getPath()));
                    String fileContent = new String(encoded,StandardCharsets.UTF_8);                    

                    //Last iteration
                    if(i == (listOfFiles.length - 1)) {
                        this.nextService.addJob(new Job(file.getName(),fileContent,true));
                    }
                    else{
                        this.nextService.addJob(new Job(file.getName(),fileContent));
                    }   

                    encoded = null; // we empty the byte array
                    Log.log("T0 Service Thread: the file: " + file.getName() + " was added to the T1 queue");
                }
            }            
        } catch (Exception ex) {
            Log.log("T0 Service Thread: IOException: " + ex.getMessage());        
        }                   
        finally {
            stop();
	}     
    }
    
    private void stop(){
        Log.log("T0 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }
}
