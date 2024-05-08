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

