package model
import java.time.{LocalDate, LocalDateTime}
import java.time.temporal.ChronoUnit

//case class BusinessRules()
case class DiscountRules() {

  // Function to create a list of all available discount rules
  def allDiscountRules(): List[DiscountRule] = List(
    expiryDaysLeftDiscount,
    productTypeDiscount,
    specialDayDiscount,
    quantityDiscount,
    AppOrdersDiscount,
    paymentChannelDiscount
  )

  val expiryDaysLeftDiscount = new DiscountRule(
    description = "if the days are 29 days or less give 1% discount for each day",
    qualifier = expiryDiscountQualifier,
    calculator = expiryDiscountCalculator
  )
  val productTypeDiscount = new DiscountRule(
    description = "if the product is cheese then 10% discount, if wine then 5% ",
    qualifier = productTypeQualifier,
    calculator = productTypeCalculator
  )
  val specialDayDiscount = new DiscountRule(
    description = "if the order day is a special day like march 23rd then 50% discount! ",
    qualifier = dayDiscountQualifier,
    calculator = dayDiscountCalculator
  )
  val quantityDiscount = new DiscountRule(
    description = "If the quantity is enough to get a discount 6+",
    qualifier = quantityDiscountQualifier,
    calculator = quantityDiscountCalculator
  )
  val AppOrdersDiscount = new DiscountRule(
    description = "If the order was made through the application an extra discount is given depending on the quantity",
    qualifier = AppOrdersDiscountQualifier,
    calculator = AppOrdersDiscountCalculator
  )

  val paymentChannelDiscount = new DiscountRule(
    description = "going paperless and paying via VISA gives an extra 5% discount",
    qualifier = paymentChannelDiscountQualifier,
    calculator = paymentChannelDiscountCalculator
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
    val isQualified = daysDiff <= 29
    return isQualified
  }

  // a function that calculates the discount based on the difference between order date and expiry date
  def expiryDiscountCalculator(l: OrderInProcess): Double = {
    val orderDate = l.orderTimestamp
    val expiryDate = l.expiryDate
    val daysDiff = getDateDifference(orderDate, expiryDate)
    val discountPct = 30.0 - daysDiff
    val discount = discountPct/100
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

  // app orders discount
  // a function to check if the order was made through the application
  def AppOrdersDiscountQualifier(l: OrderInProcess): Boolean = {
    val payment_method = l.channel
    val isQualified = if (payment_method == "App") true
    else false
    isQualified
  }

  // a function to calculate the discount if the order was made through the application
  def AppOrdersDiscountCalculator(l: OrderInProcess): Double = {
    val payment_method = l.channel
    val q = l.quantity

    // Round up quantity to the nearest multiple of 5
    val roundedQuantity = ((q + 4) / 5) * 5

    // Determine the discount based on the payment method and rounded quantity
    val discount = if (payment_method == "App") {
      if (roundedQuantity <= 5) {
        5
      } else {
        ((roundedQuantity / 5) * 5).toDouble
      }
    } else {
      0
    }

    discount/100
  }



  // payment channel discount check
  // a function to check if the payment channel of the order is visa
  def paymentChannelDiscountQualifier(l: OrderInProcess): Boolean = {
    val paymentChannel = l.paymentMethod
    val isQualified = if (paymentChannel == "Visa") true
    else false
    isQualified
  }

  // a function to calculate the discount if the payment channel of the order is visa
  def paymentChannelDiscountCalculator(l: OrderInProcess): Double = {
    val paymentChannel = l.paymentMethod
    val discount = if (paymentChannel == "Visa") 0.05
    else 0
    discount
  }

  //custom functions needed for the validation process
  // a function to calculate the difference between 2 dates(string), it will be used later to check the days left for expiry (checkExpiry)
  def getDateDifference(startDate: String, endDate: String): Long = {
    val start = LocalDate.parse(startDate.substring(0, 10))
    val end = LocalDate.parse(endDate)
    ChronoUnit.DAYS.between(start, end)
  }

}