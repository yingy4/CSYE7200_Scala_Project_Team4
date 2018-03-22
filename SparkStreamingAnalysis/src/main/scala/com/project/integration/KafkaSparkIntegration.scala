package com.project.integration

import kafka.serializer.StringDecoder

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object KafkaSparkIntegration extends App {

  val ssc = new StreamingContext(new SparkConf().setAppName("MySparkStreaming").setMaster("local[2]"), Seconds(2))

  val topicStream = createKafkaStream(ssc,"my_topic1","localhost:9092").print()


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
