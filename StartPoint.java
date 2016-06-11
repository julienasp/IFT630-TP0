
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.Log;
import services.*;

public class StartPoint {
        public static final int NBOFSERVICES = 6;
        public static final String rawFolderPath = "rawfiles/";
        public static final String formatedFolderPath = "/formatedfiles/";
	public static void main(String[] args) {
            Log.log("Prime thread is runnning...");            
            ExecutorService pool = Executors.newFixedThreadPool(NBOFSERVICES);
            
            Log.log("Starting all the services...");
            
            //Creation of the services
            T0PipelineService t0Service = new T0PipelineService(rawFolderPath);
            T1PipelineService t1Service = new T1PipelineService();
            T2PipelineService t2Service = new T2PipelineService();
            T3PipelineService t3Service = new T3PipelineService();
            T4PipelineService t4Service = new T4PipelineService();
            T5PipelineService t5Service = new T5PipelineService(formatedFolderPath);
            
            //Pipeline pattern assignements
            t0Service.setNextService(t1Service);
            t1Service.setNextService(t2Service);
            t2Service.setNextService(t3Service);
            t3Service.setNextService(t4Service);
            t4Service.setNextService(t5Service);
            
            //Starting all the services
            pool.execute(t0Service);
            pool.execute(t1Service);
            //pool.execute(t2Service);
            //pool.execute(t3Service);
            //pool.execute(t4Service);
            //pool.execute(t5Service);
            
            
	}
}
