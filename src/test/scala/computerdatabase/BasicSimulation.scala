package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  // 
  // mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulation
  //
  // TODO Extract cookie and from/to-query params to vars or environment variables.
  // 

  val httpConf = http
    .baseURL("http://computer-database.gatling.io") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .header("LoginF599BE64D22EE753BB44CF49F11031A701543E0E968396D2FB657BD71192CE9A6443030978", "JXCdse1IxTAEic9dmRBGFKPaACpO+qK9G7hZ0hesITU=")

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .exec(http("request_me")
      .get("/users/me"))
    .exec(http("request_orders")
      .get("/orders/?statuses=APPROVED,ACKNOWLEDGED,IN_PROGRESS,DELIVERED&scope=SCOPE_CONSUMER_UNIT&from=2018-05-16T21:00:00.000Z&to=2018-06-14T20:59:59.999Z"))

  // val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
  //   .exec(http("request_1")
  //     .get("/"))
  //   .pause(7) // Note that Gatling has recorder real time pauses
  //   .exec(http("request_2")
  //     .get("/computers?f=macbook"))
  //   .pause(2)
  //   .exec(http("request_3")
  //     .get("/computers/6"))
  //   .pause(3)
  //   .exec(http("request_4")
  //     .get("/"))
  //   .pause(2)
  //   .exec(http("request_5")
  //     .get("/computers?p=1"))
  //   .pause(670 milliseconds)
  //   .exec(http("request_6")
  //     .get("/computers?p=2"))
  //   .pause(629 milliseconds)
  //   .exec(http("request_7")
  //     .get("/computers?p=3"))
  //   .pause(734 milliseconds)
  //   .exec(http("request_8")
  //     .get("/computers?p=4"))
  //   .pause(5)
  //   .exec(http("request_9")
  //     .get("/computers/new"))
  //   .pause(1)
  //   .exec(http("request_10") // Here's an example of a POST request
  //     .post("/computers")
  //     .formParam("""name""", """Beautiful Computer""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
  //     .formParam("""introduced""", """2012-05-30""")
  //     .formParam("""discontinued""", """""")
  //     .formParam("""company""", """37"""))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
