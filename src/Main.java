import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
		query.setQuery("TheVoiceBrasil");	
		query.since("2016-10-26");
		query.until("2016-10-28");
		query.count(100);
		query.resultType(ResultType.recent);
	
		ArrayList<Status> results = TwitterAPI.getTweets(query);
		HashSet<Status> resultSet = new HashSet<Status>(results);
		
		try {
			exportResults(resultSet, "resultados_RECENT_THEVOICEBR.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void exportResults(HashSet<Status> results, String filename) throws IOException{
		System.out.println("Exporting tweets...");
		FileWriter fw = new FileWriter(filename);
		
		String header = "Tweet;Tradução;Sentimento;Favoritos;Retweets;Data;Location;" + System.getProperty("line.separator");
		fw.write(header);
		
		NLP.init();
		
		int i = 0;
		for (Status tweet : results){
			String t = Preprocessamento.extrairInfo(tweet.getText());
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
