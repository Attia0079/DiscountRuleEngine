package model

import java.util.UUID

case class ProcessedOrder(orderId: UUID, orderTimestamp: String, productName: String, expiryDate: String, quantity: Int, unitPrice: Double, discount: Double, discountValue: Double, priceBeforeDiscount: Double, finalPrice: Double, channel: String, paymentMethod: String)
