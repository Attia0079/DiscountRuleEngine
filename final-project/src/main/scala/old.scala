//import DataProcessing.getDateDifference
//
//import java.time.temporal.ChronoUnit
//import scala.io.Source
//import scala.math.BigDecimal.RoundingMode
//import java.sql.{Connection, Date, DriverManager, PreparedStatement, Timestamp}
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.time.ZonedDateTime
//import java.time.LocalDate
//
//
//  // a function to calculate the difference between 2 dates(string), it will be used later to check the days left for expiry (checkExpiry)
//  def getDateDifference(startDate: String, endDate: String): Long = {
//    val start = LocalDate.parse(startDate)
//    val end = LocalDate.parse(endDate)
//    ChronoUnit.DAYS.between(start, end)
//  }
//
//  // reading the data into lines
//  val lines = Source.fromFile("/home/attia/Documents/Scala/final-project/src/main/resources/TRX1000.csv").getLines().toList.tail
//
//  // a function that checks the difference in days between order date and expiry date and return a discount based on it, if any
//  // calculate the difference using getDateDifference and returns the discount
//  def checkExpiry(l: String): Double = {
//    val orderDate = l.split(',')(0).substring(0, 10)
//    val expiryDate = l.split(',')(2).substring(0, 10)
//    val daysDiff = getDateDifference(orderDate, expiryDate)
//    val discount = daysDiff match {
//      case 29 => 0.01
//      case 28 => 0.02
//      case 27 => 0.03
//      case _ => 0.0
//    }
//    return discount
//  }
//
//  // a function to check the day and see if it matches any specific days were discounts are on (like march 23rd)
//  def checkDay(l: String): Double = {
//    val day = l.split(',')(0)
//    val discount = day.substring(0, 10) match {
//      case "2023-03-23" => 0.5
//      case _ => 0.0
//    }
//    return discount
//  }
//
//  // a function that checks the product type and see if it matches any of the discounted ones
//  def checkProductType(l: String): Double = {
//    val productType = l.split(',')(1).split('-')(0).trim
//    val discount = productType match {
//      case "Cheese" => 0.1
//      case "Wine" => 0.05
//      case _ => 0.0
//    }
//    return discount
//  }
//
//  // a function that checks if the quantity ordered is enough to get a discount
//  def checkQuantity(l: String): Double = {
//    val quantity = l.split(',')(3).toInt
//    val discount = quantity match {
//      case quantity if quantity >= 6 && quantity < 10 => 0.05
//      case quantity if quantity >= 10 && quantity < 15 => 0.07
//      case quantity if quantity >= 15 => 0.1
//      case _ => 0.0
//    }
//    return discount
//  }
//
//  //a function that takes a line(order) and returns a list of all the applicable discount utilizing all the previous functions
//  def gatherDiscountsFromChecks(line: String): List[Double] = {
//    val all_discounts: List[Double] = List(checkQuantity(line), checkProductType(line), checkProductType(line), checkDay(line))
//    val sorted_discounts = all_discounts.sorted.reverse // Sort in desc order
//    return sorted_discounts
//  }
//
//  // a function that calculates the final discount according to the business logic
//  def calculateFinalDiscount(discounts: List[Double]): Double = {
//    if (discounts(1) != 0.0) {
//      val finalDiscount = (discounts(0) + discounts(1)) / 2.0
//      BigDecimal(finalDiscount).setScale(3, RoundingMode.HALF_UP).toDouble
//    } else
//      BigDecimal(discounts(0)).setScale(3, RoundingMode.HALF_UP).toDouble
//  }
//
//  //  a function that calculates the final price after the discount
//  def getFinalPrice(quantity: Int, unitPrice: Double, finalDiscount: Double): Double = {
//    val finalPrice = BigDecimal(quantity * unitPrice * finalDiscount).setScale(3, RoundingMode.HALF_UP).toDouble
//    return finalPrice
//  }
//
//  // a function that takes a line(order) as a string, implements all the following functions and returns other ready to the next stage
//  def processOrderLine(orderLine: String): String = {
//    val fields = orderLine.split(',')
//    if (fields.length >= 7) {
//      val date = fields(0)
//      val expiryDate = fields(2).substring(0, 10)
//      val unitPrice = fields(4).toDouble
//      val quantity = fields(3).toInt
//      val discount = calculateFinalDiscount(gatherDiscountsFromChecks(orderLine))
//      val finalPrice = getFinalPrice(quantity, unitPrice, discount)
//      val paymentMethod = fields(6)
//      val productName = fields(1)
//      s"$date,$productName,$expiryDate,$unitPrice,$quantity,$discount,$finalPrice,$paymentMethod"
//    } else {
//      s"Invalid input line: $orderLine"
//    }
//  }
//
//  val output = lines.map(processOrderLine)
//  output.foreach(println)
