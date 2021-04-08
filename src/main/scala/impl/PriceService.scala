package impl

import domain.model._

case class PriceService(priceRepository: Map[ProductId, BigDecimal]) {

  def calculateBasketSummary(basket: BasketWithRewards): BasketSummary = {
    val productWithPrices = calculatePrices(basket.products)
    val discounts = aggregateDiscounts(productWithPrices, basket.rewards)

    val totalDiscounts = discounts.values.sum
    val subtotal = productWithPrices.values.sum
    val total = subtotal - totalDiscounts
    BasketSummary(subtotal, total, discounts)
  }

  private def aggregateDiscounts(productWithPrice: Map[Index, BigDecimal],
                                 rewards: AppliedRewards): Map[String, BigDecimal] = {
    val empty = Map.empty[String, BigDecimal].withDefaultValue(BigDecimal(0))
    rewards.foldLeft(empty) { case (accumulator, (index, (trigger, reward))) =>
      val newDiscount = reward.calcDiscount(productWithPrice(index))
      val totalDiscounts = accumulator(trigger) + newDiscount
      accumulator.updated(trigger, totalDiscounts)
    }
  }

  private def calculatePrices(basket: Basket): Map[Index, BigDecimal] = {
    basket.map {
      case (index, product) => index -> priceRepository.getOrElse(product, sys.error(s"Unknown price for $product"))
    }
  }


}

