import java.sql.DriverManager

object Main {
  def main(args: Array[String]): Unit = {
    try {
      // JDBC URL, username, and password of PostgreSQL server
      val url = "jdbc:postgresql://localhost:5432/sales_mart"
      val user = "root"
      val password = "root"

      // Load the driver
      Class.forName("org.postgresql.Driver")

      // Establish a connection
      val connection = DriverManager.getConnection(url, user, password)

      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM orders")
      while (resultSet.next()) {
        // Retrieve column values from the result set
        val orderDate = resultSet.getTimestamp("order_date")
        val productName = resultSet.getString("product_name")
        val expiryDate = resultSet.getDate("expiry_date")
        val unitPrice = resultSet.getBigDecimal("unit_price")
        val quantity = resultSet.getInt("quantity")
        val discount = resultSet.getBigDecimal("discount")
        val finalPrice = resultSet.getBigDecimal("final_price")
        val paymentMethod = resultSet.getString("payment_method")

        // Process the retrieved data
        println(s"Order Date: $orderDate, Product Name: $productName, Expiry Date: $expiryDate, Unit Price: $unitPrice, Quantity: $quantity, Discount: $discount, Final Price: $finalPrice, Payment Method: $paymentMethod")
      }


      // Close the connection
      connection.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
