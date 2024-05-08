package model

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

//case class BusinessRules()
case class DiscountRules() {

  // Function to create a list of all available discount rules
  def allDiscountRules(): List[DiscountRule] = List(
    expiryDaysLeftDiscount,
    productTypeDiscount,
    specialDayDiscount,
    quantityDiscount
  )

  val expiryDaysLeftDiscount = new DiscountRule(
    description = "if the days are 29 days or less give 1% or more discount",
    qualifier = expiryDiscountQualifier,
    calculator = expiryDiscountCalculator
  )
  val productTypeDiscount = new DiscountRule(
    description = "if product is discounted",
    qualifier = productTypeQualifier,
    calculator = productTypeCalculator
  )
  val specialDayDiscount = new DiscountRule(
    description = "If the day is any special for a discount",
    qualifier = dayDiscountQualifier,
    calculator = dayDiscountCalculator
  )
  val quantityDiscount = new DiscountRule(
    description = "If the quantity is enough to get a discount",
    qualifier = quantityDiscountQualifier,
    calculator = quantityDiscountCalculator
  )


  // quantity discount check
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



  //custom functions needed for the validation process
  // a function to calculate the difference between 2 dates(string), it will be used later to check the days left for expiry (checkExpiry)
  def getDateDifference(startDate: String, endDate: String): Long = {
    val start = LocalDate.parse(startDate.substring(0, 10))
    val end = LocalDate.parse(endDate)
    ChronoUnit.DAYS.between(start, end)
  }
}