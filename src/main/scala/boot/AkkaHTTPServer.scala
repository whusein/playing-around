package boot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import interfaces.RestInterface


class AkkaHTTPServer extends RestInterface {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route: Route = orderRoutes

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)
}


