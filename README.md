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

## Prerequisites

- Java 21 or later
- Maven 3.3.2 or later

## API Endpoints

### 1. Post Forex Data

**Endpoint:** `POST /api/forex-data`

**Description:** This endpoint is used to trigger the scraping of forex data for specific currency pairs and time period. It can be accessed via any HTTP POST request tool.

**Request Parameters:**
- `from` (String): The currency code from which the exchange rate is to be fetched (e.g. "USD").
- `to` (String): The currency code to which the exchange rate is to be fetched (e.g. "INR").
- `period` (String): The period for the historical data to be fetched (e.g. "1W", "1M", "3M", "6M", "1Y"}.

### 2. Fetch All Data

**Endpoint:** `GET /api/fetch-all-data`

**Description:** This endpoint retrieves all the data stored in the database. It can be accessed via a web browser or any HTTP GET request tool.

## Getting Started

Follow these steps to set up and run the project on your local machine.

### Clone the Repository

Start by cloning the repository to your local machine:

```bash
git clone https://github.com/yourusername/forex-data-scraper.git
```

## Build the Project

Once you have cloned the repository, you need to build the project using Maven. Follow these steps:

1. Open your terminal or command prompt.
2. Navigate to the project directory:

    ```bash
    cd forex-data-scraper
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

    This command will:
    - Download all necessary dependencies.
    - Compile the project.

### Run the Application

After the project is successfully built, you can run the Spring Boot application:

```bash
mvn spring-boot:run
```

