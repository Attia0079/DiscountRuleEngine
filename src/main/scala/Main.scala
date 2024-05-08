// the imports required
import etl.extraction.CSVParser.parseOrdersForProcessing
import etl.loading.DataBaseLoader.{getConnection, insertRecord}
import etl.transformation.OrderProcessing.processOrders
import model.DiscountRules
object Main {
  def main(args: Array[String]): Unit = {

    val discountRules = new DiscountRules().allDiscountRules()
    val ordersForProcessing = parseOrdersForProcessing("src/main/scala/resources/orders_data.csv")
    val processedOrders = ordersForProcessing.map(order => processOrders(order, discountRules))
    processedOrders.foreach(println)

    val url = "jdbc:postgresql://172.18.59.118:5432/sales_mart"
    val user = "root"
    val password = "root"
    val conn = getConnection(url, user, password)

    processedOrders.foreach(order => insertRecord(conn, order))
  }
}
