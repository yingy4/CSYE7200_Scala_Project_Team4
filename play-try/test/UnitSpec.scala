import akka.actor.ActorSystem
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test.FakeRequest
import services.Counter
import Utils.ApplicationUtils._

/**
 * Unit tests can run without a full Play application.
 */
class UnitSpec extends PlaySpec {


  "SparkContext" should {

    "match sparkStreamingContext AppName" in {

      ssc.sparkContext.appName must equal("MySparkStreaming")

    }

    "match sparkStreamingContext current execution status" in {

      ssc.sparkContext.isStopped must equal(false)

    }

    "match sparkStreamingContext isLocal status" in {

      ssc.sparkContext.isLocal must equal(true)

    }

    "match sparkStreamingContext version" in {
      ssc.sparkContext.version must equal("2.3.0")
    }


  }

  "CleanDataFunction" should
    {
      "return a cleaned row" in {

        val answer = cleanDataFunction("1005972,P00214142,F,26-35,20,B,0,0,8,,5,5100");

        answer must equal("1005972,P00214142,F,26-35,20,B,0,0,8,EMPTY,5,5100")

      }
    }

  "Data buffers" should
    {
      "size of bufferList must be 0" in {

        val bufferListSize = bufferList.size

        bufferListSize must equal (0)

      }

      "size of productCategory must be 0" in {

        val productCategorySize = productCategory.size

        productCategorySize must equal (0)

      }
    }

  "Kafka Topic" should
    {
      "must match csvTopic" in {

        val answer = TOPIC

        answer must equal("csvTopic")

      }
    }

  "Kafka Parameters" should {
    "match bootstrap.servers" in {

      val bootstrapProps = props.get("bootstrap.servers")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("localhost:9092")

    }

    "match metadata.broker.list" in {

      val bootstrapProps = props.get("metadata.broker.list")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("localhost:9092")

    }

    "match serializer.class" in {

      val bootstrapProps = props.get("serializer.class")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("kafka.serializer.StringEncoder")

    }

    "match value.serializer" in {

      val bootstrapProps = props.get("value.serializer")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("org.apache.kafka.common.serialization.StringSerializer")

    }

    "match value.deserializer" in {

      val bootstrapProps = props.get("value.deserializer")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("org.apache.kafka.common.serialization.StringDeserializer")

    }

    "match key.serializer" in {

      val bootstrapProps = props.get("key.serializer")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("org.apache.kafka.common.serialization.StringSerializer")

    }

    "match key.deserializer" in {

      val bootstrapProps = props.get("key.deserializer")

      val answer = bootstrapProps match {
        case Some(bootstrapServer) => bootstrapServer
        case _ => ""
      }

      answer must equal ("org.apache.kafka.common.serialization.StringDeserializer")

    }




  }




}
