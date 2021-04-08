package impl

import domain.model.Condition.BaseCondition
import domain.model.RewardType.PercentageDiscount
import domain.model._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class PromotionServiceTest extends AnyFlatSpec with Matchers with TableDrivenPropertyChecks {

  it must "computePercentageDiscounts - complex promo case - single promotion" in {

    val promo = Promotion("complex promo", BaseCondition(1L, 2) and BaseCondition(2L, 1), Reward(2L, 1, PercentageDiscount(60)))
    val service = PromotionService(Set(promo))

    Table(
      "basket" -> "calculated rewards",
      Map((0,1L), (1, 1L), (2,2L)) ->
        Map(2 -> ("complex promo", PercentageDiscount(60))),

      Map((0,1L), (1, 1L)) ->
        Map(),

      Map((0,1L), (1, 1L), (2,2L), (3,2L)) ->
        Map(2 -> ("complex promo", PercentageDiscount(60))),

      Map((0,1L), (1, 1L), (2, 1L),(3, 1L),(4, 1L),(5, 1L), (6,2L), (7,2L)) ->
        Map(6 -> ("complex promo", PercentageDiscount(60)), 7 -> ("complex promo", PercentageDiscount(60))),

      Map((0,1L), (1, 1L), (2,1L), (3,1L), (4,2L)) ->
        Map(4 -> ("complex promo", PercentageDiscount(60)))

    ) forEvery {
      case (basket, rewards) => service.getRewardsForAllPromotions(basket) must contain theSameElementsAs rewards
    }

  }

  it must "computePercentageDiscounts - simple promo case - multiple promotions" in {
    val promo = Set(
      Promotion("simple promo #1", 1L, PercentageDiscount(10)),
      Promotion("simple promo #2", 2L, PercentageDiscount(10)),
      Promotion("simple promo #3", 3L, PercentageDiscount(30))
    )

    val service = PromotionService(promo)

    Table(
      "basket" -> "calculated rewards",

      Map((0,1L), (1, 1L), (2,2L))  ->
        Map(
          0 -> ("simple promo #1", PercentageDiscount(10)),
          1 -> ("simple promo #1", PercentageDiscount(10)),
          2 -> ("simple promo #2", PercentageDiscount(10))
        ),

      Map((0,2L))  ->
        Map(
          0 -> ("simple promo #2", PercentageDiscount(10))
        ),

      Map((0,4L))->
        Map()

    ) forEvery {
      case (basket, rewards) => service.getRewardsForAllPromotions(basket) must contain theSameElementsAs rewards
    }

  }

  // unclear requirement to discuss
  it must "computePercentageDiscounts - multiple promotions matches the same product - no requirements provided" ignore {
    val promo = Set(
      Promotion("simple promo #1", BaseCondition(1L, 1), Reward(1L, 1, PercentageDiscount(10))),
      Promotion("simple promo #2", BaseCondition(1L, 1), Reward(1L, 1, PercentageDiscount(20)))
    )

    val service = PromotionService(promo)

    Table(
      "basket" -> "calculated rewards",
      Map((0,1L), (1, 1L), (2,2L)) -> List.empty
    ) forEvery {
      case (basket, rewards) => service.getRewardsForAllPromotions(basket) must contain theSameElementsAs rewards
    }

  }

}