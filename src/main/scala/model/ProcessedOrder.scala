package model

import java.util.UUID

case class ProcessedOrder(
                           orderId: UUID,
                           orderTimestamp: String,
                           productName: String,
                           expiryDate: String,
                           quantity: Int,
                           unitPrice: Double,
                           priceBeforeDiscount: Double,
                           discount: Double,
                           discountValue: Double,
                           finalPrice: Double,
                           channel: String,
                           paymentMethod: String
                         )
