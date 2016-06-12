
import core.Job;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Log;
import services.*;

public class StartPointNoThread {
    /************************************/
    /****  PUBLIC STATIC ATTRIBUTES *****/
    /************************************/
    public static final String RAWFILEPATH = "rawfiles/";
    public static final String HTMLFILEPATH = "formatedfiles/";
    public static final int POOLSIZE = 6;
    public static ArrayList<Job> jobQueue = null;
    
     /***************************************/
    /*************  MAIN *******************/
    /***************************************/
    public static void main(String[] args) {
        Log.log("Prime thread is runnning...");
        jobQueue = new ArrayList<Job>();
        
        //Chrono start!
        long start = System.nanoTime();
        t0();
        t1();
        t2();
        t3();
        t4();
        t5();
        
        //Chrono end!
        long time = System.nanoTime() - start;
        System.out.printf("Prime thread: Tasks took %.3f ms to run%n", time/1e6);
    }
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    public static void t0(){
        File folder = new File(RAWFILEPATH);            
        File[] listOfFiles = folder.listFiles(); 

        for (int i = 0; i < listOfFiles.length ; i++) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                Log.log("T0 : the file: " + file.getName() + " is being process...");
                byte[] encoded;
                try {
                    encoded = Files.readAllBytes(Paths.get(file.getPath()));
                    String fileContent = new String(encoded);
                    jobQueue.add(new Job(file.getName(),fileContent));
                } catch (IOException ex) {
                    Log.log("NO THREAD t0(): IO EXCEPTION" + ex.getMessage());
                }
            }
        }
    }

    public static void t1(){  
        for (Job currentJob: jobQueue) {
            Log.log("T1 : the file: " + currentJob.getJobName() + " is being process...");
            
            //Remove all the metacharacter
            currentJob.setContent(currentJob.getContent().replaceAll("&", "&amp"));
            currentJob.setContent(currentJob.getContent().replaceAll("<", "&lt"));
            currentJob.setContent(currentJob.getContent().replaceAll(">", "&gt"));
        }            
    }

    public static void t2(){  
        for (Job currentJob: jobQueue) {
            Log.log("T2 : the file: " + currentJob.getJobName() + " is being process...");
            
            //add color to java keywords 
            Pattern pattern = Pattern.compile("(alignas|alignof| and |and_eq|asm|atomic_cancel|atomic_commit|atomic_noexcept|auto |bitand|bitor|bool |break|case|catch|char |char16_t|char32_t|class|compl|vconcept|const|constexpr|const_cast|continue|decltype|default|delete|do |double|dynamic_cast|else|enum|explicit|export|extern|false|float|for|friend|goto|if|inline|int |import|long |module|mutable|namespace|new|noexcept|not|not_eq|nullptr|operator| or |or_eq|private|protected|public|register|reinterpret_cast|requires|return |short |signed|sizeof|static|static_assert|static_cast|struct|switch|synchronized|template|this|thread_local|throw|true|try|typedef|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while|xor|xor_eq|override|final|transaction_safe|transaction_safe_dynamic)");
            Matcher matcher = pattern.matcher(currentJob.getContent());
            StringBuffer sb = new StringBuffer(currentJob.getContent().length());
            while (matcher.find()) {                        
                matcher.appendReplacement(sb, Matcher.quoteReplacement("<span style=\"color:blue\">"+matcher.group(1)+"</span>"));                        
            }                   
            matcher.appendTail(sb);
            currentJob.setContent(sb.toString());
        }            
    }

    public static void t3(){  
        for (Job currentJob: jobQueue) {
            Log.log("T3 : the file: " + currentJob.getJobName() + " is being process...");
            //add color to comments                    
            Pattern pattern = Pattern.compile("(\\/\\*(\\*(?!\\/)|[^*])*\\*\\/|\\/\\/.*)");
            Matcher matcher = pattern.matcher(currentJob.getContent());
            StringBuffer sb = new StringBuffer(currentJob.getContent().length());
            while (matcher.find()) {                        
                matcher.appendReplacement(sb, Matcher.quoteReplacement("<span style=\"color:green\">"+matcher.group(1)+"</span>"));                        
            }                   
            matcher.appendTail(sb);
            currentJob.setContent(sb.toString());
        }            
    }

    public static void t4(){  
        for (Job currentJob: jobQueue) {
            Log.log("T4: the file: " + currentJob.getJobName() + " is being process...");
            
            //add color to preprocessor                   
            Pattern pattern = Pattern.compile("(#.*)");
            Matcher matcher = pattern.matcher(currentJob.getContent());
            StringBuffer sb = new StringBuffer(currentJob.getContent().length());
            while (matcher.find()) {                        
                matcher.appendReplacement(sb, Matcher.quoteReplacement("<span style=\"color:red\">"+matcher.group(1)+"</span>"));                        
            }                   
            matcher.appendTail(sb);
            currentJob.setContent(sb.toString());
        }            
    }

    public static void t5(){ 
        String html = null;
        for (Job currentJob: jobQueue) {
            Log.log("T5 : " + currentJob.getJobName() + " is being process...");                    

            //html formating 
            html = "<pre>\n" +
                "<!DOCTYPE html>\n" +                        
                "<html>\n" +
                "<head>\n" +
                "<meta charset='UTF-8'>"+
                "<title>"+currentJob.getJobName()+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>html-format of a cpp source file NO THREAD</h1>\n" +
                currentJob.getContent() +
                "\n" +
                "</body>\n" +
                "</html>"; 
            
            currentJob.setContent(html);                        

            try {
                File file = new File(HTMLFILEPATH + currentJob.getJobName().replaceAll("\\.[^/.]+$", "nothread.html"));
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(currentJob.getContent());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                Log.log("NO THREAD t5(): IO EXCEPTION" + ex.getMessage());
            }
        }            
    }
}
