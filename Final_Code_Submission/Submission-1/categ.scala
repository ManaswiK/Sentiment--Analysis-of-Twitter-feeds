import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.sql.Row


val reviewdata = sc.textFile("hdfs://cshadoop1/user/sxs159231/project/Stocks.txt");


val lines = reviewdata.map{line => val parts= line.split("\\^")
(parts(1).toDouble, parts(0))
}


val splits = lines.randomSplit(Array(0.6, 0.4), seed = 11L)
val trainsplit = splits(0).cache()
val testsplit = splits(1)


val training = sqlContext.createDataFrame(trainsplit.collect()).toDF("label","text")
val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
val hashingTF = new HashingTF().setNumFeatures(500).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01)
val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, lr))



// Fit the pipeline to training documents.
val model = pipeline.fit(training)

// now we can optionally save the fitted pipeline to disk
model.write.overwrite().save("/tmp/spark-logistic-regression-model28")

// and load it back in during production
val sameModel = PipelineModel.load("/tmp/spark-logistic-regression-model28")

val test = sqlContext.createDataFrame(testsplit.collect()).toDF("actualclass","text")

var actual_predicted : List[(Double, Double)] = List()
var pred_upcnt=0 
var pred_downcnt=0
var actual_upcnt=0
var actual_downcnt=0
// Make predictions on test documents.
val predicted = model.transform(test).select("actualclass", "text", "probability", "prediction").collect().foreach { case Row(actualclass: Double, text: String, prob: Vector, prediction: Double) => 
    println(s"($text) --> prob=$prob, prediction=$prediction, actual=$actualclass")
    actual_predicted = actual_predicted :+((prediction,actualclass))
    if(prediction == 1) pred_upcnt = pred_upcnt + 1
    else pred_downcnt = pred_downcnt + 1
    if(actualclass == 1) actual_upcnt = actual_upcnt + 1
    else actual_downcnt = actual_downcnt + 1
    //println("a")
  }
 
//predicted.collect().foreach(x=>println(x))
val a_p = sc.parallelize(actual_predicted)


//Evaluation metrics
val metrics = new BinaryClassificationMetrics(a_p)
val auROC = metrics.areaUnderROC()
val auPR = metrics.areaUnderPR()
println("Area under ROC = " + auROC + "Area under PR = " + auPR)
println("Predicted Upcount: " + pred_upcnt + "Predicted Downcount: "+pred_downcnt)
println("Actual Upcount: " + actual_upcnt + "Actual Downcount: "+actual_downcnt)
println("================================================")
println("|               PREDICTION                     |")
println("================================================")
if(pred_upcnt>=pred_downcnt) println("|        The price is expected to go up        |") else println("|        The price is expected to go down      |")
println("================================================")