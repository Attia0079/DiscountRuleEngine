package model

case class OrderForProcessing(
                               orderTimestamp: String,
                               productName: String,
                               expiryDate: String,
                               quantity: Int,
                               unitPrice: Double,
                               channel: String,
                               paymentMethod: String)