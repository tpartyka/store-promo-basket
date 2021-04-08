package impl

import domain.model.Condition.BaseCondition
import domain.model.RewardType.PercentageDiscount
import domain.model._


class PriceBasketService {

  private val promos = Set(
      Promotion("Apples 10% off", 1L, PercentageDiscount(10)),
      Promotion(
        "Buy 2 tins of soup and get load of bread for half price",
        BaseCondition(4L, 2) and BaseCondition(3L, 1),
        Reward(3L, 1, PercentageDiscount(50))
      )
    )


  private val prices: Map[ProductId, BigDecimal] = Map(
      1L -> 1.00,
      2L -> 1.30,
      3L -> 0.80,
      4L -> 0.65
    )


  private val products = Map(
      "apples" -> 1L,
      "milk" -> 2L,
      "bread" -> 3L,
      "soup" -> 4L
    )



  def run(productNames: List[String]): String = {
    val basket = ProductService(products).resolveBasket(productNames)
    val basketWithRewards = PromotionService(promos).resolveRewards(basket)
    val basketSummary = PriceService(prices).calculateBasketSummary(basketWithRewards)
    basketSummary.render(Currency.Pound)
  }
}
