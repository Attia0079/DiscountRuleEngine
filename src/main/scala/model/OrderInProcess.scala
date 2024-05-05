package model
import java.util.UUID

case class OrderInProcess(orderId: UUID, orderTimestamp: String, productName: String, expiryDate: String, quantity: Int, unitPrice: Double, channel: String, paymentMethod: String)
