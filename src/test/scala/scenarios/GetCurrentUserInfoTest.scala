package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class GetCurrentUserInfoTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8082")
  val formHeader = Map("content-type" -> "application/x-www-form-urlencoded")

  def paramMap = Map("username" -> "sang", "password" -> "123")

  object getCookie {
    val cookie = exec(
      http("test login")
        .post("/login")
        .headers(formHeader)
        .formParamMap(paramMap)
        .check(status is 200)
        .check(header("set-cookie").saveAs("cookies"))
    )
  }

//   this used to save variable, then the variable can pass to next exec
//  object getFirstId {
//    val firstId = exec(
//      http("test login")
//        .post("/login")
//        .headers(formHeader)
//        .formParamMap(paramMap)
//        .check(status is 200)
//        .check(jsonPath("$.firstId")
//          .transform(firstId => "firstId:"+firstId )
//          .saveAs("result firstId"))
//    )
//  }


  object getCurrent {
    val name = exec(
      http("test get current user name")
        .get("/currentUserName")
        .headers(formHeader)
        .header("Cookie", "${cookies}")
        .check(status is 200)
        .check(regex("江南").count.is(1))
    )
  }

  val scn = scenario("get current user name").exec(getCookie.cookie).exec(getCurrent.name)

  setUp(scn.inject(constantUsersPerSec(1) during (1 seconds)).protocols(httpConf))


}
