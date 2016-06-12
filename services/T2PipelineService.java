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

    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
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
                    Pattern pattern = Pattern.compile("(alignas|alignof| and |and_eq|asm|atomic_cancel|atomic_commit|atomic_noexcept|auto |bitand|bitor|bool |break|case|catch|char |char16_t|char32_t|class|compl|vconcept|const|constexpr|const_cast|continue|decltype|default|delete|do |double|dynamic_cast|else|enum|explicit|export|extern|false|float|for|friend|goto|if|inline|int |import|long |module|mutable|namespace|new|noexcept|not|not_eq|nullptr|operator| or |or_eq|private|protected|public|register|reinterpret_cast|requires|return |short |signed|sizeof|static|static_assert|static_cast|struct|switch|synchronized|template|this|thread_local|throw|true|try|typedef|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while|xor|xor_eq|override|final|transaction_safe|transaction_safe_dynamic)");
                    Matcher matcher = pattern.matcher(currentJob.getContent());
                    StringBuffer sb = new StringBuffer(currentJob.getContent().length());
                    while (matcher.find()) {                        
                        matcher.appendReplacement(sb, Matcher.quoteReplacement("<span style=\"color:blue\">"+matcher.group(1)+"</span>"));                        
                    }                   
                    matcher.appendTail(sb);
                    currentJob.setContent(sb.toString());
                    
                    this.getNextService().addJob(currentJob);
                    Log.log("T2 Service Thread: the file: " + currentJob.getJobName() + " was added to the T3 queue");
                    
                    if(currentJob.isLastJob()){
                        Log.log("T2 Service Thread: the 'NoMoreJob' flag has been detected.");
                        run = false;
                    }                    
                }
                else{
                    //Thread.currentThread().sleep(10); // IF EMPTY WE SLEEP FOR A WHILE... TO GIVE TIME TO THE PRODUCER
                }                
            }
        } catch (Exception ex) {
                Log.log("T2 Service Thread: Exception: " + ex.getMessage());
        } finally {
            stop();
	}
    }
    
    private void stop(){
        Log.log("T2 Service Thread: is being stop...."); 
        Thread.currentThread().interrupt();
    }        
}
    
