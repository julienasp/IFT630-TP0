/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import core.Job;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class T5PipelineService extends ServicePipeline implements Runnable {
    /******************************/
    /****  PRIVATE ATTRIBUTES *****/
    /******************************/
    private String formatedFolderPath = null;

    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/
    public T5PipelineService(String ffp) {
        this.formatedFolderPath = ffp;
    }    
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    @Override
    public void run() {
        Log.log("T5 Service Thread running...");
        boolean run = true;
        String html = "";
        try {
            while(run){            
                Log.log("T5 Service Thread: waiting for a new job...");
                Job currentJob = this.popJob();               
                
                if(currentJob != null){
                    Log.log("T5 Service Thread: the file: " + currentJob.getJobName() + " is being process...");                    
                    
                    //html formating 
                    html = "<pre>\n" +
                        "<!DOCTYPE html>\n" +                        
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset='UTF-8'>"+
                        "<style>\n"+
                            ".keyword {\n"+
                                "color: blue;\n"+
                            "}\n"+
                            ".comment > .keyword {\n"+
                                "color: green;\n"+
                            "}\n"+
                             ".process > .keyword {\n"+
                                "color: red;\n"+
                            "}\n"+
                            ".comment {\n"+
                                "color: green;\n"+
                            "} \n"+
                            ".process {\n"+
                                "color: red;\n"+
                            "} \n"+
                            "</style>\n"+
                        "<title>"+currentJob.getJobName()+"</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h1>html-format of a cpp source file</h1>\n" +
                        currentJob.getContent() +
                        "\n" +
                        "</body>\n" +
                        "</html>";                                        
                    
                        currentJob.setContent(html);
                        
                        File file = new File(formatedFolderPath + currentJob.getJobName().replaceAll("\\.[^/.]+$", "thread.html"));
			FileWriter fileWriter = new FileWriter(file);			
			fileWriter.write(currentJob.getContent());
			fileWriter.flush();
			fileWriter.close();
                     
                    if(currentJob.isLastJob()){
                        Log.log("T5 Service Thread: the 'NoMoreJob' flag has been detected.");
                        run = false;                        
                    }                    
                }
                else{
                    //Thread.currentThread().sleep(25); // IF EMPTY WE SLEEP FOR A WHILE... TO GIVE TIME TO THE PRODUCER
                }                
            }
        } catch (Exception ex) {
                Log.log("T5 Service Thread: Exception: " + ex.getMessage());
        } finally {
            stop();
	}
    }
    
    private void stop(){
        Log.log("T5 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }
}
    
    
