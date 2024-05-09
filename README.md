# Discount Rule Engine ETL Project

Welcome to the Discount Rule Engine ETL Project! This project is implemented in Scala and utilizes functional programming and OOP concepts to process orders, apply discount rules, and load data into a database.

## Overview

The project consists of three main components:

1. **Model Package**: Contains classes representing different phases of an order and discount rules.
2. **ETL Package**: Implements the ETL (Extract, Transform, Load) process with three layers: Extraction, Transformation, and Loading.
3. **Resources**: The CSV file that we will parse the data from.

### Model Package

The Model Package includes the following classes:

- `OrderForProcessing`: Represents the initial object created from parsing a CSV line.
- `OrderInProcess`: An intermediary object created during the transformation layer.
- `ProcessedOrder`: Represents the final processed order with discounts applied.
- `DiscountRule`: A class representing business rules for discounts.
- `DiscountRules`: A placeholder class for discount rules, including functions to qualify orders and calculate discounts.

### ETL Package

The ETL Package comprises three layers:

1. **Extraction Layer**: Parses CSV lines into `OrderForProcessing` objects.
2. **Transformation Layer**: Applies transformations to orders and calculates discounts based on business rules.
3. **Loading Layer (Database Loader)**: Loads processed orders into a database table.

### Resources

The Resources directory contains configuration files and other resources used by the project. This may include:
- `application.conf`: Configuration file for database connection parameters.
- `logging.xml`: Configuration file for logging settings.
- Other resource files used by the project.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository to your local machine.
2. Set up your database connection parameters in the `Main.scala` file.
3. Configure logging settings in the logging XML file.
4. Run the main class to start the ETL process.

## Dependencies

This project relies on the following dependencies:
- Scala version: "2.13.14"
- Java 8
- PostgreSQL JDBC Driver:
  - https://jdbc.postgresql.org/download/

## Tools and Technologies

- Programming Language: Scala 
- logging: SLF4J 
- Database: PostgreSQL
- IDE: IntelliJ Community Edition



## To add a new Business Rule

In the ```BusinessRules.scala``` file, you need  to do 4 things in

1. Create a qualifier function, which is a function that takes an ```OrderForProcessing``` object and returns a ```boolean```, based on which we'll decide whether this order is eligible for the discount or not.

2. Create a calculator function,  which is a function that takes an ```OrderForProcessing``` object and returns a discount as ```Double``` according to the business rules as well.

3. Create a ```discountRule``` object and give the following:

   - Description: String

   - Qualifier Function

   - Calculator Function

4. Add the newly created  ```discountRule``` object to the ``` allDiscountRules``` function

   ## Screenshots:
   ### 1. The Raw CSV 
   ![image](https://github.com/Attia0079/DiscountRuleEngine/assets/90058837/84ef68d1-08e9-4538-bbf3-7fb693584938)

   ### 2. The Output
   ![image](https://github.com/Attia0079/DiscountRuleEngine/assets/90058837/31708dac-b479-470e-bb47-f89761344349)

   ### 3. The Logs
   ![Screenshot 2024-05-09 222914](https://github.com/Attia0079/DiscountRuleEngine/assets/90058837/a6dee5a2-f592-4ea9-ae38-f8124ff75858)
   ![Screenshot 2024-05-09 223043](https://github.com/Attia0079/DiscountRuleEngine/assets/90058837/e5868be3-4051-44c1-8bfc-a359582dc418)
   ![Screenshot 2024-05-09 223112](https://github.com/Attia0079/DiscountRuleEngine/assets/90058837/fa347d3d-a791-4e1a-86b4-b2d501c066a8)

