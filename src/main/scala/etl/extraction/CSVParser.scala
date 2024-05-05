package etl.extraction
import model.OrderForProcessing
import scala.io.Source

case object CSVParser{
// Define a method to read lines from a CSV file and convert them into orders for processing
  def parseOrdersForProcessing(filePath: String): List[OrderForProcessing] = {
    val lines = Source.fromFile(filePath).getLines().toList.tail // Skip header line
    lines.map { l =>
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
  }

}
