
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.Log;
import services.*;

public class StartPoint {
        public static final int NBOFSERVICES = 5;
        public static final String rawFolderPath = "/rawfiles/";
        public static final String formatedFolderPath = "/formatedfiles/";
	public static void main(String[] args) {
            Log.log("Début du Thread principal pour le TP0");            
            ExecutorService pool = Executors.newFixedThreadPool(NBOFSERVICES);
            
            Log.log("Démarrage des services");
            pool.execute(new T0PipelineService());
            
            
                
                
                
                File folder = new File(rawFolderPath);            
		

                File[] listOfFiles = folder.listFiles();

                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        System.out.println(file.getName());
                    }
                }
	}

}
