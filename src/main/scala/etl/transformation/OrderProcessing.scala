package etl.transformation

import model.{OrderInProcess, ProcessedOrder}
import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.math.BigDecimal.RoundingMode

case object OrderProcessing{
// First: Some functions that we will need later in the trasnformation process
  // a function that takes the ISO date formate and convert it into timestamp
  def stringToTimestamp(isoString: String): Timestamp = {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val dateTime = LocalDateTime.parse(isoString, formatter)
    Timestamp.valueOf(dateTime)
  }

  // Function to convert ISO formatted date string to Date
  def stringToDate(isoString: String): Date = {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    val localDate = LocalDate.parse(isoString, formatter)
    Date.valueOf(localDate)
  }

  // a function to calculate the difference between 2 dates(string), it will be used later to check the days left for expiry (checkExpiry)
  def getDateDifference(startDate: String, endDate: String): Long = {
    val start = LocalDate.parse(startDate)
    val end = LocalDate.parse(endDate)
    ChronoUnit.DAYS.between(start, end)
  }

// Second The Qualifiers and Calculators
  // expiry discount check
  // a function that checks the difference in days between order date and expiry date
  def expiryDiscountQualifier(l: OrderInProcess): Boolean = {
    val orderDate = l.orderTimestamp
    val expiryDate = l.expiryDate
    val daysDiff = getDateDifference(orderDate, expiryDate)
    //    val isQualified = if(daysDiff<=29) true else false
    val isQualified = daysDiff <= 29
    return isQualified
  }
  // a function that calculates the discount based on the difference between order date and expiry date
  def expiryDiscountCalculator(l: OrderInProcess): Double = {
    val orderDate = l.orderTimestamp
    val expiryDate = l.expiryDate
    val daysDiff = getDateDifference(orderDate, expiryDate)
    val discount = daysDiff match {
      case 29 => 0.01
      case 28 => 0.02
      case 27 => 0.03
      case _ => 0.0
    }
    return discount
  }

  // day discount check
  // a function to check the day and see if it matches any specific day were discounts are on (like march 23rd)
  def dayDiscountQualifier(l: OrderInProcess): Boolean = {
    val day = l.orderTimestamp
    val isQualified = day.substring(0, 10) match {
      case "2023-03-23" => true
      case _ => false
    }
    return isQualified
  }
  // a function to calculate discount based on the business rules for that day
  def dayDiscountCalculator(l: OrderInProcess): Double = {
    val day = l.orderTimestamp
    val discount = day.substring(0, 10) match {
      case "2023-03-23" => 0.5
      case _ => 0.0
    }
    return discount
  }

  // product type discount check
  // a function that checks the product type and see if it matches any of the discounted ones
  def productTypeQualifier(l: OrderInProcess): Boolean = {
    val productType = l.productName.split('-')(0).trim
    val isQualified = productType match {
      case "Cheese" => true
      case "Wine" => true
      case _ => false
    }
    return isQualified
  }
  // a function that checks the product type and see if it matches any of the discounted ones
  def productTypeCalculator(l: OrderInProcess): Double = {
    val productType = l.productName.split('-')(0).trim
    val discount = productType match {
      case "Cheese" => 0.1
      case "Wine" => 0.05
      case _ => 0.0
    }
    return discount
  }

  // quality discount check
  // a function that checks if the quantity ordered is enough to get a discount
  def quantityDiscountQualifier(l: OrderInProcess): Boolean = {
    val quantity = l.quantity
    val isQualified = quantity match {
      case quantity if quantity >= 6 && quantity < 10 => true
      case quantity if quantity >= 10 && quantity < 15 => true
      case quantity if quantity >= 15 => true
      case _ => false
    }
    return isQualified
  }
  // a function that checks if the quantity ordered is enough to get a discount
  def quantityDiscountCalculator(l: OrderInProcess): Double = {
    val quantity = l.quantity
    val discount = quantity match {
      case quantity if quantity >= 6 && quantity < 10 => 0.05
      case quantity if quantity >= 10 && quantity < 15 => 0.07
      case quantity if quantity >= 15 => 0.1
      case _ => 0.0
    }
    return discount
  }

  // putting the qualifiers and calculators in a rules tuple
  // Define a type alias for the discount rules
  type Rule = (OrderInProcess => Boolean, OrderInProcess => Double)

  //=================================================================================
  // ||||||||||||||||||||||Please ADD YOUR DISCOUNT RULES HERE!||||||||||||||||||||||
  //=================================================================================
  // a function where we add buiness rules and get a list of tuples of functions,
  // List[(qualifier, calculator)]
  def getDiscountRules(): List[Rule] = {
    val rules: List[Rule] = {
      List(
      (expiryDiscountQualifier, expiryDiscountCalculator),
      (dayDiscountQualifier, dayDiscountCalculator),
      (productTypeQualifier, productTypeCalculator),
      (quantityDiscountQualifier, quantityDiscountCalculator)
      )
    }
    return rules
  }
  // Function to filter rules based on qualifier function
  //  def filterRules(input: String, rules: List[Rule]): List[Rule] = {
  //    rules.filter { case (qualifier, _) => qualifier(input) }
  //  }

  // and now putting things together
  // so now, what we want is: tuple of functions ()
  def processOrders(order: OrderInProcess, rules: List[Rule]): ProcessedOrder = {
    val qualifiedRules = rules.filter{ case (qualifier,_) => qualifier(order)}
    // if the order is qualified for only one order will calculate it's discount
    // if an order is qualified for more than one rule, take the top 2 discounts
    // else the discount will be 0
    val finalDiscount: Double = if(qualifiedRules.length >= 2) {
      val discounts = qualifiedRules.map { case (_, calculator) => calculator(order) }.sorted.reverse.take(2)
      (discounts.head + discounts(1)) / 2
    } else if (qualifiedRules.length == 1){
      val discounts = qualifiedRules.map { case (_, calculator) => calculator(order) }
      discounts.head
    } else {
      0.0
    }
    val processedOrder = ProcessedOrder(
      order.orderId,
      order.orderTimestamp,
      order.productName,
      order.expiryDate,
      order.quantity,
      order.unitPrice,
      round(order.unitPrice * order.quantity, 2),
      round(finalDiscount, 2),
      round((order.quantity * order.unitPrice) * finalDiscount, 2),
      round((order.quantity * order.unitPrice) * (1- finalDiscount), 2),
      order.channel,
      order.paymentMethod)
    processedOrder
  }

  //a function to round numbers
  def round(number: Double, decimalPlaces: Int): Double = {
    BigDecimal(number).setScale(decimalPlaces, RoundingMode.HALF_UP).toDouble
  }
}

