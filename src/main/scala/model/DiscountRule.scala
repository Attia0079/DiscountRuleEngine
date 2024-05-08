package model
case class DiscountRule(
                      description: String,
                      qualifier: OrderInProcess => Boolean,
                      calculator: OrderInProcess => Double)


