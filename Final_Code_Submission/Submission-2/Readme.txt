The Submission-2 directory consists of all the code to download the tweets of the past seven days and then download the corresponding finance data. Then this data has to be labelled and provide it as an input to the scala file. Step by step instructions are as follows.


1)The following code is to Download tweets for the past 7days from the current day:
 hadoop jar ./DownloadFinanaceDataForDate-0.0.1-SNAPSHOT-jar-with-dependencies.jar DownloadFinanaceDataForDate.DownloadFinanaceDataForDate.TweetDownloader

 downloads the tweets for the past seven days for the search keyword apple. and stores them in a files.


2)Downloads Finance data from the yahoo finance and generates labelled dataset:

hadoop jar ./DownloadFinanaceDataForDate-0.0.1-SNAPSHOT-jar-with-dependencies.jar DownloadFinanaceDataForDate.DownloadFinanaceDataForDate.GenerateLabelledDataset

generates the labelled dataset "Stocks.txt" from yahoo finanace data and twitter data.

3)upload it to hdfs :

 hadoop jar ./DownloadFinanaceDataForDate-0.0.1-SNAPSHOT-jar-with-dependencies.jar DownloadFinanaceDataForDate.DownloadFinanaceDataForDate.FileCopyWithProgress ./Stocks.txt hdfs://cshadoop1/user/axk150431/Project/

 
 source and destination folders are to be provided as command line arguments..
 stocks file is now uploaded onto hdfs
 
 4)
 run scala file using the shell script with the help of the following command:

 sh project1.sh hdfs://cshadoop1/user/axk150431/Project/Stocks.txt

The scala file performs sentiment analysis and predicts whether the stocks might go up or down. The hdfs path to the Stocks.txt file is to be provided as an argument in the command line.
 
