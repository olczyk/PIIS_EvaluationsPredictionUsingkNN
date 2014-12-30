import java.util.ArrayList;
import java.util.Collections;


public class kNN {
	
	int k;
	ArrayList<Evaluation> Evaluations;
	ArrayList<Evaluation> MissingEvaluations;
	ArrayList<Movie> Movies;
	
	kNN(int k, ArrayList<Evaluation> Evaluations, ArrayList<Evaluation> MissingEvaluations, ArrayList<Movie> Movies)
	{
		this.k = k;
		this.Evaluations = Evaluations;
		this.MissingEvaluations = MissingEvaluations;
		this.Movies = Movies;
	}
	
	public ArrayList<Evaluation> PredictEvaluations()
	{
		for(int i=5; i<1817; i++) //iterating through each person (I know the range from the observation)
		{
			ArrayList<Evaluation> PersonEvaluations = GetPersonEvaluations(i, Evaluations);
			ArrayList<Evaluation> PersonMissingEvaluations = GetPersonEvaluations(i, MissingEvaluations);
			
			if(PersonMissingEvaluations.size() > 0 && PersonEvaluations.size() > 0) //TODO sprawdziæ, czy w ogóle kiedyœ tu wchodzi
			{
				System.out.println("*********** Person ID = " + i + " ***********");
				
				for(int j=0; j<PersonMissingEvaluations.size(); j++) //iterating through the not evaluated movies
				{
					int evaluationId = PersonMissingEvaluations.get(j).id;
					int movieId = PersonMissingEvaluations.get(j).movieId;
					
					if(k == 1)
					{
						PersonMissingEvaluations.get(j).evaluation = GetPredictionForK_1(movieId, PersonEvaluations);
						FillMissingEvaluation(evaluationId, PersonMissingEvaluations.get(j).evaluation);
					}
					else if(k ==3)
					{
						PersonMissingEvaluations.get(j).evaluation = GetPredictionForBiggerK(movieId, PersonEvaluations);
						FillMissingEvaluation(evaluationId, PersonMissingEvaluations.get(j).evaluation);
					}
					else if(k == 5)
					{
						PersonMissingEvaluations.get(j).evaluation = GetPredictionForBiggerK(movieId, PersonEvaluations);
						FillMissingEvaluation(evaluationId, PersonMissingEvaluations.get(j).evaluation);
					}
				}
			}
			else
			{
				if(PersonMissingEvaluations.size() == 0)
				{
					System.out.println("WARNING! Person ID = " + i + " has evaluated all of the movies.");
				}
				
				if(PersonEvaluations.size() == 0)
				{
					System.out.println("WARNING! Person ID = " + i + " has not evaluated any movie. No data for predictions.");
				}
			}
		}
		return MissingEvaluations;
	}
	
	private ArrayList<Evaluation> GetPersonEvaluations(int personId, ArrayList<Evaluation> Evaluations)
	{
		ArrayList<Evaluation> PersonEvaluations = new ArrayList<Evaluation>();
		
		for(int i=0;i<Evaluations.size();i++)
		{
			if(personId == Evaluations.get(i).personId)
			{
				PersonEvaluations.add(Evaluations.get(i));
			}
		}
		return PersonEvaluations;
	}
	
	private String GetPredictionForK_1(int movieId, ArrayList<Evaluation> PersonEvaluations)
	{
		String prediction = "NULL";
		
		BestMatch bestMatch = new BestMatch(0, "0");
		
		for(int i=0; i<PersonEvaluations.size(); i++)
		{
			int currentEvaluatedMovieId = PersonEvaluations.get(i).movieId;
			Comparator comparator = new Comparator(Movies.get(movieId-1), Movies.get(currentEvaluatedMovieId-1), Movies); 
			double similarityRate = comparator.GetMoviesSimilarityRate();
			
			if(similarityRate > bestMatch.similarityRate)
			{
				bestMatch.similarityRate = similarityRate;
				bestMatch.evaluation = PersonEvaluations.get(i).evaluation;
			}
		}
		prediction = bestMatch.evaluation;
		System.out.println("Prediction for movie ID = " + movieId + " equals " + prediction);
		return prediction;
	}
	
	private String GetPredictionForBiggerK(int movieId, ArrayList<Evaluation> PersonEvaluations)
	{
		String prediction = "NULL";
		
		ArrayList<BestMatch> BestMatches = new ArrayList<BestMatch>();
		
		for(int i=0; i<PersonEvaluations.size(); i++)
		{
			int currentEvaluatedMovieId = PersonEvaluations.get(i).movieId;
			Comparator comparator = new Comparator(Movies.get(movieId-1), Movies.get(currentEvaluatedMovieId-1), Movies); 
			double similarityRate = comparator.GetMoviesSimilarityRate();
			
			if(i<k)
			{
				BestMatch bestMatch = new BestMatch(similarityRate, PersonEvaluations.get(i).evaluation);
				BestMatches.add(bestMatch);
			}
			else if(similarityRate > BestMatches.get(0).similarityRate)
			{
				BestMatch bestMatch = new BestMatch(similarityRate, PersonEvaluations.get(i).evaluation);
				BestMatches.remove(0);
				BestMatches.add(bestMatch);
			}
			
			Collections.sort(BestMatches, BestMatch.SimilarityRateComparator);
		}
		prediction = GetAverageEvaluation(BestMatches);
		System.out.println("Prediction for movie ID = " + movieId + " equals " + prediction);
		return prediction;
	}
	
	private void FillMissingEvaluation(int evaluationId, String evaluation)
	{
		for(int i=0; i<MissingEvaluations.size();i++)
		{
			if(MissingEvaluations.get(i).id == evaluationId)
			{
				MissingEvaluations.get(i).evaluation = evaluation;
				return;
			}
		}
	}
	
	private String GetAverageEvaluation(ArrayList<BestMatch> BestMatches)
	{
		int evaluationsSum = 0;
		
		for(int i=0; i<BestMatches.size(); i++)
		{
			evaluationsSum += Integer.parseInt(BestMatches.get(i).evaluation);
		}
		
		int averageEvaluation = (int) Math.round(evaluationsSum / BestMatches.size());
		
		return Integer.toString(averageEvaluation);
	}
	
//	private int GetEvaluationBySimilarityRate(double similarityRate)
//	{
//		int evaluation = 0;
//		
//		if(similarityRate < 0.166)
//		{
//			evaluation = 0;
//		}
//		else if(similarityRate >= 0.166 && similarityRate < 0.332)
//		{
//			evaluation = 1;
//		}
//		else if(similarityRate >= 0.332 && similarityRate < 0.498)
//		{
//			evaluation = 2;
//		}
//		else if(similarityRate >= 0.498 && similarityRate < 0.664)
//		{
//			evaluation = 3;
//		}
//		else if(similarityRate >= 0.664 && similarityRate < 0.83)
//		{
//			evaluation = 4;
//		}
//		else if(similarityRate >= 0.83 && similarityRate <= 1)
//		{
//			evaluation = 5;
//		}
//		return evaluation;
//	}
}
