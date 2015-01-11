import java.io.IOException;
import java.util.ArrayList;


public class Main {

	static final String moviesFileName = "records.csv";
	static final String evaluationsFileName = "train.csv";
	static final String missingEvaluationsFileName = "task.csv";
	static final boolean limitedFeatures = true;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Utilities util = new Utilities();
		ArrayList<Movie> Movies = util.GetMoviesFromFile(moviesFileName);
		ArrayList<Evaluation> Evaluations = util.GetEvaluations(evaluationsFileName);
		
//		FeatureSelector featureSelector = new FeatureSelector(Movies, Evaluations);
//		featureSelector.Choose10BestFeatures();
		
		ArrayList<Evaluation> MissingEvaluations = util.GetMissingEvaluations(missingEvaluationsFileName);
		
		kNN kNN = new kNN(5, Evaluations, MissingEvaluations, Movies, limitedFeatures);
		ArrayList<Evaluation> PredictedEvaluations = kNN.PredictEvaluations();
		
		util.SavePredictedEvaluationsToFile("result6.csv", PredictedEvaluations);
	}
}