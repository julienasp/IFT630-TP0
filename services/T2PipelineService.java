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
public class T2PipelineService extends ServicePipeline implements Runnable {

    @Override
    public void run() {
        Log.log("T2 Service Thread running...");
        boolean run = true;
        try {
            while(run){
            
                Log.log("T2 Service Thread: waiting for a new job...");
                Job currentJob = this.popJob();
                
                if(currentJob != null){
                    Log.log("T2 Service Thread: the file: " + currentJob.getJobName() + " is being process...");                    
                    
                    //add color to java keywords                    
                    Pattern pattern = Pattern.compile("(abstract|assert|boolean|break|byte|case|catch|char| class|const|continue|default| do |double|else|extends|final|float| for | if | int |interface|long|native| new |private|protected|public|return|short|static|switch|synchronized|this|super|throws|try|void|volatile|while|false|null|true)");
                    Matcher matcher = pattern.matcher(currentJob.getContent());
                    StringBuffer sb = new StringBuffer(currentJob.getContent().length());
                    while (matcher.find()) {                        
                        matcher.appendReplacement(sb, Matcher.quoteReplacement("<span style=\"color:blue\">"+matcher.group(1)+"</span>"));                        
                    }                   
                    matcher.appendTail(sb);
                    currentJob.setContent(sb.toString());
                    
                    this.getNextService().addJob(currentJob);
                     
                    if(currentJob.isLastJob()){
                        Log.log("T2 Service Thread: the 'NoMoreJob' flag has been detected.");
                        Log.log(currentJob.getContent()); 
                        run = false;
                    }                    
                }
                else{
                    Thread.currentThread().sleep(10); // IF EMPTY WE SLEEP FOR A WHILE... TO GIVE TIME TO THE PRODUCER
                }
                
            }
        } catch (Exception ex) {
                Log.log("T2 Service Thread: Exception: " + ex.getMessage());
        } finally {
            stop();
	}
    }
    private void stop(){
        Log.log("T1 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }
        
}
    