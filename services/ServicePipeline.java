package services;
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
public class ServicePipeline {
    /********************************/
    /****  PROTECTED ATTRIBUTES *****/
    /********************************/
    protected ConcurrentLinkedQueue<Job> jobQueue = null;
    protected ServicePipeline nextService = null;

    
    /***************************************/
    /***********  CONSTRUCTOR **************/
    /***************************************/
    public ServicePipeline() {
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
        //Don't need to be synchronized because of ConcurrentLinkedQueue
        this.jobQueue.offer(newJob); //Thread-safe 
    }
    
    public Job popJob(){
        //Don't need to be synchronized because of ConcurrentLinkedQueue
        return this.jobQueue.poll(); //Thread-safe + Exception safe
    }
}
