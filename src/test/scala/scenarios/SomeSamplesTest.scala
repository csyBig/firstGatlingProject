package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class SomeSamplesTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8082")
  val formHeader = Map("content-type" -> "application/x-www-form-urlencoded")

  def paramMap = Map("username" -> "sang", "password" -> "123")


  // feeder
  object getResultFromFeeder {
    val feeder = csv("filename.csv").random

    val result = feed(feeder)
      .exec(
        http("test get current user name")
          .get("/currentUserName")
          .headers(formHeader)
          .header("Cookie", "${cookies}")
          .check(status is 200)
          .check(regex("江南").count.is(1))
      )
  }


  //




  val scn = scenario("get current user name").exec(getResultFromFeeder.result)

  setUp(scn.inject(constantUsersPerSec(1) during (1 seconds)).protocols(httpConf))


}
