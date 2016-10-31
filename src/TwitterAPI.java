import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

public class TwitterAPI {
	private static final String TWITTER_CONSUMER_KEY = "di1tNQeO3Evfr0WVtM6G7rhLk";
	private static final String TWITTER_SECRET_KEY = "yBdmCqtEvJTjGvv5oxXDqAoL3Me8LazVuDTqao3sI5aQHhgnGV";
	private static final String TWITTER_ACCESS_TOKEN = "193012190-gtmI9EepYjEhNL7y6mpjHfm0g4XfdBetUnt69jW7";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "NS8CILBytfUXrNpFoRbnaPhxvP87PoDlBculDDZ3kpNsC";

	public static String transltrTranslation(String text) {
	  HttpURLConnection connection = null;

	  try {
	    //Create connection
		String parameters = "text="+URLEncoder.encode(text, "UTF-8")+"&to=en&from=pt";
	    URL url = new URL("http://www.transltr.org/api/translate?"+parameters);
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Accept", 
	        "application/json");	    
	    connection.setUseCaches(false);
	    connection.setDoOutput(true);

	    //Get Response  
	    InputStream is = connection.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
	    String line;
	    while ((line = rd.readLine()) != null) {
	      response.append(new String(line.getBytes(), "UTF-8"));
	      response.append('\n');
	    }
	    rd.close();
	    String resp = response.toString();
	    System.out.println(resp.length());
	    System.out.println(resp);
	    return response.toString();
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  } finally {
	    if (connection != null) {
	      connection.disconnect();
	    }
	  }
	}
	
	public static String yandexTranslation(String text){
		Translate.setKey("trnsl.1.1.20161011T185140Z.c71d1c17ed00525a.d081564173bc99c47fb6b5211567c5ccd26160bc");

        String translatedText = null;
		try {
			translatedText = Translate.execute(text, Language.PORTUGUESE, Language.ENGLISH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return translatedText;
	}
	
	public static ArrayList<Status> getTweets(Query query){
		System.out.println("Getting tweets...");
		
		ArrayList<Status> tweets = new ArrayList<Status>();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		try {
		    QueryResult result;
		    do {
		        result = twitter.search(query);
		        List<Status> list = result.getTweets();
		        tweets.addAll(list);
		        System.out.println(tweets.size() + "...");
		    } while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to search tweets: " + te.getMessage());
		    return null;
		}
		
		return tweets;
	}
	
	public static void getTweetStream(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		
		StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) {
	            //if (status.getText().contains)
	            if(status.getUser().getLang().equalsIgnoreCase("pt")
	                    || status.getUser().getLang().equalsIgnoreCase("pt_BR")) {
	                System.out.println(status.getUser().getName() + " :: " + status.getText() + " :: " + status.getGeoLocation());
	            }
	        }
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
	        public void onScrubGeo(long arg0, long arg1) {

	        }
	        public void onStallWarning(StallWarning arg0) {

	        }
	    };
		
	    twitterStream.addListener(listener);
	    FilterQuery query = new FilterQuery();
	    // São Paulo BR
	    double lat = -23.5;
	    double lon = -46.6;

	    double lon1 = lon - .5;
	    double lon2 = lon + .5;
	    double lat1 = lat - .5;
	    double lat2 = lat + .5;

	    double box[][] = {{lon1, lat1}, {lon2, lat2}};

	    query.locations(box);

	    String[] trackArray = {"The Voice Brasil"}; 
	    query.track(trackArray);
	    twitterStream.filter(query);
	}
}
