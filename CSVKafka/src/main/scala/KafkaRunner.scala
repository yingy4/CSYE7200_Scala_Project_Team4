import org.apache.kafka.clients.producer.{ProducerRecord}

import java.util.{Random}

object KafkaRunner extends App {

    val bufferedSource = io.Source.fromFile("/Users/akshayjain/Downloads/test.csv")
    for (line <- bufferedSource.getLines) {

      println("--------------------------")
      println(line);

      MyKafkaProducer.producer.send(new ProducerRecord[String,Any](MyKafkaProducer.TOPIC,line))
      Thread.sleep(scala.util.Random.nextInt(3000))

    }
    bufferedSource.close

  MyKafkaProducer.producer.close()
}
