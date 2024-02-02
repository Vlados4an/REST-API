# RestProjectUp

RestProjectUp is a robust RESTful API developed using Spring Boot. It provides a comprehensive platform for managing measurements and sensors, offering a range of functionalities from creating and updating to retrieving specific data.

## Features

- **Manage Measurements**: Enables Create, Read, Update, and Delete (CRUD) operations on Measurements.
- **Manage Sensors**: Facilitates CRUD operations on Sensors.
- **Retrieve Measurement by ID**: Allows retrieval of specific measurements using their ID.
- **Retrieve Count of Rainy Days**: Provides the ability to get the count of rainy days based on measurements.
- **Register a Sensor**: Offers functionality to register a new sensor to the system.

## Technologies Used

- Java
- Spring Boot
- Maven
- PostgreSQL
- Lombok
- MapStruct
- Hibernate

## Setup

1. Clone the repository.
2. Install PostgreSQL and create a database named `your_database`.
3. Update `src/main/resources/application.properties` with your PostgreSQL username and password.
4. Run the application using the command `mvn spring-boot:run`.

## API Endpoints

- `/measurements`: GET all measurements, POST a new measurement.
- `/measurements/{id}`: GET a specific measurement by its ID.
- `/measurements/rainyDaysCount`: GET the count of rainy days.
- `/sensors/registration`: POST to register a new sensor.

## Project Structure

The project adheres to a typical Spring Boot project structure:

- `src/main/java/ru/erma/restprojectup`: Contains the main application classes.
- `src/main/java/ru/erma/restprojectup/controllers`: Houses the controller classes.
- `src/main/java/ru/erma/restprojectup/services`: Contains the service classes.
- `src/main/java/ru/erma/restprojectup/repositories`: Holds the repository interfaces.
- `src/main/java/ru/erma/restprojectup/models`: Contains the entity classes.
- `src/main/java/ru/erma/restprojectup/dto`: Contains the data transfer object (DTO) classes.
- `src/main/java/ru/erma/restprojectup/util`: Contains utility classes for exception handling.
- `src/main/resources/application.properties`: Contains the application configuration properties.
- `pom.xml`: Contains the project's Maven configuration.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Contact

- Email: ssvetlaa235@gmail.com
- Telegram: @evlad03
