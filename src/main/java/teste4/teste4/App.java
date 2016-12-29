package teste4.teste4;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
// Import log4j classes.
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
	   // Define a static logger variable so that it references the
	   // Logger instance named "MyApp".
	static Logger logger = Logger.getLogger(App.class);
	   
    public static void main( String[] args ) throws IOException, TasteException
    {

        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
    	System.out.println( "Start!" );
    	DataModel model = new FileDataModel(new File("data/dataset.csv"));
    	
    	UserSimilarity similarity = (UserSimilarity) new PearsonCorrelationSimilarity(model);
    	
    	UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
    	
    	Recommender recomender = new GenericUserBasedRecommender(model, neighborhood, similarity);
    	
    	List<RecommendedItem> recomendations = recomender.recommend(2, 3);
    	
    	for(RecommendedItem recomendation: recomendations){
    		System.out.println(recomendation);
    	}
    	
        System.out.println( "End!" );
    }
}
