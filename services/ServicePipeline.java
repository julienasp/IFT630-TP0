package services;

import core.*;
import java.util.concurrent.LinkedBlockingQueue;
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
public class ServicePipeline {
    /********************************/
    /****  PROTECTED ATTRIBUTES *****/
    /********************************/    
    protected LinkedBlockingQueue<Job> jobQueue = null;
    protected ServicePipeline nextService = null;

    
    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/
    public ServicePipeline() {
        jobQueue = new LinkedBlockingQueue<>();
    }
  
    /***************************************/
    /********  GETTER AND SETTER ***********/
    /***************************************/
    public LinkedBlockingQueue<Job> getJobQueue() {
        return jobQueue;
    }
    
    public void setJobQueue(LinkedBlockingQueue<Job> jobQueue) {
        this.jobQueue = jobQueue;
    }
    public ServicePipeline getNextService() {
        return nextService;
    }

    public void setNextService(ServicePipeline nextService) {
        this.nextService = nextService;
    }
    
    /***************************************/
    /*************  METHODS ****************/
    /***************************************/
    public void addJob(Job newJob){
        try {
            //Don't need to be synchronized because of LinkedBlockingQueue
            this.jobQueue.put(newJob); //Thread-safe 
        } catch (InterruptedException ex) {
            Log.log("ServicePipeline Service Thread: InterruptedException: " + ex.getMessage());
        }
    }
    
    public Job popJob(){
        Job j = null;
        try {
            //Don't need to be synchronized because of LinkedBlockingQueue
            j = this.jobQueue.take(); //Thread-safe + Exception safe
        } catch (InterruptedException ex) {
            Log.log("ServicePipeline Service Thread: InterruptedException: " + ex.getMessage());
        }
        return j;
    }
}
