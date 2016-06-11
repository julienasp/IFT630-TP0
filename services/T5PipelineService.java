/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import utils.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class T5PipelineService extends ServicePipeline implements Runnable {
    private String formatedFolderPath = null;

    public T5PipelineService(String ffp) {
        this.formatedFolderPath = ffp;
    }
    
    
    
    @Override
    public void run() {
        Log.log("T5 Service Thread running...");
        
    }
    
    
}
