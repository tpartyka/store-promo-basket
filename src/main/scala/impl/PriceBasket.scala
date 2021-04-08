package impl

object PriceBasket {

  def main(args: Array[String]): Unit = args.toList match {
    case "PriceBasket" :: tail => println(new PriceBasketService().run(tail))
    case _ => println("Unknown application, choose PriceBasket")
  }

}
