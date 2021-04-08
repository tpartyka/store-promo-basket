package domain.model

case class BasketSummary(subtotal: BigDecimal, total: BigDecimal, offersApplied: Map[String, BigDecimal]) {

  def render(currency: Currency): String = {
    val offers = offersApplied.map {
      case (promoName, discount) => s"$promoName: ${currency.render(discount)}"
    }

    s"""
    |Subtotal: ${currency.render(subtotal)}
    |${if (offers.nonEmpty) offers.mkString("\n") else "(No offers available)"}
    |Total: ${currency.render(total)}
    |""".stripMargin
  }

}