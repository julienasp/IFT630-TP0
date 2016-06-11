
import java.util.concurrent.ConcurrentLinkedQueue;
import core.*;
import utils.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JUASP-G73-Android
 */
public class servicePipeline {
    /********************************/
    /****  PROTECTED ATTRIBUTES *****/
    /********************************/
    protected ConcurrentLinkedQueue<Job> jobQueue = null;
    protected servicePipeline nextService = null;

    
    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/
    public servicePipeline() {
        jobQueue = new ConcurrentLinkedQueue<>();
    }

    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
    public ConcurrentLinkedQueue<Job> getJobQueue() {
        return jobQueue;
    }

    public void setJobQueue(ConcurrentLinkedQueue<Job> jobQueue) {
        this.jobQueue = jobQueue;
    }    

    public servicePipeline getNextService() {
        return nextService;
    }

    public void setNextService(servicePipeline nextService) {
        this.nextService = nextService;
    }
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    public synchronized void addJob(Job newJob){
        this.jobQueue.add(newJob);
    }
    
    public synchronized Job popJob(){
        return this.jobQueue.poll(); //Exception safe
    }
}
