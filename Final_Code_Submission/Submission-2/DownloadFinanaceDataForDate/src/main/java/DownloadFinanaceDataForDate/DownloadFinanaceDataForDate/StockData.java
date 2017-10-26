package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

import java.util.Date;

public class StockData {
	/*-----------------------------------------------------------------
	 * Stock Data class is used to store the fetched finance data from Yahoo API .
	 * ------------------------------------------------------------------*/

	private double open;
	private double close;
	private double high;
	private double low;
	private int volume;
	private double adjClose;
	private Date date;

	public StockData(double open, double close, double high, double low, int volume, double adjClose, Date date) {
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.high = high;
		this.low = low;
		this.adjClose = adjClose;
		this.date = date;

	}

	// This method is used to get opening price of the stock for the day.
	public double getOpenPrice() {
		return this.open;
	}

	// This method is used to get the date on which stock is extracted
	public Date getDate() {
		return this.date;
	}

	// This method is used to extract closing price of the stock for the day.
	public double getClosePrice() {
		return this.close;
	}

	// This method is used to extract maximum price of the stock for the day.
	public double getHigh() {
		return this.high;
	}

	// This method is used to extract minimum price of the stock for the day.
	public double getLow() {
		return this.low;
	}

	// This method is used to extract volume of stocks for the day.
	public int getVolume() {
		return this.volume;
	}

	// This method is used to extract adjusted stock price for the day.
	public double getAdjClose() {
		return this.adjClose;
	}

}
