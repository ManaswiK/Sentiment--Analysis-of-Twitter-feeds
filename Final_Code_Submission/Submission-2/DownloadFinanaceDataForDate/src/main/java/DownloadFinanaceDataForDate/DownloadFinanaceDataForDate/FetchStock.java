package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchStock {

	/*-----------------------------------------------------------------
	 * Fetch Stock class is used to fetch the finance data from Yahoo API .
	 * Then parse and store the data in StockData class object  
	 * ------------------------------------------------------------------*/
	static List<StockData> getStock(String symbol) throws ParseException {

		// symbol is the symbol of the company for which stocks need to be
		// fetched
		String sym = symbol.toUpperCase();
		double open = 0.0;
		double close = 0.0;
		double high = 0.0;
		double low = 0.0;
		int volume = 0;
		double adjClose = 0.0;
		Date date = new Date();
		try {

			// Extracting the CSV File containing finance data from yahoo
			// finance API
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			int endMonth = cal.get(Calendar.MONTH);
			int endDay = cal.get(Calendar.DAY_OF_MONTH);
			int endYear = cal.get(Calendar.YEAR);
			cal.add(Calendar.DATE,-7);
			Date sevenDaysBeforeDate=cal.getTime();
			int startMonth = cal.get(Calendar.MONTH);
			int startDay = cal.get(Calendar.DAY_OF_MONTH);
			int startYear = cal.get(Calendar.YEAR);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
			String endDate=dateFormat.format(today.getTime());
			String startDate=dateFormat.format(sevenDaysBeforeDate.getTime());
			//System.out.println("startmonth:"+startMonth+":"+startDay+":"+startYear);
			//System.out.println("end month:"+endMonth+":"+endDay+":"+endYear);
			System.out.println("Collecting finanace data for dates ranging From: " + startDate + " To: "
					+ endDate + "\n");
			URL yahoo = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + symbol
					+ "&d="+endMonth+"&e="+endDay+"&f="+endYear+"&g=d&a="+startMonth+"&b="+startDay+"&c="+startYear+"&ignore=.csv");
			URLConnection connection = yahoo.openConnection();
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);
			List<StockData> stock = new ArrayList<StockData>();
			br.readLine();
			String line = null;
			while ((line = br.readLine()) != null) {
				// Parsing the CSV file and extracting the stock information
				// from it and storing it..
				String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				//System.out.println("line:"+line);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				date = formatter.parse(stockinfo[0]);
				open = Double.parseDouble(stockinfo[1]);
				high = Double.parseDouble(stockinfo[2]);
				low = Double.parseDouble(stockinfo[3]);
				close = Double.parseDouble(stockinfo[4]);
				volume = Integer.parseInt(stockinfo[5]);
				adjClose = Double.parseDouble(stockinfo[6]);
				stock.add(new StockData(open, close, high, low, volume, adjClose, date));

			}
			return stock;

		} catch (IOException e) {
			System.out.println("IO Exception:" + e);
			return null;
		}

	}

}
