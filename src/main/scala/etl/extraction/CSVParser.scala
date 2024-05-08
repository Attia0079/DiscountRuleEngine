package etl.extraction
import com.typesafe.scalalogging.Logger
import model.OrderForProcessing
import scala.io.Source


case object CSVParser{
  private val logger = Logger(getClass.getName)

  // Define a method to read lines from a CSV file and convert them into orders for processing
  def parseOrdersForProcessing(filePath: String): List[OrderForProcessing] = {
    try {
      val lines = Source.fromFile(filePath).getLines().toList.tail // Skip header line
      val orders = lines.map { l =>
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
      logger.info(s"Parsing orders from file $filePath completed successfully.")
      orders
    } catch {
      case ex: Exception =>
        logger.error(s"Error occurred while parsing orders from file $filePath", ex)
        Nil // Return empty list in case of error
    }
  }
}
