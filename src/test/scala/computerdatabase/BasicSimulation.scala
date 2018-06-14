package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  // 
  // mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulation
  //
  // TODO Extract cookie and from/to-query params to vars or environment variables.
  // Could also use: https://gatling.io/docs/2.3/http/http_helpers/
  // 

  val httpConf = http
    .baseURL("https://testipaketti.pshp.fi/rest/orderer/v1") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("Login9F8E8C5DD064795235F642E90258B4662A49F0CAF882167920EE39AC5711890D3233353288", "Br20iOT0u7HPAlTyIgZUhrLV7Q7oAGhJ1X3GBz4dJHI=")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .exec(http("request_me")
      .get("/users/me"))
    .exec(http("request_orders")
      .get("/orders/?statuses=APPROVED,ACKNOWLEDGED,IN_PROGRESS,DELIVERED&scope=SCOPE_CONSUMER_UNIT&from=2018-05-16T21:00:00.000Z&to=2018-06-14T20:59:59.999Z"))
    .pause(1000 milliseconds)
    .exec(http("request_orders")
      .get("/orders/?statuses=APPROVED,ACKNOWLEDGED,IN_PROGRESS,DELIVERED&scope=SCOPE_CONSUMER_UNIT&from=2018-05-16T21:00:00.000Z&to=2018-06-14T20:59:59.999Z"))
    .pause(1000 milliseconds)
    .exec(http("request_orders")
      .get("/orders/?statuses=APPROVED,ACKNOWLEDGED,IN_PROGRESS,DELIVERED&scope=SCOPE_CONSUMER_UNIT&from=2018-05-16T21:00:00.000Z&to=2018-06-14T20:59:59.999Z"))
    .pause(1000 milliseconds)
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

  setUp(scn.inject(atOnceUsers(5)).protocols(httpConf))
}
