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

    @Override
    public void run() {
        Log.log("T1 Service Thread running...");
        boolean run = true;
        while(run){
            try {
                Log.log("T1 Service Thread: waiting for a new job...");                
                Job currentJob = this.popJob();
                
                if(currentJob != null){
                    Log.log("T1 Service Thread: the file: " + currentJob.getJobName() + " is being process...");
                }
            } catch (Exception ex) {
                Log.log("T1 Service Thread: Exception: " + ex.getMessage());
            }
            
        }
        
    }
    
    
}
