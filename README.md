1. # Kaffa-Challenges

   This project consists of four main modules: a **document-validator**, **shape-calculator**, **simple-todo-list** with several utility components and **world-clock** that implements a Spring application called **WorldClockApplication**.

   ## Project Structure

   ### Application Modules

   The **document-validator**, **shape-calculator**, **simple-todo-list** contains utility classes and a task listing application. Here are the main components:

   1. **CNPJDocumentValidator**
   [validator.CNPJDocumentValidator](https://github.com/eduardowgmendes/kaffa-challenges/blob/main/kaffa-challenges/kaffa-challenges/document-validator/src/main/java/validator/CNPJDocumentValidator.java)
      - This class validates whether the CNPJ (Cadastro Nacional da Pessoa Jurídica) is correctly formatted according to the rules of Brazil's Receita Federal.
      - The implemented algorithm checks the last two digits (the "check digits") of the CNPJ by performing weight calculations as required for CNPJ validation.

   2. **RectangleShapeIntersectionCalculator**
   [intersection.RectangleShapeIntersectionCalculator](https://github.com/eduardowgmendes/kaffa-challenges/blob/main/kaffa-challenges/kaffa-challenges/shape-calculator/src/main/java/intersection/RectangleShapeIntersectionCalculator.java)
      - This class is responsible for calculating the intersection between two rectangles.
      - The presence of the `ShapeIntersectionCalculator` interface makes the system more flexible, allowing other geometric shapes to be checked for intersection by extending this interface and implementing the appropriate rules.

   3. **ApplicationExecutor**
   [todo.list.application.SimpleTodoListApplication](https://github.com/eduardowgmendes/kaffa-challenges/blob/main/kaffa-challenges/kaffa-challenges/simple-todo-list/src/main/java/todo/list/application/SimpleTodoListApplication.java)
      - A task listing application (To-Do List) with a graphical interface built using Swing. See UI implementation [application.ui.ApplicationUI](https://github.com/eduardowgmendes/kaffa-challenges/blob/main/kaffa-challenges/kaffa-challenges/simple-todo-list/src/main/java/todo/list/application/ui/ApplicationUI.java)
      - It allows users to create, list, and manage tasks. This application serves as an example of a simple desktop interface for task management.

   ### Spring Module

   The **world-clock** contains an application called **WorldClockApplication**, which is responsible for fetching and displaying the current date and time from the World Clock API. The application is built using the Spring framework and provides a REST API to return the date and time information.

   #### Main Features:
   - Queries the World Clock API to get the current UTC date and time.

   - If the query fails, the local JVM time is returned as a fallback.

   - API documentation with Swagger is available to ease integration.

     
    
   ## Prerequisites

   - Java 21 or higher
   - Maven 3.9.6 or higher
   - PostgreSQL 14.13 - Must to be installed

   

   ## How to Run Applications
   
  
   ### document-validator module

   To run the validator application:

   1. Navigate to the **document-validator** directory:
      ```bash
      cd document-validator
      ```

   2. Compile and run:

   ```bash
   mvn clean package
   ```
   3. Run document-validator-1.0-SNAPSHOT.jar
   
   ```bash
   cd target/
   java -jar document-validator-1.0-SNAPSHOT.jar "000.000.000/0000-00"
   ```

   ### shape-calculator module

   To run the shape intersection calculator:

   1. Navigate to the **shape-calculator** directory:
      ```bash
      cd shape-calculator
      ```

   2. Compile and run:

   ```bash
   mvn clean package
   ```
   3. Run shape-calculator-1.0-SNAPSHOT.jar passing as args <RectangleA> coordinates and <RectangleB> coordinates. 

	Each coordinate arg must to be in this pattern "0,0,0,0" without space after comma: "0,0,0,0" 
   
   ```bash
   cd target/
   java -jar shape-calculator-1.0-SNAPSHOT.jar "3,5,11,11" "7,2,13,7"
   ```

### simple-todo-list Module

To run the **simple-todo-list**:

   1. Navigate to the maven-module directory:

```bash
    cd simple-todo-list
```
   2. Compile and run:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="todo.list.application.ApplicationExecutor"
```

### world-clock Module

To run the **WorldClockApplication**:

1. Navigate to the **world-clock** module directory:

   ```bash
   cd world-clock
   ```

2. Compile and run:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.


## Testing

Tests can be executed with the following command:

```bash
cd kaffa-challenges/kaffa-challenges/kaffa-challenges
mvn test
```
