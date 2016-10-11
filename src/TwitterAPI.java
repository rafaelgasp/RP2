import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
	
	public static void twitter(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {
		    Query query = new Query("Palmeiras");
		    QueryResult result;
		    do {
		        result = twitter.search(query);
		        List<Status> tweets = result.getTweets();
		        for (Status tweet : tweets) {
		            //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
		        }
		    } while ((query = result.nextQuery()) != null);
		    System.exit(0);
		} catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to search tweets: " + te.getMessage());
		    System.exit(-1);
		}
	}
	
	public static void main(String[] args){
		System.out.println(transltrTranslation("coala bonito"));
		
		
	}
}
