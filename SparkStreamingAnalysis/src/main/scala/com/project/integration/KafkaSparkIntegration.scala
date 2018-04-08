package com.project.integration

import kafka.serializer.StringDecoder

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object KafkaSparkIntegration extends App {

  case class SalesInputData(user_id: Int,product_id: String, gender: String, age: String, occupation: String, city: String, city_tenure: String, marital_status: String, product_category1: String, product_category2: String, product_category3: String)

  case class SalesTransformedData(user_id: Int,product_id: String, gender: String, age: String, occupation: String, city: String, city_tenure: String, marital_status: String, product_category1: String, product_category2: String, product_category3: String)



  val ssc = new StreamingContext(new SparkConf().setAppName("MySparkStreaming").setMaster("local[2]"), Seconds(2))

  val topicStream = createKafkaStream(ssc,"my_topic1","localhost:9092").map(_._2)

  val eachRow = topicStream.foreachRDD{
    rdd => rdd.foreach{
      row => {
        val dirtyRow = row.replaceAll(",,",",EMPTY,")

        val cleanedRow = if(dirtyRow.charAt(dirtyRow.length()-1) == ',') dirtyRow+"EMPTY" else dirtyRow

       val input = cleanedRow.split(",")

        //print(Some(SalesInputData(input(0).toInt,input(1),input(2),input(3),input(4),input(5),input(6),input(7),input(8),input(9),input(10))))
        val inputInCaseClass = Some(SalesInputData(input(0).toInt,input(1),input(2),input(3),input(4),input(5),input(6),input(7),input(8),input(9),input(10)))
        println(inputInCaseClass)

//        inputInCaseClass.map{
//          case (user_id,product_id,_,_,_,_,_,_,_,_,_) => (product_id,user_id)
//        }.reduceByKey(_ + 1)

      }
    }
  }


  val dataInRDD = topicStream


  def createKafkaStream(ssc: StreamingContext, kafkaTopics: String, brokers: String): DStream[(String, String)] = {
    val topicsSet = kafkaTopics.split(",").toSet
    val props = Map(
      "bootstrap.servers" -> "localhost:9092",
      "metadata.broker.list" -> "localhost:9092",
      "serializer.class" -> "kafka.serializer.StringEncoder",
      "value.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
    )
    KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, props, topicsSet)
  }


  ssc.start()
  ssc.awaitTermination()
}
