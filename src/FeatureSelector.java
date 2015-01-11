import java.util.ArrayList;

/*The result was:
	popularity: 	426305.5
	runtime: 	421660
	voteCount: 	420281.5
	voteAverage: 	420191.5
	releaseDate: 	389452.5
	budget: 	345143.5
	revenue: 	284810
	productionCountriesIso: 	273633
	collectionId:	261462
	spokenLanguagesIso: 	251679
	genresIds: 	141399
	productionCompaniesIds: 	8526.5
	castIds: 	4164.5
	directingIds: 	4013
	productionIds: 	2843
	soundIds: 	2324
	cameraIds: 	2032.5
	writingIds: 	1977
	editingIds: 	1765
	artIds: 	755
	crewIds: 	25.5
	costumeMakeUpIds: 	0
	visualEffectsIds: 	0

It may seem strange that features like genre, directing are not the best, but it has to be noticed, that our algorithms for comparison those features are not sophisticated*/

public class FeatureSelector {

	ArrayList<Movie> Movies;
	ArrayList<Evaluation> Evaluations;

	ArrayList<Evaluation> Evaluations1;
	ArrayList<Evaluation> Evaluations2;
	ArrayList<Evaluation> Evaluations3;
	ArrayList<Evaluation> Evaluations4;
	ArrayList<Evaluation> Evaluations5;
	
	double collectionIdSimilarityCount = 0;
	double budgetSimilarityCount = 0;
	double genresIdsSimilarityCount = 0;
	double popularitySimilarityCount = 0;
	double productionCompaniesIdsSimilarityCount = 0;
	double productionCountriesIsoSimilarityCount = 0;
	double releaseDateSimilarityCount = 0;
	double revenueSimilarityCount = 0;
	double runtimeSimilarityCount = 0;
	double spokenLanguagesIsoSimilarityCount = 0;
	double voteAverageSimilarityCount = 0;
	double voteCountSimilarityCount = 0;
	double castIdsSimilarityCount = 0;
	double costumeMakeUpIdsSimilarityCount = 0;
	double directingIdsSimilarityCount = 0;
	double cameraIdsSimilarityCount = 0;
	double editingIdsSimilarityCount = 0;
	double productionIdsSimilarityCount = 0;
	double soundIdsSimilarityCount = 0;
	double writingIdsSimilarityCount = 0;
    double artIdsSimilarityCount = 0;
    double crewIdsSimilarityCount = 0;
    double visualEffectsIdsSimilarityCount = 0;
	
	FeatureSelector(ArrayList<Movie> Movies, ArrayList<Evaluation> Evaluations) 
	{
		this.Movies = Movies;
		this.Evaluations = Evaluations;
	}
	
