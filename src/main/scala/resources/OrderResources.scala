package resources

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import services.OrderServices
import entities.{Order, OrderUpdate}
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.marshalling.{ToResponseMarshallable, ToResponseMarshaller}
import spray.json.DefaultJsonProtocol
import scala.concurrent.Future

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val orderFormat = jsonFormat6(Order)
  implicit val orderUpdateFormat = jsonFormat3(OrderUpdate)
}

trait OrderResources extends Directives with JsonSupport {

  val orderService: OrderServices

  def orderRoutes: Route = pathPrefix("orders") {
    pathEnd {
      post {
        entity(as[Order]) { order =>
          if(order.id < 0) throw new IllegalArgumentException("Order ID must not be 0")
          val saved: Future[Done] = orderService.createOrder(order)
          onComplete(saved) { done =>
            complete(s"order created:" + done.toString)
          }
        }
      } ~
      get {
        complete(ToResponseMarshallable(orderService.getOrders))
      }
    } ~
      path(Segment) { id =>
        get {
          complete(ToResponseMarshallable(orderService.getOrder(id.toInt)))
        } ~
          put {
            entity(as[OrderUpdate]) { update =>
              complete(ToResponseMarshallable(orderService.updateOrder(id.toInt, update)))
            }
          }
      }
  }

}
