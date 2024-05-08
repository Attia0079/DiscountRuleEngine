## The Transformation Layer: ```OrderProcessing```

```OrderForProcessing => OrderInProcess => ProcessedOrder```

this is the layer where all the  transformation **magic** happens, it consists of a case object that has few functions:

-  ```round``` 

  it takes a double (to round) and and int (number of decimals points)

- ```prepareOrders``` 

  it takes an ```OrderForProcessing``` , gives it an ID and then returns it as an ```OrderInProcess```

- ```processOrders```

  it takes an ```OrderForProcessing```  and a list of ```DiscountRule```s and returns a ```ProcessedOrder```,  Let's see  How!

  First, the ```OrderForProcessing``` is converted to an ```OrderInProcess``` by Giving it an ID using the ```prepareOrders``` Function

  then it takes the  `OrderInProcess` starts to apply the qualifier function in the List of  ```DiscountRule```s and then takes the Ones where the qualifier retuned true only as another List of ```DiscountRule```s named ```Qualified Rules``` now this list is what we apply the Discount Calculator from to the  `OrderInProcess` returning a sorted List of Discounts, 

  * if the discounts are more than 2 we take the average of the top 2 discounts
  * if it's only one discount that was qualified from the ```Qualified Rules``` List we take it as is
  * if the order was not qualified for any discounts it returns ```0.0```

  and then it creates a ```processOrders``` object giving it the discount, and adding the following attributes:

  ```scala
  priceBeforeDiscount: Double,
  discount: Double,
  discountValue: Double,
  finalPrice: Double
  ```

