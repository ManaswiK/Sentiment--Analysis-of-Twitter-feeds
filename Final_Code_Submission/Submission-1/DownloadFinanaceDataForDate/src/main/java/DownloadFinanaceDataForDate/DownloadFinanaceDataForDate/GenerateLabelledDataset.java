package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
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
		int j = 8;
		for (int i = 0; i < facebook.size() - 1; i++) {
			String line = "";
			String tweet_line = "";
			line += facebook.get(i).getDate() + ":";
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

		}
		bf.close();

	}

}
