package impl

import domain.model.{Basket, ProductId}

case class ProductService (cache: Map[String, ProductId]) {
  def resolveBasket(productNames: List[String]): Basket = {
    productNames.map(_.toLowerCase).map(name => cache(name)).zipWithIndex.map(_.swap).toMap
  }
}
