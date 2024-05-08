// the imports required
import com.typesafe.scalalogging.Logger
import etl.extraction.CSVParser.parseOrdersForProcessing
import etl.loading.DataBaseLoader.{getConnection, insertRecord}
import etl.transformation.OrderProcessing.processOrders
import model.DiscountRules

object Main {

  private val logger = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {

    try {
      logger.info("Starting application")

      val discountRules = new DiscountRules().allDiscountRules()
      logger.debug("Discount rules loaded Successfully")

      val ordersForProcessing = parseOrdersForProcessing("src/main/scala/resources/orders_data.csv")
      logger.debug("Orders parsed for processing Successfully")

      val processedOrders = ordersForProcessing.map(order => processOrders(order, discountRules))
      logger.debug("Orders processed Successfully")


      val url = "jdbc:postgresql://172.18.59.118:5432/sales_mart"
      val user = "root"
      val password = "root"
      val conn = getConnection(url, user, password)
      logger.debug("Database connection established")


      processedOrders.foreach(order => insertRecord(conn, order))
      logger.info("All records inserted successfully")
      logger.info("Discount Rule Engine application has successfully finished its job ")

    } catch {
      case ex: Exception =>
        logger.error("An error occurred in the main method", ex)
    }
  }
}
