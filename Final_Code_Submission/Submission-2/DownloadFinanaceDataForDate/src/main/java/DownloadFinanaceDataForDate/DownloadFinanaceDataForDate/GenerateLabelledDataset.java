package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DownloadFinanaceDataForDate.DownloadFinanaceDataForDate.StockData;
import DownloadFinanaceDataForDate.DownloadFinanaceDataForDate.FetchStock;

public class GenerateLabelledDataset {

	public static void main(String[] args) throws ParseException, IOException {
		/*-----------------------------------------------------------------
		 * GenerateLabelledDataset class is used to retrieve the finance data from Yahoo API .
		 * Then it reads the twitter data and finance data and generates labeled data set.
		 * The labelled dataset is stored in a file "Stocks.txt".   
		 * ------------------------------------------------------------------*/

		List<StockData> facebook = new ArrayList<StockData>();
		// Fetching stocks of apple company (symbol - AAPL)
		facebook = FetchStock.getStock("AAPL");

		// Creating the output labelled data set "Stocks.txt"
		BufferedWriter bf = new BufferedWriter(new FileWriter("Stocks.txt"));
		int j = 6;
        int k=1;
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		cal.add(Calendar.DATE,-k);
		today=cal.getTime();
		String Date1=dateFormat.format(today.getTime());
		Date1=Date1.trim();
        String Date2="";
		System.out.println("Reading twitter data collected from downloading tweets....");
		for (int i = 0; i < facebook.size(); ) {

			String line = "";
			String tweet_line = "";
			Date date= facebook.get(i).getDate();
			Date2=dateFormat.format(date.getTime());
			//System.out.println("Date:fectchedDate"+Date1+":"+Date2);
			if(!Date2.trim().equals(Date1))
			{
				System.out.println("NOT EQUAL");
				cal.add(Calendar.DATE,-k);
				today=cal.getTime();
				Date1=dateFormat.format(today.getTime());
				j--;
				continue;
			}
			
			//System.out.println("considering Tweet file:"+j);
			line += facebook.get(i).getOpenPrice() + ":";
			line += facebook.get(i).getHigh() + ":";
			line += facebook.get(i).getLow() + ":";
			line += facebook.get(i).getClosePrice() + ":";
			line += facebook.get(i).getVolume() + ":";
			line += facebook.get(i).getAdjClose() + "\n";
			double diff = facebook.get(i).getClosePrice() - facebook.get(i).getOpenPrice();
			int res = 0;
			if (diff >= 0.0) {
				res = 1;
			}
			// Reading from the tweets files to collect twitter data
			BufferedReader b1 = new BufferedReader(new FileReader("./tweets" + j + "_aapl.txt"));
			while ((tweet_line = b1.readLine()) != null) {
				if (tweet_line.isEmpty() || tweet_line.trim().equals("") || tweet_line.trim().equals("\n"))
					continue;
				bf.write(tweet_line + "^" + res);
				bf.write("\n");
			}
			j--;
			b1.close();
			cal.add(Calendar.DATE,-k);
			today=cal.getTime();
			Date1=dateFormat.format(today.getTime());
			i++;
		}
		System.out.println("Completed generating Labelled Dataset \"Stocks.txt\" from twitter and finanace data....");
		bf.close();

	}

}
