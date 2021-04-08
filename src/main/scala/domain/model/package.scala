package domain

package object model {

  type Index = Int
  type ProductId = Long


  type Basket = Map[Index, ProductId]
  type AppliedRewards = Map[Index, (String, RewardType)]
}
