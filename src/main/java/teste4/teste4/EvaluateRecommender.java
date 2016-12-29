package teste4.teste4;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class EvaluateRecommender {
	
	   // Define a static logger variable so that it references the
	   // Logger instance named "MyApp".
	static Logger logger = Logger.getLogger(App.class);

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TasteException 
	 */
	public static void main(String[] args) throws IOException, TasteException {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
		DataModel model = new FileDataModel(new File("data/dataset.csv"));
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		
		RecommenderBuilder builder = new MyRecommenderbuilder();
		
		double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
		
		System.out.println(result);
	}

}


class MyRecommenderbuilder implements RecommenderBuilder
{

	public Recommender buildRecommender(DataModel arg0) throws TasteException {
		UserSimilarity similarity = new PearsonCorrelationSimilarity(arg0);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, arg0);
		return new GenericUserBasedRecommender(arg0, neighborhood, similarity);
	}
}