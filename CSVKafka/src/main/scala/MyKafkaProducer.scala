import java.util.{Properties, Random}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object MyKafkaProducer extends App {
  val Stop = "Stop"

  var counter = 0;

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("client.id", "ScalaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String,Any](props)

  val bufferedSource = io.Source.fromFile("/Users/akshayjain/Downloads/test.csv")
  for (line <- bufferedSource.getLines) {

    println("--------------------------")
    println(line);

    producer.send(new ProducerRecord[String,Any]("my_topic1",line))

    Thread.sleep(new Random().nextInt(2000)+1000)

  }
  bufferedSource.close

  producer.close()
}
