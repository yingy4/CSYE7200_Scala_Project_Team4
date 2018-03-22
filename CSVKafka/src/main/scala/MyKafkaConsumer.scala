

import java.util.concurrent.{ExecutorService, Executors}
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}

class MyKafkaConsumer (val topic: String){

  val props  = createConsumerConfig()
  val consumer = new KafkaConsumer[String,Any](props)

  var executor: ExecutorService = null
  def shutdown() = {
    if (consumer != null)
      consumer.close();
    if (executor != null)
      executor.shutdown();
  }

  def createConsumerConfig(): Properties ={
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "1000")
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props
  }

  def run() = {
    consumer.subscribe(Collections.singletonList(this.topic))
    Executors.newSingleThreadExecutor.execute(    new Runnable {
      override def run(): Unit = {
        while (true) {
          val records = consumer.poll(1000)

          records.forEach(record => println(record.value()));

        }
      }
    })
  }

}

object MyKafkaConsumer extends App {
  val example = new MyKafkaConsumer("my_topic")
  example.run()
}
