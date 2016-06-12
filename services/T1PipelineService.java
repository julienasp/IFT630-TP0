/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import core.Job;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class T1PipelineService extends ServicePipeline implements Runnable {
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    @Override
    public void run() {
        Log.log("T1 Service Thread running...");
        boolean run = true;
        try {
            while(run){
            
                Log.log("T1 Service Thread: waiting for a new job...");
                Job currentJob = this.popJob();
                
                if(currentJob != null){
                    Log.log("T1 Service Thread: the file: " + currentJob.getJobName() + " is being process...");                    
                    
                    //Remove all the metacharacter
                    currentJob.setContent(currentJob.getContent().replaceAll("&", "&amp"));                    
                    currentJob.setContent(currentJob.getContent().replaceAll("<", "&lt"));                    
                    currentJob.setContent(currentJob.getContent().replaceAll(">", "&gt"));
                    
                    this.getNextService().addJob(currentJob);
                     
                    if(currentJob.isLastJob()){
                        Log.log("T1 Service Thread: the 'NoMoreJob' flag has been detected."); 
                        run = false;
                    }                    
                }
                else{
                    Thread.currentThread().sleep(5); // IF EMPTY WE SLEEP FOR A WHILE... TO GIVE TIME TO THE PRODUCER
                }                
            }
        } catch (Exception ex) {
                Log.log("T1 Service Thread: Exception: " + ex.getMessage());
        } finally {
            stop();
	}
    }
    
    private void stop(){
        Log.log("T1 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }    
}
