package Utils

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object ApplicationUtils {


  /**
    * Defining Spark Streaming Context. 1 instance of streaming context per application.
    * Fetching the data every 2 seconds
    */
  val ssc = new StreamingContext(new SparkConf().setAppName("MySparkStreaming").setMaster("local[2]"), Seconds(2))


  /**
    * Method to clean the data, remove the unwanted blank/white spaces and replace them with 'EMPTY'
    * @param inputRow: Raw input data
    * @return Cleaned data without any empty spaces
    */
  def cleanDataFunction(inputRow:String) = {

    val dirtyRow = inputRow.replaceAll(",,", ",EMPTY,")

    val cleanedRow = if (dirtyRow.charAt(dirtyRow.length() - 1) == ',') dirtyRow + "EMPTY" else dirtyRow

    cleanedRow
  }

  val bufferList = new ListBuffer[String]()

}
