package domain.model

import domain.model.Condition.BaseCondition

case class Promotion(name: String, condition: Condition, reward: Reward)

object Promotion {
  def apply(name: String, product: ProductId, rewardType: RewardType): Promotion = {
    Promotion(name, BaseCondition(product, 1), Reward(product, 1, rewardType))
  }
}