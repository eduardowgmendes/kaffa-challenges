1. # Kaffa-Challenges

   This project consists of two main modules: a **Maven Module** with several utility components and a **Spring Module** that implements a Spring application called **WorldClockApplication**.

   ## Project Structure

   ### Maven Module

   The **Maven Module** contains utility classes and a task listing application. Here are the main components:

   1. **document.format.validator.CNPJDocumentValidator**
      - This class validates whether the CNPJ (Cadastro Nacional da Pessoa Jurídica) is correctly formatted according to the rules of Brazil's Receita Federal.
      - The implemented algorithm checks the last two digits (the "check digits") of the CNPJ by performing weight calculations as required for CNPJ validation.

   2. **shapes.intersection.RectangleShapeIntersectionCalculator**
      - This class is responsible for calculating the intersection between two rectangles.
      - The presence of the `ShapeIntersectionCalculator` interface makes the system more flexible, allowing other geometric shapes to be checked for intersection by extending this interface and implementing the appropriate rules.

   3. **todo.list.application.ApplicationExecutor**
      - A task listing application (To-Do List) with a graphical interface built using Swing.
      - It allows users to create, list, and manage tasks. This application serves as an example of a simple desktop interface for task management.

   ### Spring Module

   The **Spring Module** contains an application called **WorldClockApplication**, which is responsible for fetching and displaying the current date and time from the World Clock API. The application is built using the Spring framework and provides a REST API to return the date and time information.

   #### Main Features:
   - Queries the World Clock API to get the current UTC date and time.

   - If the query fails, the local JVM time is returned as a fallback.

   - API documentation with Swagger is available to ease integration.

     
     

   ## Prerequisites

   - Java 21 or higher
   - Maven 3.9.6 or higher

   

   ## How to Run

   ### Maven Module

   To run the validation, shape intersection, or task listing applications:

   1. Navigate to the **maven-module** directory:
      ```bash
      cd maven
      ```

   2. Compile and run:

   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="todo.list.application.ApplicationExecutor"
   ```

   

### Spring Module

To run the **WorldClockApplication**:

1. Navigate to the **spring-module** directory:

   ```bash
   cd spring
   ```

2. Compile and run:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.



## Testing

Tests can be executed with the following command:

```bash
mvn test
```
