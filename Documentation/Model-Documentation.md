# The Model Documentation

The Model Package has few Models that well cover in this module

## ```OrderForProcessing```

the first Object created from the parsing of the csv line, it's the output of the **Extraction Layer**  in the ETL Process

it has the following attributes:

```scala
orderTimestamp: String,
productName: String,
expiryDate: String,
quantity: Int,
unitPrice: Double,
channel: String,
paymentMethod: String
```

## ```OrderInProcess```

this is an Intermediary Object created in the **Transformation Layer** it's basically an ```orderForProcessing`` with an ID

here are it's attributes:

```scala
orderId: UUID,
orderTimestamp: String,
productName: String,
expiryDate: String,
quantity: Int,
unitPrice: Double,
channel: String,
paymentMethod: String
```

## ```ProcessedOrder```

this is the final Object and the Output of the **Transformation Layer** in the ETL Process

it has the discount calculated based on the business discount rules, here are it's attributes:

```scala
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
```



## ```DiscountRule```

this is a class created as a **Representative** for the Business Rules for the Discounts, it has 3 attributes:

```scala
description: String,
qualifier: OrderInProcess => Boolean,
calculator: OrderInProcess => Double
```



## ```DiscountRules``` 

this is a **Place Holder** class for the Business Discount Rules that will be applied on orders later

it has no attributes and one main function which is ```allDiscountRules``` which takes nothing and returns a list of all the ```DiscountRule``` objects available in the class

also it has more functions but that was the main one, now let's go over the other functions in the class:

according to the business requirements, each Business Rule must have 3 things

- Description: a description about the discount rule and if any notes are available.
- Qualifier: a function that takes in the order and returns a ```boolean``` depending the order passed to it if it's qualified or not 
- Calculator: a function that takes in the order and returns a ```Double``` referring to the discount calculated based on the business rules

and any change in the Business Rules should reflect only in this class ```DiscountRules``` , if the Business decided to add a new rule

we will create in this class a new ```DiscountRule``` object and give it it's data, a description, a qualifier and a calculator functions that follow the logic of the business.

now after explaining everything, we have 3 types functions in the class



1. ### ```discountRuleQualifier``` s 

   these functions take in order, checks if it's qualified to get a discount and returns a ```Boolean``` 

2. ### ```discountRuleCalculator```s

   these functions take in order and calculate discount returning it as a ```Double```

3. ### intermediary Functions

   these are just helper functions that used to do some transformations needed for the calculations,

   ​	``` getDateDifference```

   ​	this function takes in 2 dates start date and end date as string

   ​	and return the difference in days between them

   