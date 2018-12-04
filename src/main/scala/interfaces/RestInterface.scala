package interfaces

import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import resources.OrderResources
import services.OrderServices

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val orderService = new OrderServices

  val routes: Route = orderRoutes

}

trait Resources extends OrderResources