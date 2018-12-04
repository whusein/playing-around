package entities

final case class Order (id: Int, side: Int, product: String, price: Double, quantity: Int, owner: String)
