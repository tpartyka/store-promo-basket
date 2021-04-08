package impl

import domain.model.RewardType.{FixedAmountDiscount, PercentageDiscount}
import domain.model.{BasketSummary, BasketWithRewards}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers


class PriceServiceTest extends AnyFlatSpec with Matchers {

  it must "calculate BasketSummary - simple case - fixed amount promo" in {
    val priceRepo = Map(
      1L -> BigDecimal(20.00),
      2L -> BigDecimal(30.00)
    )

    val basket = BasketWithRewards(
      products = Map(
        0 -> 1L,
        1 -> 1L,
        2 -> 2L
      ),
      rewards = Map(
        2 -> ("PriceCut", FixedAmountDiscount(2.50))
      )
    )

    PriceService(priceRepo).calculateBasketSummary(basket) mustEqual
      BasketSummary(70.00, 67.50, Map("PriceCut" -> 2.50))
  }

  it must "calculate BasketSummary - simple case - percentage promo" in {
    val priceRepo = Map(
      1L -> BigDecimal(20.00),
      2L -> BigDecimal(30.00)
    )

    val basket = BasketWithRewards(
      products = Map(
        0 -> 1L,
        1 -> 1L,
        2 -> 2L
      ),
      rewards = Map(
        1 -> ("PriceCut", PercentageDiscount(80)),
        2 -> ("PriceCut", PercentageDiscount(50))
      )
    )

    PriceService(priceRepo).calculateBasketSummary(basket) mustEqual
      BasketSummary(70.00, 39.00, Map("PriceCut" -> 31.00))
  }

  it must "calculate BasketSummary - multiple promotions" in {
    val priceRepo = Map(
      1L -> BigDecimal(20.00),
      2L -> BigDecimal(30.00)
    )

    val basket = BasketWithRewards(
      products = Map(
        0 -> 1L,
        1 -> 1L,
        2 -> 2L
      ),
      rewards = Map(
        1 -> ("PriceCutFixed", FixedAmountDiscount(80)),
        2 -> ("PriceCutPerc", PercentageDiscount(50))
      )
    )

    PriceService(priceRepo).calculateBasketSummary(basket) mustEqual
      BasketSummary(70.00, 35.00, Map("PriceCutFixed" -> 20.00, "PriceCutPerc" -> 15.00))
  }
}

