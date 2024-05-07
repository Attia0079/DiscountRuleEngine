package etl.loading
//
//import model.ProcessedOrder
//
import model.ProcessedOrder

import java.sql.{Connection, Date, DriverManager, Timestamp}
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import scala.util.Try
//
case object DataBaseLoader{
  // Function to establish a database connection

  def getConnection(url: String, user: String, password: String): Connection = {
    DriverManager.getConnection(url, user, password)
  }
//  val conn = getConnection(url, user, password)

  // Function to insert a record into the database
  def insertRecord(connection: Connection, order: ProcessedOrder): Unit = {
    val sql = "INSERT INTO orders (order_id, order_date, product_name, expiry_date, unit_price, quantity, price_before_discount, discount, discount_value, final_price, channel, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
//    val sql = "INSERT INTO orders (order_date, product_name, expiry_date, unit_price, quantity, discount, discountValue, final_price, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    val preparedStatement = connection.prepareStatement(sql)

    preparedStatement.setString(1, (order.orderId).toString) // product name
    preparedStatement.setTimestamp(2, stringToTimestamp(order.orderTimestamp)) //order date
    preparedStatement.setString(3, order.productName) // product name
    preparedStatement.setDate(4, stringToDate(order.expiryDate)) // expiry date
    preparedStatement.setInt(5, order.quantity) // unit price
    preparedStatement.setDouble(6, order.unitPrice) // quantity
    preparedStatement.setDouble(7, order.priceBeforeDiscount) // price before discount
    preparedStatement.setDouble(8, order.discount) // discount
    preparedStatement.setDouble(9, order.discountValue) // discount value
    preparedStatement.setDouble(10, order.finalPrice) // final price
    preparedStatement.setString(11, order.channel) // channel method
    preparedStatement.setString(12, order.paymentMethod) // payment method

    preparedStatement.executeUpdate()
    preparedStatement.close()
  }

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
}

//
//  // Function to insert a record into the database
//  def insertRecord(connection: Connection, order: ProcessedOrder): Unit = {
//    val sql = "INSERT INTO ProcessedOrder ( orderId, orderTimestamp, productName, expiryDate, quantity, unitPrice, priceBeforeDiscount, discount, discountValue, finalPrice, channel, paymentMethod) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
//    val preparedStatement = connection.prepareStatement(sql)
//    try {
//      preparedStatement.setString(1, (order.orderId).toString) // product name
//      preparedStatement.setTimestamp(2, stringToTimestamp(order.orderTimestamp)) //order date
//      preparedStatement.setString(3, order.productName) // product name
//      preparedStatement.setDate(4, stringToDate(order.expiryDate)) // expiry date
//      preparedStatement.setInt(5, order.quantity) // unit price
//      preparedStatement.setDouble(6, order.unitPrice) // quantity
//      preparedStatement.setDouble(7, order.priceBeforeDiscount) // price before discount
//      preparedStatement.setDouble(8, order.discount) // discount
//      preparedStatement.setDouble(9, order.discountValue) // discount value
//      preparedStatement.setDouble(10, order.finalPrice) // final price
//      preparedStatement.setString(11, order.channel) // channel method
//      preparedStatement.setString(12, order.paymentMethod) // payment method
//
//      preparedStatement.executeUpdate()
//    } finally {
//      preparedStatement.close()
//    }
//  }
//
//  // Function to establish a database connection
//  private def getConnection(url: String, user: String, password: String): Connection = {
//    DriverManager.getConnection(url, user, password)
//  }
//
//  // Function to insert data into the database
//  def insertIntoDB(url: String, user: String, password: String, order: ProcessedOrder): Try[Int] = Try {
//    val connection = getConnection(url, user, password)
//    try {
//      insertRecord(connection, order)
//    } finally {
//      connection.close()
//    }
//  }.flatten
//
//}
//
//
