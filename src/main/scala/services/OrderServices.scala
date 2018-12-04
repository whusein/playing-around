package services

import akka.Done
import entities.{Order, OrderUpdate}

import scala.concurrent.{ExecutionContext, Future}

class OrderServices (implicit val executionContext: ExecutionContext) {
  var orders: List[Order] = Nil

  def createOrder(order: Order): Future[Done]= Future{
    orders = order :: orders
    Done
  }

  def getOrder(id: Int): Future[Option[Order]] = Future{
    Option(orders.filter(o => o.id == id) match {case x :: xs => x; case List() => throw new Error("There is no such id")})
  }

  def getOrders: Future[Option[List[Order]]] = Future {
    Option(orders)
  }

  def updateOrder(id: Int, update: OrderUpdate): Future[Option[Order]] = ???

  def deleteOrder(id: Int): Future[Unit] = ???

}
