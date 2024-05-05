// the imports required
import model.{OrderForProcessing, OrderInProcess}

import java.time.temporal.ChronoUnit
import scala.io.Source
import scala.math.BigDecimal.RoundingMode
import java.sql.{Connection, Date, DriverManager, PreparedStatement, Timestamp}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.util.UUID

object Main extends App{
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

  // reading the data into a list of orders to be processed
  val lines = Source.fromFile("src/main/scala/resources/TRX1000.csv").getLines().toList.tail
//  val person = Person("John", 30)
  //converting the lines into order for processing object
    val ordersForProcessing = lines.map{ l =>
      val line = l.split(',').map(_.trim) // Split once and trim whitespace
      OrderForProcessing(
        line.headOption.getOrElse(""),
        line.lift(1).getOrElse(""),
        line.lift(2).getOrElse(""),
        line.lift(3).getOrElse("").toInt,
        line.lift(4).getOrElse("").toDouble,
        line.lift(5).getOrElse(""),
        line.lift(6).getOrElse("")
      )
    }
//  converting the ordersForProcessing to ordersInProcess
  val ordersInProcess = ordersForProcessing.map{ order =>
    val id = UUID.randomUUID()
    OrderInProcess(
      orderId = id,
      orderTimestamp = order.orderTimestamp,
      productName = order.productName,
      expiryDate = order.expiryDate,
      quantity = order.quantity,
      unitPrice = order.unitPrice,
      channel = order.channel,
      paymentMethod = order.paymentMethod
    )
  }

  //*******************************************
  // now with the Qualifiers and Calculators
  //*******************************************

  // expiry discount check
  // a function that checks the difference in days between order date and expiry date
  def expiryDiscountQualifier(l: String): Boolean = {
    val orderDate = l.split(',')(0).substring(0, 10)
    val expiryDate = l.split(',')(2).substring(0, 10)
    val daysDiff = getDateDifference(orderDate, expiryDate)
//    val isQualified = if(daysDiff<=29) true else false
    val isQualified = daysDiff <= 29
    return isQualified
  }
  // a function that calculates the discount based on the difference between order date and expiry date
  def expiryDiscountCalculator(l: String): Double = {
    val orderDate = l.split(',')(0).substring(0, 10)
    val expiryDate = l.split(',')(2).substring(0, 10)
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
  def dayDiscountQualifier(l: String): Boolean = {
    val day = l.split(',')(0)
    val isQualified = day.substring(0, 10) match {
      case "2023-03-23" => true
      case _ => false
    }
    return isQualified
  }
  // a function to calculate discount based on the business rules for that day
  def dayDiscountCalculator(l: String): Double = {
    val day = l.split(',')(0)
    val discount = day.substring(0, 10) match {
      case "2023-03-23" => 0.5
      case _ => 0.0
    }
    return discount
  }

  // product type discount check
  // a function that checks the product type and see if it matches any of the discounted ones
  def productTypeQualifier(l: String): Boolean = {
    val productType = l.split(',')(1).split('-')(0).trim
    val isQualified = productType match {
      case "Cheese" => true
      case "Wine" => true
      case _ => false
    }
    return isQualified
  }
  // a function that checks the product type and see if it matches any of the discounted ones
  def productTypeCalculator(l: String): Double = {
    val productType = l.split(',')(1).split('-')(0).trim
    val discount = productType match {
      case "Cheese" => 0.1
      case "Wine" => 0.05
      case _ => 0.0
    }
    return discount
  }

  // quality discount check
  // a function that checks if the quantity ordered is enough to get a discount
  def quantityDiscountQualifier(l: String): Boolean = {
    val quantity = l.split(',')(3).toInt
    val isQualified = quantity match {
      case quantity if quantity >= 6 && quantity < 10 => true
      case quantity if quantity >= 10 && quantity < 15 => true
      case quantity if quantity >= 15 => true
      case _ => false
    }
    return isQualified
  }
  // a function that checks if the quantity ordered is enough to get a discount
  def quantityDiscountCalculator(l: String): Double = {
    val quantity = l.split(',')(3).toInt
    val discount = quantity match {
      case quantity if quantity >= 6 && quantity < 10 => 0.05
      case quantity if quantity >= 10 && quantity < 15 => 0.07
      case quantity if quantity >= 15 => 0.1
      case _ => 0.0
    }
    return discount
  }




  // and now putting things together
  // so now, what we want is: tuple of functions ()
  def getOrdersWithDiscount(order: String, rules: (String => Boolean, String => Double)): String = {
    val discount = rules.
    "wtf"
  }

  def getDiscountRules(): List[(String => Boolean,String => Double)] = {
    List(
      (expiryDiscountQualifier, expiryDiscountCalculator),
      (dayDiscountQualifier, dayDiscountCalculator),
      (productTypeQualifier, productTypeCalculator),
      (quantityDiscountQualifier, quantityDiscountCalculator)
    )
  }

//  val ordersWithDiscount = getDiscount
}
