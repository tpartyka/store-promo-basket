package domain.model

sealed trait RewardType {

  def calcDiscount(productPrice: BigDecimal): BigDecimal

}

object RewardType {
  case class PercentageDiscount(percentageAmount: Long) extends RewardType {
    override def calcDiscount(productPrice: BigDecimal): BigDecimal = {
      productPrice * percentageAmount / 100
    }
  }

  case class FixedAmountDiscount(discount: BigDecimal) extends RewardType {
    override def calcDiscount(productPrice: BigDecimal): BigDecimal = {
      val afterDiscount = productPrice - discount
      if (afterDiscount < 0) productPrice else discount
    }
  }
}
