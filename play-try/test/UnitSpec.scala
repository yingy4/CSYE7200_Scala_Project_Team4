import akka.actor.ActorSystem
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test.FakeRequest
import services.Counter
import Utils.ApplicationUtils.{cleanDataFunction,ssc}

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

          val answer = cleanDataFunction("1005972,P00214142,F,26-35,20,B,0,0,8,,");

          answer must equal("1005972,P00214142,F,26-35,20,B,0,0,8,EMPTY,EMPTY")

        }
      }



}
