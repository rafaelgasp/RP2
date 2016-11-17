import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.jhenrique.model.Tweet;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.Query.Unit;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.setQuery("#TheVoiceBrasil");	
		query.since("2016-11-03");
		//query.until("2016-11-04");
		query.setGeoCode(new GeoLocation(-23.5489, -46.6388), 100, Query.KILOMETERS);
	
		ArrayList<Status> results = TwitterAPI.getTweets(query, 1000);
		//List<Tweet> results = TwitterAPI.getTweets("#TheVoiceBrasil", 1000);
		
		try {
			exportResults(results, "resultados_"+System.currentTimeMillis()+"_THEVOICE.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void exportResults(List<Tweet> results, String filename) throws IOException{
		System.out.println("Exporting tweets...");
		FileWriter fw = new FileWriter(filename);
		
		String header = "Tweet;Tradução;Sentimento;Favoritos;Retweets;Data;Location;" + System.getProperty("line.separator");
		fw.write(header);
		
		NLP.init();
		
		int i = 0;
		for (Tweet tweet : results){
			String t = Preprocessamento.preprocessar(tweet.getText());
			String translated = TwitterAPI.yandexTranslation(t);
			
			String line = t + ";";
			line += translated + ";";
			line += NLP.findSentiment(translated)+ ";";
			line += tweet.getFavorites() + ";";
			line += tweet.getRetweets() + ";";
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			line += format.format(tweet.getDate()) + ";";
			if(tweet.getGeo() != null){
				line += tweet.getGeo() + ";";
			}else{
				line += tweet.getUsername() + ";";
			}
			fw.write(line + System.getProperty("line.separator"));
			
			if( i % 25 == 0){
				System.out.println(i + "...");
			}
			i++;
		}
		
		fw.flush();
		fw.close();
	}
	
	public static void exportResults(ArrayList<Status> results, String filename) throws IOException{
		System.out.println("Exporting tweets...");
		FileWriter fw = new FileWriter(filename);
		
		String header = "Tweet;Tradução;Sentimento;Favoritos;Retweets;Data;Location;" + System.getProperty("line.separator");
		fw.write(header);
		
		NLP.init();
		
		int i = 0;
		for (Status tweet : results){
			String t = Preprocessamento.preprocessar(tweet.getText());
			String translated = TwitterAPI.yandexTranslation(t);
			
			String line = t + ";";
			line += translated + ";";
			line += NLP.findSentiment(translated)+ ";";
			line += tweet.getFavoriteCount() + ";";
			line += tweet.getRetweetCount() + ";";
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			line += format.format(tweet.getCreatedAt()) + ";";
			if(tweet.getGeoLocation() != null){
				line += tweet.getGeoLocation().getLatitude() + ", " + tweet.getGeoLocation().getLongitude() + ";";
			}else{
				line += tweet.getUser().getLocation() + ";";
			}
			fw.write(line + System.getProperty("line.separator"));
			
			if( i % 25 == 0){
				System.out.println(i + "...");
			}
			i++;
		}
		
		fw.flush();
		fw.close();
	}
}
