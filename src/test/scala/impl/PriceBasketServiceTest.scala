package impl

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class PriceBasketServiceTest extends AnyFlatSpec with Matchers {
  it must "calculate and render final price" in {
    new PriceBasketService().run(List("Soup", "Soup", "bread", "apples", "apples", "milk", "bread")) mustEqual
      s"""
         |Subtotal: £6.20
         |Apples 10% off: 20p
         |Buy 2 tins of soup and get load of bread for half price: 40p
         |Total: £5.60
         |""".stripMargin
  }
}
