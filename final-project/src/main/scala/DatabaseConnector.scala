import java.sql._

object DatabaseConnector {
  // Method to establish a connection to the database
  def getConnection(url: String, user: String, password: String): Connection = {
    Class.forName("org.postgresql.Driver")
    DriverManager.getConnection(url, user, password)
  }

  // Method to close the database connection
  def closeConnection(connection: Connection): Unit = {
    if (connection != null) {
      connection.close()
    }
  }
}
