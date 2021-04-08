package domain.model

import scala.math.BigDecimal.RoundingMode

sealed trait Currency {
  def render(price: BigDecimal): String
}

object Currency {

  case object Pound extends Currency {

    override def render(price: BigDecimal): String = {
      if (price >= 1)
        s"£${price.setScale(2, RoundingMode.HALF_UP)}"
      else
        s"${(price * 100).toLong}p"
    }

  }

}