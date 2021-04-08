package domain.model

case class BasketWithRewards(products: Basket, rewards: Map[Index, (String, RewardType)])

