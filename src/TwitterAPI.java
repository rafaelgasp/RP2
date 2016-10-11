import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

public class TwitterAPI {
	private static final String TWITTER_CONSUMER_KEY = "di1tNQeO3Evfr0WVtM6G7rhLk";
	private static final String TWITTER_SECRET_KEY = "yBdmCqtEvJTjGvv5oxXDqAoL3Me8LazVuDTqao3sI5aQHhgnGV";
	private static final String TWITTER_ACCESS_TOKEN = "193012190-gtmI9EepYjEhNL7y6mpjHfm0g4XfdBetUnt69jW7";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "NS8CILBytfUXrNpFoRbnaPhxvP87PoDlBculDDZ3kpNsC";

	public static void main(String[] args){
		Translate.setKey("trnsl.1.1.20161011T185140Z.c71d1c17ed00525a.d081564173bc99c47fb6b5211567c5ccd26160bc");

        String translatedText = null;
		try {
			translatedText = Translate.execute("Hola, mundo!", Language.SPANISH, Language.PORTUGUESE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println(translatedText);
		
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
}
