# Yahoo Finance Data Scraper

This Spring Boot project scrapes historical finance data from Yahoo Finance and stores it in an in-memory database (H2). The application includes a scheduled task to periodically scrape and update the forex data.

## About the Project

- **src/main/java**: Contains the Java source code for the application.
- **src/main/resources**: Contains application configuration files.
- **pom.xml**: Maven configuration file containing project dependencies and plugins.

## Maven Dependencies

- **Spring Boot**: Framework to build the application.
- **Jsoup**: Library to scrape and parse HTML.
- **H2 Database**: In-memory database for storing scraped data.
- **Lombok**: Reduces boilerplate code by generating getters, setters, etc.
- **Spring JPA**: Provides JPA-based data access layers.
- **Spring Boot DevTools**: Provides development-time features for a smoother development experience.

## API Endpoints

### 1. Post Forex Data

**Endpoint:** `POST localhost:8080/api/forex-data`

**Description:** This endpoint is used to trigger the scraping of forex data for specific currency pairs and time period. It can be accessed via any HTTP POST request tool.

**Request Parameters:**
- `from` (String): The currency code from which the exchange rate is to be fetched (e.g. "USD").
- `to` (String): The currency code to which the exchange rate is to be fetched (e.g. "INR").
- `period` (String): The period for the historical data to be fetched (e.g. "1W", "1M", "3M", "6M", "1Y"}.

### 2. Fetch All Data

**Endpoint:** `GET localhost:8080/api/fetch-all-data`

**Description:** This endpoint retrieves all the data stored in the database. It can be accessed via a web browser or any HTTP GET request tool.

## Accessing the H2 Database Console

To access the H2 database console, follow these steps:

1. **Start the Spring Boot application.**
   - Make sure the application is running.

2. **Open your browser.**

3. **Navigate to the H2 console:**
   - URL: `http://localhost:8080/h2-console`

4. **Login to the H2 console:**
   - **JDBC URL:** `jdbc:h2:mem:forexdata`
   - **Username:** `sa`
   - **Password:** *(leave this field empty)*

5. **Click on the "Connect" button.**

Now you can interact with the in-memory database directly from the H2 console.

## Getting Started

Follow these steps to set up and run the project on your local machine.

## Prerequisites

- Java 17 or later
- Maven 3.3.2 or later

### Clone the Repository

Start by cloning the repository to your local machine:

```bash
git clone https://github.com/mishra-abhii/Spring-Boot-Web-Scraper.git
```

## Run the Application through IntelliJ IDEA

After cloning the project, follow these steps to run the application:

1. **Open IntelliJ IDEA:**
   - Launch IntelliJ IDEA on your machine.

2. **Import the Project:**
   - Click on `File` > `New` > `Project from Existing Sources...`.
   - Navigate to the directory where you cloned the project.
   - Select the project's root folder and click `OK`.

3. **Ensure Maven Dependencies Are Loaded:**
   - IntelliJ should automatically detect the `pom.xml` file and download the necessary dependencies.
   - You can check the Maven tool window on the right-hand side to see if the dependencies are loaded.

4. **Build the Project:**
   - Once the project is imported, you can build it by going to `Build` > `Build Project`.

5. **Run the Spring Boot Application:**
   - In the `Project` explorer, navigate to `src/main/java/com/forexdata/scraper`.
   - Right-click on the `YahooScraperApplication.java` file.
   - Choose `Run 'YahooScraperApplication'`.
   - The Spring Boot application will start, and you should see the output in the Run console at the bottom of the IntelliJ IDEA window.

6. **Access the Application:**
   - Once the application is running, you can access it through the endpoints configured:
     - Use Postman or any other API testing tool to send a POST request to `http://localhost:8080/api/forex-data` 
       with the required parameters to get the scraped data as output.
     - Access `http://localhost:8080/api/fetch-all-data` in your browser to fetch all database records.

7. **Stop the Application:**
   - To stop the application, you can click the red square button in the Run console.

## Run the Application through Command Prompt/Terminal

Once you have cloned the repository, you need to build the project using Maven. Follow these steps:

1. Open your terminal or command prompt.
2. Navigate to the project directory:

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

    This command will:
    - Download all necessary dependencies.
    - Compile the project.

4. Run the Application

    ```bash
    mvn spring-boot:run
    ```

5. Access the Application:
   - Once the application is running, you can access it through the endpoints configured:
     - Use Postman or any other API testing tool to send a POST request to `http://localhost:8080/api/forex-data` 
       with the required parameters to get the scraped data as output.
     - Access `http://localhost:8080/api/fetch-all-data` in your browser to fetch all database records.    

5. Stop the Application
    - To stop a Spring Boot application running in the terminal, use the following command: Press `Ctrl + C`.
  
---

## Thanks for Using This Project!

If you found this project helpful, feel free to give it a star ⭐ on GitHub. 

For any questions or feedback, please don't hesitate to reach out!

Happy coding! 😊

