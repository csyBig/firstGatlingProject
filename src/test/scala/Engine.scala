import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import scenarios.{GetCurrentUserInfoTest, LoginTest}

object Engine extends App {

  val simClass = classOf[GetCurrentUserInfoTest].getName

  val props = new GatlingPropertiesBuilder()
    .simulationClass(simClass)
    .resourcesDirectory(IDEPathHelper.resourcesDirectory.toString)
    .resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    .binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)
  Gatling.fromMap(props.build)
}