	public void Choose10BestFeatures()
	{
		for(int i=5; i<1817; i++)
		{
			ArrayList<Evaluation> PersonEvaluations = GetPersonEvaluations(i, Evaluations);
			
			if(PersonEvaluations.size() > 0)
			{
				System.out.println("Person " + i);
				
				SortEvaluations(PersonEvaluations);
				
				System.out.println("Evaluation set 1");
				CalculateFeatureInfluenceOnMoviesSimilarity(Evaluations1);
				System.out.println("Evaluation set 2");
				CalculateFeatureInfluenceOnMoviesSimilarity(Evaluations2);
				System.out.println("Evaluation set 3");
				CalculateFeatureInfluenceOnMoviesSimilarity(Evaluations3);
				System.out.println("Evaluation set 4");
				CalculateFeatureInfluenceOnMoviesSimilarity(Evaluations4);
				System.out.println("Evaluation set 5");
				CalculateFeatureInfluenceOnMoviesSimilarity(Evaluations5);
			}
		}
		PrintFeaturesInfluenceRate();
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
	
	private void CalculateFeatureInfluenceOnMoviesSimilarity(ArrayList<Evaluation> EvaluationsSet)
	{
		if(EvaluationsSet.size() > 1) //if there is only 1 movie, it is not similar to any other
		{
			if(EvaluationsSet.size() == 2)
			{
				System.out.println("2 evaluations");
				
				Movie movie1 = Movies.get(EvaluationsSet.get(0).movieId - 1);
				Movie movie2 = Movies.get(EvaluationsSet.get(1).movieId - 1);
				
				CountSimilarities(movie1, movie2);
			}
			else
			{
				System.out.println("More evaluations");
				for(int i=0;i<EvaluationsSet.size();i++)
				{
					System.out.println(i);
					
					if(i != EvaluationsSet.size() - 1) //there is no sense in comparing the last element, as it has already been compared with every previous element
					{
						Movie movie1 = Movies.get(EvaluationsSet.get(i).movieId - 1);
							
						for(int j=i+1;j<EvaluationsSet.size();j++)
						{
							System.out.println("Comparing with " + j);
							Movie movie2 = Movies.get(EvaluationsSet.get(j).movieId - 1);
								
							CountSimilarities(movie1, movie2);
						}
					}
				}
			}
		}
		else
		{
			System.out.println("No evaluations");
		}
	}
	
	private void SortEvaluations(ArrayList<Evaluation> PersonEvaluations)
	{
		Evaluations1 = new ArrayList<Evaluation>();
		Evaluations2 = new ArrayList<Evaluation>();
		Evaluations3 = new ArrayList<Evaluation>();
		Evaluations4 = new ArrayList<Evaluation>();
		Evaluations5 = new ArrayList<Evaluation>();
		
		for(int i=0;i<PersonEvaluations.size();i++)
		{
			if(PersonEvaluations.get(i).evaluation.equals("1"))
			{
				Evaluations1.add(PersonEvaluations.get(i));
			}
			else if(PersonEvaluations.get(i).evaluation.equals("2"))
			{
				Evaluations2.add(PersonEvaluations.get(i));
			}
			else if(PersonEvaluations.get(i).evaluation.equals("3"))
			{
				Evaluations3.add(PersonEvaluations.get(i));
			}
			else if(PersonEvaluations.get(i).evaluation.equals("4"))
			{
				Evaluations4.add(PersonEvaluations.get(i));
			}
			else if(PersonEvaluations.get(i).evaluation.equals("5"))
			{
				Evaluations5.add(PersonEvaluations.get(i));
			}
		}
	}
	
	private void CountSimilarities(Movie movie1, Movie movie2)
	{
		Comparator comparator = new Comparator(movie1, movie2, Movies);
		comparator.CalculateSimilarityRatesForSeparateFeatures(false);
		
		//collectionId
		if(comparator.collectionSimilarityRate > 0 && comparator.collectionSimilarityRate < 0.5)
		{
			collectionIdSimilarityCount += 0.5;
		}
		else if(comparator.collectionSimilarityRate > 0.5)
		{
			collectionIdSimilarityCount +=1;
		}
		
	    //budget
		if(comparator.budgetSimilarityRate > 0 && comparator.budgetSimilarityRate < 0.5)
		{
			budgetSimilarityCount += 0.5;
		}
		else if(comparator.budgetSimilarityRate > 0.5)
		{
			budgetSimilarityCount +=1;
		}
		
	    //genresIds
		if(comparator.genresSimilarityRate > 0 && comparator.genresSimilarityRate < 0.5)
		{
			genresIdsSimilarityCount += 0.5;
		}
		else if(comparator.genresSimilarityRate > 0.5)
		{
			genresIdsSimilarityCount +=1;
		}
		
	    //popularity
		if(comparator.popularitySimilarityRate > 0 && comparator.popularitySimilarityRate < 0.5)
		{
			popularitySimilarityCount += 0.5;
		}
		else if(comparator.popularitySimilarityRate > 0.5)
		{
			popularitySimilarityCount +=1;
		}
		
	    //productionCompaniesIds
		if(comparator.productionCompaniesSimilarityRate > 0 && comparator.productionCompaniesSimilarityRate < 0.5)
		{
			productionCompaniesIdsSimilarityCount += 0.5;
		}
		else if(comparator.productionCompaniesSimilarityRate > 0.5)
		{
			productionCompaniesIdsSimilarityCount +=1;
		}
		
	    //productionCountriesIso
		if(comparator.productionCountriesSimilarityRate > 0 && comparator.productionCountriesSimilarityRate < 0.5)
		{
			productionCountriesIsoSimilarityCount += 0.5;
		}
		else if(comparator.productionCountriesSimilarityRate > 0.5)
		{
			productionCountriesIsoSimilarityCount +=1;
		}
		
	    //releaseDate
		if(comparator.releaseDateSimilarityRate > 0 && comparator.releaseDateSimilarityRate < 0.5)
		{
			releaseDateSimilarityCount += 0.5;
		}
		else if(comparator.releaseDateSimilarityRate > 0.5)
		{
			releaseDateSimilarityCount +=1;
		}
		
	    //revenue
		if(comparator.revenueSimilarityRate > 0 && comparator.revenueSimilarityRate < 0.5)
		{
			revenueSimilarityCount += 0.5;
		}
		else if(comparator.revenueSimilarityRate > 0.5)
		{
			revenueSimilarityCount +=1;
		}
		
	    //runtime
		if(comparator.runtimeSimilarityRate > 0 && comparator.runtimeSimilarityRate < 0.5)
		{
			runtimeSimilarityCount += 0.5;
		}
		else if(comparator.runtimeSimilarityRate > 0.5)
		{
			runtimeSimilarityCount +=1;
		}
		
	    //spokenLanguagesIso
		if(comparator.spokenLanguagesSimilarityRate > 0 && comparator.spokenLanguagesSimilarityRate < 0.5)
		{
			spokenLanguagesIsoSimilarityCount += 0.5;
		}
		else if(comparator.spokenLanguagesSimilarityRate > 0.5)
		{
			spokenLanguagesIsoSimilarityCount +=1;
		}
		
	    //voteAverage
		if(comparator.voteAverageSimilarityRate > 0 && comparator.voteAverageSimilarityRate < 0.5)
		{
			voteAverageSimilarityCount += 0.5;
		}
		else if(comparator.voteAverageSimilarityRate > 0.5)
		{
			voteAverageSimilarityCount +=1;
		}
		
	    //voteCount
		if(comparator.voteCountSimilarityRate > 0 && comparator.voteCountSimilarityRate < 0.5)
		{
			voteCountSimilarityCount += 0.5;
		}
		else if(comparator.voteCountSimilarityRate > 0.5)
		{
			voteCountSimilarityCount +=1;
		}
		
	    //castIds
		if(comparator.castSimilarityRate > 0 && comparator.castSimilarityRate < 0.5)
		{
			castIdsSimilarityCount += 0.5;
		}
		else if(comparator.castSimilarityRate > 0.5)
		{
			castIdsSimilarityCount +=1;
		}
		
	    //costumeMakeUpIds
		if(comparator.costumeMakeUpSimilarityRate > 0 && comparator.costumeMakeUpSimilarityRate < 0.5)
		{
			costumeMakeUpIdsSimilarityCount += 0.5;
		}
		else if(comparator.costumeMakeUpSimilarityRate > 0.5)
		{
			costumeMakeUpIdsSimilarityCount +=1;
		}
		
	    //directingIds
		if(comparator.directingSimilarityRate > 0 && comparator.directingSimilarityRate < 0.5)
		{
			directingIdsSimilarityCount += 0.5;
		}
		else if(comparator.directingSimilarityRate > 0.5)
		{
			directingIdsSimilarityCount +=1;
		}
		
	    //cameraIds
		if(comparator.cameraSimilarityRate > 0 && comparator.cameraSimilarityRate < 0.5)
		{
			cameraIdsSimilarityCount += 0.5;
		}
		else if(comparator.cameraSimilarityRate > 0.5)
		{
			cameraIdsSimilarityCount +=1;
		}
		
	    //editingIds
		if(comparator.editingSimilarityRate > 0 && comparator.editingSimilarityRate < 0.5)
		{
			editingIdsSimilarityCount += 0.5;
		}
		else if(comparator.editingSimilarityRate > 0.5)
		{
			editingIdsSimilarityCount +=1;
		}
		
	    //productionIds
		if(comparator.productionSimilarityRate > 0 && comparator.productionSimilarityRate < 0.5)
		{
			productionIdsSimilarityCount += 0.5;
		}
		else if(comparator.productionSimilarityRate > 0.5)
		{
			productionIdsSimilarityCount +=1;
		}
		
	    //soundIds
		if(comparator.soundSimilarityRate > 0 && comparator.soundSimilarityRate < 0.5)
		{
			soundIdsSimilarityCount += 0.5;
		}
		else if(comparator.soundSimilarityRate > 0.5)
		{
			soundIdsSimilarityCount +=1;
		}
		
	    //writingIds
		if(comparator.writingSimilarityRate > 0 && comparator.writingSimilarityRate < 0.5)
		{
			writingIdsSimilarityCount += 0.5;
		}
		else if(comparator.writingSimilarityRate > 0.5)
		{
			writingIdsSimilarityCount +=1;
		}
		
	    //artIds
		if(comparator.artSimilarityRate > 0 && comparator.artSimilarityRate < 0.5)
		{
			artIdsSimilarityCount += 0.5;
		}
		else if(comparator.artSimilarityRate > 0.5)
		{
			artIdsSimilarityCount +=1;
		}
		
	    //crewIds
		if(comparator.crewSimilarityRate > 0 && comparator.crewSimilarityRate < 0.5)
		{
			crewIdsSimilarityCount += 0.5;
		}
		else if(comparator.crewSimilarityRate > 0.5)
		{
			crewIdsSimilarityCount +=1;
		}
		
	    //visualEffectsIds
		if(comparator.visualEffectsSimilarityRate > 0 && comparator.visualEffectsSimilarityRate < 0.5)
		{
			visualEffectsIdsSimilarityCount += 0.5;
		}
		else if(comparator.visualEffectsSimilarityRate > 0.5)
		{
			visualEffectsIdsSimilarityCount +=1;
		}
	}
	
	private void PrintFeaturesInfluenceRate()
	{
		//collectionId
		System.out.println("collectionId: " + collectionIdSimilarityCount);
		
	    //budget
		System.out.println("budget: " + budgetSimilarityCount);
		
	    //genresIds
		System.out.println("genresIds: " + genresIdsSimilarityCount);
		
	    //popularity
		System.out.println("popularity: " + popularitySimilarityCount);
		
	    //productionCompaniesIds
		System.out.println("productionCompaniesIds: " + productionCompaniesIdsSimilarityCount);
		
	    //productionCountriesIso
		System.out.println("productionCountriesIso: " + productionCountriesIsoSimilarityCount);
		
	    //releaseDate
		System.out.println("releaseDate: " + releaseDateSimilarityCount);
		
	    //revenue
		System.out.println("revenue: " + revenueSimilarityCount);
		
	    //runtime
		System.out.println("runtime: " + runtimeSimilarityCount);
		
	    //spokenLanguagesIso
		System.out.println("spokenLanguagesIso: " + spokenLanguagesIsoSimilarityCount);
		
	    //voteAverage
		System.out.println("voteAverage: " + voteAverageSimilarityCount);
		
	    //voteCount
		System.out.println("voteCount: " + voteCountSimilarityCount);
		
	    //castIds
		System.out.println("castIds: " + castIdsSimilarityCount);
		
	    //costumeMakeUpIds
		System.out.println("costumeMakeUpIds: " + costumeMakeUpIdsSimilarityCount);
		
	    //directingIds
		System.out.println("directingIds: " + directingIdsSimilarityCount);
		
	    //cameraIds
		System.out.println("cameraIds: " + cameraIdsSimilarityCount);
		
	    //editingIds
		System.out.println("editingIds: " + editingIdsSimilarityCount);
		
	    //productionIds
		System.out.println("productionIds: " + productionIdsSimilarityCount);
		
	    //soundIds
		System.out.println("soundIds: " + soundIdsSimilarityCount);
		
	    //writingIds
		System.out.println("writingIds: " + writingIdsSimilarityCount);
		
	    //artIds
		System.out.println("artIds: " + artIdsSimilarityCount);
		
	    //crewIds
		System.out.println("crewIds: " + crewIdsSimilarityCount);
		
	    //visualEffectsIds
		System.out.println("visualEffectsIds: " + visualEffectsIdsSimilarityCount);
	}
}
