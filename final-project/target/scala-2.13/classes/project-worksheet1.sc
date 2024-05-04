var a = "col1,Hello - World,col3"
var b = a.split(',')(1).split('-')(1).trim
//var c = " ,How are you?"
//var d = a + b + c
print(b)
//import scala.io.Source
//import java.time.LocalDate
//import java.time.temporal.ChronoUnit
//import java.io.{File, FileOutputStream, PrintWriter}
//import scala.io.{BufferedSource, Source}
//
//def getDateDifference(startDate: String, endDate: String): Long = {
//  val start = LocalDate.parse(startDate)
//  val end = LocalDate.parse(endDate)
//  ChronoUnit.DAYS.between(start, end)
//}
//
//val lines = Source.fromFile("/home/attia/Documents/Scala/final-project/src/main/resources/TRX1000.csv").getLines().toList.tail
////lines.foreach(println)
////val mappedInput = lines.map(_.split(',')).map(x=>x(1).toString)
//
////mappedInput.foreach(println)
//// 2023-04-18T18:18:40Z	Wine - White Pinot Grigio	2023-06-10	6	122.47	Store	Visa
//
//
////////////////////////////////////////////////////////////////////////////////
//// a function that checks the difference in days between order date and expiry date
//def checkExpiry(l:String): Double ={
//  val orderDate = l.split(',')(0).substring(0,10)
//  val expiryDate = l.split(',')(2).substring(0,10)
//  val daysDiff = getDateDifference(orderDate, expiryDate)
//  val discount = daysDiff match {
//    case 29 => 0.01
//    case 28 => 0.02
//    case 27 => 0.03
//    case _ => 0.0
//  }
//  return discount
//}
//
////lines.map(checkExpiry).filter(_ != 0.0).foreach(println)
////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
////// a function to check upon the day and see if it is march 23rd
//def checkDay(l:String): Double ={
//  val day = l.split(',')(0)
//  val discount = day.substring(0,10) match {
//    case "2023-03-23" => 0.5
//    case _ => 0.0
//  }
//  return discount
//}
////
////lines.map(checkDay).filter(_ == 0.5).foreach(println)
////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////
//////// a function to check upon the product type
//def checkProductType(l:String): Double ={
//  val productType = l.split(',')(1)
//  val discount = productType.split('-')(0).trim match {
//    case "Cheese" => 0.1
//    case "Wine" => 0.05
//    case _ => 0.0
//  }
//  return discount
//}
////
////lines.map(checkProductType).filter(_ == 0.1).foreach(println)
//////////////////////////////////////////////////////////////////////////////
//
//////////////////////////////////////////////////////////////////////////////
////// a function that checks on the order quantity
//////6 – 9 units -> 5% discount
//////10-14 units -> 7% discount
//////More than 15 -> 10% discount
////
//def checkQuantity(l:String): Double ={
//  val quantity = l.split(',')(3).toInt
//  val discount = quantity match {
//    case quantity if quantity >= 6  && quantity < 10 => 0.05
//    case quantity if quantity >= 10 && quantity < 15 => 0.07
//    case quantity if quantity >= 15  => 0.1
//    case _ => 0.0
//  }
//  return discount
//}
////
////lines.map(checkQuantity).filter(_ == 0.05).foreach(println)
//////////////////////////////////////////////////////////////////////////////
//// a function that takes a line and uses all the discounts to return a list of discounts (doubles)
//def getAllDiscounts(line: String):List[Double]= {
//  val all_discounts: List[Double] = List(checkQuantity(line),checkProductType(line),checkProductType(line),checkDay(line))
//  val sorted_discounts = all_discounts.sorted.reverse // Sort in desc order
//  return sorted_discounts
//}
//
//
//// a function that calculates the final discount
//def getFinalDiscount (discounts: List[Double]): Double = {
//  if (discounts(1) != 0.0) {
//      val finalDiscount = (discounts(0) + discounts(1))/2.0
//    finalDiscount
//  }
//  else discounts(0)
//}
//
//def getFinalPrice(quantity: Int,unitPrice: Double, finalDiscount: Double ): Double = {
//  val finalPrice = quantity * unitPrice * finalDiscount
//  return finalPrice
//}
//
//val allDiscountsSorted = lines.map(line => getAllDiscounts(line).sorted.reverse)
//val topDiscounts = allDiscountsSorted.map(_.slice(0,2))
//val finalDiscount = topDiscounts.map(getFinalDiscount)
////val Discounted
//
////topDiscounts.foreach(println)
//finalDiscount.foreach(println)
//
////val tableToWrite = finalDiscount
//
//
//
//val f: File = new File("src/main/resources/outputOrders.csv")
//val writer = new PrintWriter(new FileOutputStream(f, true))
//
////writeLine(header)
////body.map(toCall).map(processCall).foreach(writeLine)
////writer.close()
