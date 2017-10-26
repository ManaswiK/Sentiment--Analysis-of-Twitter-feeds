The available code is what we used to download the tweets and finance data, for one week. 

The DownloadFinanaceDataForDate directory contains the entire code. The TweetDownloader.java in the main folder of the package was used to download the tweets for the past week using the twitter4j api. 

The FetchStock.java and StockData.java directory was used to fetch and return the yahoo finance data using the yahoo finance api.

The GenerateLabelledDataset.java directory was used to label the tweets into positive  or negative based on the finance data. This labelled data called Stocks.txt is provided as the input to the scala file. 

Finally the categ.scala is the scala file used to predict the tweets based on the training dataset provided.

So the entire process can be easily tested using the shell script provided. The following are the instructions to do so.   
 
   1) Run the scala code using the shell script. 

           sh project.sh

   2) This scala code is hardcoded to a path to Stocks.txt already available in the hdfs directory, just to eliminate the overhead of downloading the tweets.


** If at all there is a problem in running the shell script due to security restrictions in the hdfs then the Stocks.txt file is included in the DownloadFinanaceDataForDate directory. Upload it into your hdfs and change the path in the scala file to your hdfs directory and then try running it. 