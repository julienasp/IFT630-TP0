/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import core.Job;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class T4PipelineService extends ServicePipeline implements Runnable {

    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    @Override
    public void run() {
        Log.log("T4 Service Thread running...");
        boolean run = true;
        try {
            while(run){            
                Log.log("T4 Service Thread: waiting for a new job...");
                Job currentJob = this.popJob();
                
                if(currentJob != null){
                    Log.log("T4 Service Thread: the file: " + currentJob.getJobName() + " is being process...");                    
                    
                    //add color to preprocessor                   
                    Pattern pattern = Pattern.compile("(#.*)");
                    Matcher matcher = pattern.matcher(currentJob.getContent());
                    StringBuffer sb = new StringBuffer(currentJob.getContent().length());
                    while (matcher.find()) {                        
                        matcher.appendReplacement(sb, Matcher.quoteReplacement("<span class=\"process\">"+matcher.group(1)+"</span>"));                        
                    }                   
                    matcher.appendTail(sb);
                    currentJob.setContent(sb.toString());
                    
                    this.getNextService().addJob(currentJob);
                    Log.log("T4 Service Thread: the file: " + currentJob.getJobName() + " was added to the T5 queue");
                     
                    if(currentJob.isLastJob()){
                        Log.log("T4 Service Thread: the 'NoMoreJob' flag has been detected.");
                        run = false;                        
                    }                    
                }
                else{
                    //Thread.currentThread().sleep(20); // IF EMPTY WE SLEEP FOR A WHILE... TO GIVE TIME TO THE PRODUCER
                }                
            }
        } catch (Exception ex) {
                Log.log("T4 Service Thread: Exception: " + ex.getMessage());
        } finally {
            stop();
	}
    }
    
    private void stop(){
        Log.log("T4 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }
}
    
    

