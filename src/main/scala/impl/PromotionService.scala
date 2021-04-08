package impl

import domain.model._

case class PromotionService(promotionRepo: Set[Promotion]) {


  def resolveRewards(basket: Basket): BasketWithRewards = {
    BasketWithRewards(basket, getRewardsForAllPromotions(basket))
  }

  def getRewardsForAllPromotions(basket: Basket): AppliedRewards = {
    val empty = Map.empty[Index, (String, RewardType)]
    promotionRepo.foldLeft(empty) { case (accumulator, nextPromo) =>
      val rewards = getRewardForSinglePromo(nextPromo, basket)
      val itemsAlreadyInPromo = (accumulator.keySet intersect rewards.keySet).nonEmpty
      if (itemsAlreadyInPromo) accumulator else accumulator ++ rewards
    }
  }


  private def getRewardForSinglePromo(promotion: Promotion, products: Basket): AppliedRewards = {
    val Promotion(name, condition, Reward(rewardProduct, maxQuantity, rewardType)) = promotion
    condition.evaluate(products).flatMap { singleProductSet =>
      singleProductSet.collect { case (index, id) if id == rewardProduct =>
        (index, (name, rewardType))
      }.take(maxQuantity)
    }.toMap

  }
}
