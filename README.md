# Temperature Converter Web Application

A modern, slick web application for converting temperatures between Celsius and Fahrenheit. Built with Spring Boot, PostgreSQL, HTML, CSS, JavaScript, and Bootstrap.

## Features

- 🌡️ **Bidirectional Conversion**: Convert between Celsius and Fahrenheit
- 💾 **Save History**: Optional saving of conversions to PostgreSQL database
- 📊 **View History**: Display all saved conversions with timestamps
- 🗑️ **Delete History**: Remove individual items or clear all history
- 🎨 **Modern UI**: Beautiful, responsive design with animations
- 📱 **Mobile Friendly**: Works seamlessly on all devices

## Tech Stack

### Backend
- **Java 21**
- **Spring Boot 4.0.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**

### Frontend
- **HTML5**
- **CSS3** with animations
- **JavaScript (ES6+)**
- **Bootstrap 5.3.2**
- **Bootstrap Icons**

## Prerequisites

Before running this application, make sure you have:

1. **Java 21** or higher installed
2. **Maven** installed
3. **PostgreSQL** installed and running

## Database Setup

1. Install PostgreSQL if not already installed
2. Create a database named `tempconverter`:

```sql
CREATE DATABASE tempconverter;
```

3. Create a `.env` file in the project root directory:

```bash
# Copy the example file
cp .env.example .env
```

4. Update the `.env` file with your database credentials:

```properties
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/tempconverter
DB_USERNAME=postgres
DB_PASSWORD=your_password_here

# Server Configuration
SERVER_PORT=8080
```

**Important:** The `.env` file contains sensitive credentials and should never be committed to version control. It's already included in `.gitignore`.

The application will automatically create the required tables on startup using Hibernate.

## Installation & Running

### Option 1: Using Maven Wrapper (Recommended)

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Option 2: Using Maven

```bash
mvn spring-boot:run
```

### Option 3: Build and Run JAR

```bash
# Build
mvn clean package

# Run
java -jar target/TempConverter-0.0.1-SNAPSHOT.jar
```

## Accessing the Application

Once the application is running, open your browser and navigate to:

```
http://localhost:8080
```

## Usage

1. **Convert Temperature**:
   - Enter a temperature value in the input field
   - Select the unit you want to convert FROM (Celsius or Fahrenheit)
   - Click the "Convert" button or press Enter
   - The result will be displayed with the conversion formula

2. **Save to History**:
   - Toggle the "Save to History" switch ON before converting
   - The conversion will be saved to the database
   - View saved conversions in the History panel

3. **View History**:
   - All saved conversions appear in the right panel
   - Each entry shows the conversion and timestamp
   - History is ordered by most recent first

4. **Delete History**:
   - Click the trash icon on individual items to delete them
   - Click "Clear All" button to remove all history

## API Endpoints

The application provides the following REST API endpoints:

### Convert Temperature
```http
POST /api/temperature/convert
Content-Type: application/json

{
    "value": 100,
    "fromUnit": "CELSIUS",
    "saveToHistory": true
}
```

### Get Conversion History
```http
GET /api/temperature/history
```

### Delete Single History Item
```http
DELETE /api/temperature/history/{id}
```

### Clear All History
```http
DELETE /api/temperature/history
```

## Conversion Formulas

- **Celsius to Fahrenheit**: °F = (°C × 9/5) + 32
- **Fahrenheit to Celsius**: °C = (°F - 32) × 5/9

## Project Structure

```
TempConverter/
├── src/
│   ├── main/
│   │   ├── java/com/chamage/tempconverter/
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   └── TemperatureController.java
│   │   │   ├── dto/
│   │   │   │   ├── ConversionRequest.java
│   │   │   │   └── ConversionResponse.java
│   │   │   ├── model/
│   │   │   │   └── Conversion.java
│   │   │   ├── repository/
│   │   │   │   └── ConversionRepository.java
│   │   │   ├── service/
│   │   │   │   └── TemperatureService.java
│   │   │   └── TempConverterApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   └── app.js
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Troubleshooting

### Database Connection Issues

If you encounter database connection errors:

1. Verify PostgreSQL is running:
   ```bash
   # Windows
   Get-Service postgresql*
   
   # Linux
   sudo systemctl status postgresql
   ```

2. Check if the database exists:
   ```sql
   \l  -- List all databases in psql
   ```

3. Verify credentials in `application.properties`

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:

```properties
server.port=8081
```

### Build Errors

If you encounter build errors:

```bash
# Clean and rebuild
mvn clean install

# Skip tests if needed
mvn clean install -DskipTests
```

## Features to Add (Future Enhancements)

- [ ] Add Kelvin temperature scale
- [x] Export history to CSV/JSON
- [ ] User authentication and personal history
- [x] Dark mode toggle
- [ ] Batch conversion
- [ ] Temperature unit preferences
- [ ] Charts and statistics

## License

This project is open source and available under the MIT License.

## Author

Built with ❤️ using Spring Boot and Bootstrap

## Support

For issues or questions, please create an issue in the repository.

