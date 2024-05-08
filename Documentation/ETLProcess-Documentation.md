# ETL - Documentation

The ETL Process obviously has 3 Layers

- Extraction: this is where the data is parsed from csv to be an object with attributes
- Transformation: this is where the magic happens and order goes in and eventually an order goes out
- Loading: this is where the data is loaded to the database table

**now let's take them a step by step**



## The Extraction Layer: ```CSVParser```

``` Line => OrderForProcessing```

this layer consists of a case object that has only one function ```parseOrdersForProcessing```

that takes a line from the csv file, parse it into an ```OrderForProcessing``` Object

---

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

---

## The Loading Layer: ```DataBaseLoader```

```ProcessedOrder => Database Table Record```

this layer consists of a case object that has some functions:

there are 2 main functions:

- ```getConnection```  this function takes in the connection ```url, username, password```  and returns a ```Connection``` object
- ```insertRecord``` this function takes in a ```Connection``` and ```ProcessedOrder``` Inserts the order in the Database Table

and 2 helping functions:

- ```stringToTimestamp``` this function takes in an ISO formatted Timestamp string and returns it as a ```Timestamp``` Object
- ```stringToDate``` this function takes in a Date formatted string and returns it as a ```Date``` Object

---

