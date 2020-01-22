/*
 * Copyright 2011-2019 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DemoSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io/computers")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  object UserFunctionality {
    val check = exec(http("user requests").get("/365")).pause(5)
  }

  object AdminFunctionality {
    val check = exec(http("admin requests").get("/1")).pause(5)
  }

  val users = scenario("Users").exec(UserFunctionality.check)
  val admins = scenario("Admins").exec(UserFunctionality.check, AdminFunctionality.check)

  // linear rampup of 10 users added over 5 seconds (1 extra user every 500 ms)
  setUp(
    users.inject(rampUsers(10) during (5 seconds)),
    admins.inject(rampUsers(2) during (5 seconds))
  ).protocols(httpProtocol)

}
