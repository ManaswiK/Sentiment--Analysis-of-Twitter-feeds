package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetDownloader {
	/*-----------------------------------------------------------------
	 * TweetDownloader class is used to download tweets of the company apple (Symbol:AAPL) 
	 * for one week( from current day to past 7 days).
	 * ------------------------------------------------------------------*/

	public static void main(String args[]) {

		// Setting consumer key,consumer secret key, access token and secret
		// access token to get access to twitter4j API
		ConfigurationBuilder cf = new ConfigurationBuilder();
		cf.setDebugEnabled(true).setOAuthConsumerKey("i3h61waTNVfWAI1ySdjjJjpL5")
				.setOAuthConsumerSecret("apSYcGzQBJ1wOXBZlZxxqM5S3Ck441T0XWXZ180l3wFsNI1PfK")
				.setOAuthAccessToken("2755620841-aBvE10b2oLEVlH0NIWDOBix3fnrvCZGBcgcqu3S")
				.setOAuthAccessTokenSecret("gQpb2Af98x3E5FIktJ8qzsikvpNLfMOpao9a4oMo2VXHY");
		TwitterFactory tf = new TwitterFactory(cf.build());
		twitter4j.Twitter twitter = tf.getInstance();
		for (int i = 0; i < 7; i++) {
			// Searching for the query "AAPL"
			Query searchquery = new Query("AAPL");
			searchquery.setCount(100);

			// Get today's date
			Calendar today = Calendar.getInstance();
			Calendar fromdate = Calendar.getInstance();
			Calendar todate = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			fromdate.add(Calendar.DATE, -(i + 1));
			todate.add(Calendar.DATE, -i);

			// Setting dates for which search need to be performed.
			searchquery.setSince(dateFormat.format(fromdate.getTime()));
			searchquery.setUntil(dateFormat.format(todate.getTime()));

			System.out.println("From: " + (dateFormat.format(fromdate.getTime())) + " To: "
					+ (dateFormat.format(todate.getTime())) + "\n");
			try {
				QueryResult result = twitter.search(searchquery);
				File tweets1 = new File("tweets" + i + "_aapl.txt");
				tweets1.createNewFile();
				do {
					System.out.println("page");
					List<Status> tweets = result.getTweets();

					// creates a FileWriter Object
					FileWriter writer = new FileWriter(tweets1, true);
					// Writes the content to the file
					System.out.println("Retrieved " + tweets.size() + "tweets");
					for (Status st : tweets) {
						// Writing twitter text to a output file
						writer.write(st.getText() + "\n");
						writer.flush();
					}
					writer.close();
					if (result.hasNext()) {
						searchquery = result.nextQuery();
						result = twitter.search(searchquery);
					}
				} while (result.hasNext());
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
