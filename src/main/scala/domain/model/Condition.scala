package domain.model


sealed trait Condition {

  def evaluate(basket: Basket): List[Map[Index, ProductId]]

  def and(other: Condition): Condition = Condition.and(this, other)

  def or(other: Condition): Condition = Condition.or(this, other)

}

case object Condition {

  private def and(left: Condition, right: Condition): Condition = new Condition {
    override def evaluate(basket: Basket): List[Map[Index, ProductId]] =
      (left.evaluate(basket) zip right.evaluate(basket)).map { case (left, right) => left ++ right }
  }

  private def or(left: Condition, right: Condition): Condition = ???

  case class BaseCondition(productRequired: ProductId, quantity: Int) extends Condition {

    def evaluate(basket: Basket): List[Map[Index, ProductId]] = {
      val requiredProducts: Basket =
        basket.groupBy(_._2).getOrElse(productRequired, Map.empty[Index, ProductId])
      requiredProducts.grouped(quantity).takeWhile(_.size == quantity).toList
    }

  }
}



