package etl.transformation

import com.typesafe.scalalogging.Logger
import model.{DiscountRule, OrderForProcessing, OrderInProcess, ProcessedOrder}
import java.util.UUID
import scala.math.BigDecimal.RoundingMode
case object OrderProcessing{

  private val logger = Logger(getClass.getName)

  //a function to round numbers
  def round(number: Double, decimalPlaces: Int): Double = {
    BigDecimal(number).setScale(decimalPlaces, RoundingMode.HALF_UP).toDouble
  }

  //  converting the ordersForProcessing to ordersInProcess and giving it an ID
  def prepareOrders(order: OrderForProcessing): OrderInProcess = {
      val id = UUID.randomUUID()
      val orderInProcess = OrderInProcess(
        id,
        order.orderTimestamp,
        order.productName,
        order.expiryDate,
        order.quantity,
        order.unitPrice,
        order.channel,
        order.paymentMethod
      )
    logger.debug(s"Order prepared: $orderInProcess")
    orderInProcess
  }

  // this function takes an order for processing and returns a processed order
  // it first gives it an ID
  def processOrders(order: OrderForProcessing, rules: List[DiscountRule]): ProcessedOrder = {
    val orderInProcess = prepareOrders(order)
    val qualifiedRules = rules.filter(r => r.qualifier(orderInProcess))
    // if the order is qualified for only one order will calculate it's discount
    // if an order is qualified for more than one rule, take the top 2 discounts
    // else the discount will be 0
    val finalDiscount: Double =
      if(qualifiedRules.length >= 2) {
        val discounts = qualifiedRules.map { qr => qr.calculator(orderInProcess) }.sorted.reverse.take(2)
        val avg_discount = (discounts.head + discounts(1)) / 2
        avg_discount
      }
      else if (qualifiedRules.length == 1){
        val discounts = qualifiedRules.map { qr => qr.calculator(orderInProcess) }
        discounts.head
      }
      else 0.0

    val processedOrder = ProcessedOrder(
      orderInProcess.orderId,
      orderInProcess.orderTimestamp,
      orderInProcess.productName,
      orderInProcess.expiryDate,
      orderInProcess.quantity,
      orderInProcess.unitPrice,
      round(orderInProcess.unitPrice * order.quantity, 2),
      round(finalDiscount, 2),
      round((orderInProcess.quantity * orderInProcess.unitPrice) * finalDiscount, 2),
      round((orderInProcess.quantity * orderInProcess.unitPrice) * (1- finalDiscount), 2),
      orderInProcess.channel,
      orderInProcess.paymentMethod)
    logger.info(s"Order processed: $processedOrder")
    processedOrder
  }
}

