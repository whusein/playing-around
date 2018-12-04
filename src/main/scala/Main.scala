import boot._

object Main extends App {

  val initStatus = init()
  println("Initialing the app: " + (if(initStatus) "success" else "failed"))

  def init(): Boolean = {
    new AkkaHTTPServer
    new SlickConnector
    true
  }
}



