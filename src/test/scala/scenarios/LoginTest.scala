package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoginTest extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8082")
  val formHeader = Map("content-type" -> "application/x-www-form-urlencoded")

  def paramMap = Map("username" -> "sang", "password" -> "123")

  object login {
    val token = exec(
      http("test login")
        .post("/login")
        .headers(formHeader)
        .formParamMap(paramMap)
        .check(status is 200)
    )
  }

  val scn = scenario("login vue blog").exec(login.token)

  setUp(scn.inject(constantUsersPerSec(1) during (1 seconds)).protocols(httpConf))


}
