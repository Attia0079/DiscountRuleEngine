// the imports required
import etl.extraction.CSVParser.parseOrdersForProcessing
import etl.transformation.OrderProcessing.processOrders
import model.DiscountRules
object Main extends App{

//  val discountRules: List[DiscountRule] = allDiscountRules()
  val discountRules = new DiscountRules().allDiscountRules()

  val ordersForProcessing = parseOrdersForProcessing("src/main/scala/resources/TRX1000.csv")
  val processedOrders = ordersForProcessing.map(order => processOrders(order, discountRules))
  processedOrders.foreach(println)
}
