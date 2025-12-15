# 🌡️ Temperature Converter Web Application

A modern, feature-rich web application for converting temperatures between Celsius and Fahrenheit with history tracking and nickname support. Built with Spring Boot, PostgreSQL, and a sleek responsive UI.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.2-purple)

## ✨ Features

- 🔄 **Bidirectional Conversion**: Convert between Celsius and Fahrenheit with precise calculations
- 💾 **Smart History Saving**: Save conversions with optional custom nicknames for easy reference
- 🏷️ **Nickname Support**: Add meaningful names to your conversions (e.g., "Summer temperature", "Room temp")
- 📊 **History Management**: View, search, and manage all saved conversions
- 🗑️ **Flexible Deletion**: Remove individual items or clear all history at once
- 🌓 **Dark Mode**: Toggle between light and dark themes with smooth transitions
- 🎨 **Modern UI**: Beautiful, animated interface with gradient backgrounds
- 📱 **Fully Responsive**: Seamless experience across all devices and screen sizes
- 📚 **Swagger API Documentation**: Interactive API explorer with full documentation
- 🔐 **Environment Variables**: Secure credential management with `.env` file support
- ⚡ **Real-time Updates**: Instant feedback and smooth animations

## 🚀 Tech Stack

### Backend
- **Java 21** - Modern Java features
- **Spring Boot 3.4.0** - Application framework
- **Spring Data JPA** - Database abstraction
- **Spring Web** - REST API
- **PostgreSQL** - Relational database
- **Lombok** - Reduce boilerplate code
- **Swagger/OpenAPI 3** - API documentation

### Frontend
- **HTML5** - Semantic markup
- **CSS3** - Custom animations and dark mode
- **JavaScript (ES6+)** - Modern async/await patterns
- **Bootstrap 5.3.2** - Responsive framework
- **Bootstrap Icons** - Beautiful icon set
- **Google Fonts (Poppins)** - Modern typography

## 📋 Prerequisites

Before running this application, ensure you have:

- ☕ **Java 21** or higher ([Download](https://adoptium.net/))
- 📦 **Maven 3.6+** (or use included Maven wrapper)
- 🐘 **PostgreSQL 12+** ([Download](https://www.postgresql.org/download/))

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/TempConverter.git
cd TempConverter
```

### 2. Database Setup

#### Create Database

Open PostgreSQL terminal (psql) and create the database:

```sql
CREATE DATABASE tempconverter;
```

#### Configure Environment Variables

Create a `.env` file in the project root directory:

```bash
# Windows PowerShell
New-Item -Path .env -ItemType File

# Linux/Mac
touch .env
```

Add your database credentials to `.env`:

```properties
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/tempconverter
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password_here

# Server Configuration (optional)
SERVER_PORT=8080
```

**🔒 Security Note:** The `.env` file is automatically excluded from version control via `.gitignore`. Never commit sensitive credentials!

#### Configure IntelliJ IDEA to Use .env File

To make IntelliJ IDEA load environment variables from the `.env` file:

1. Install the **EnvFile** plugin:
   - Go to `File` → `Settings` → `Plugins`
   - Search for "EnvFile"
   - Install and restart IntelliJ

2. Configure Run Configuration:
   - Go to `Run` → `Edit Configurations...`
   - Select your Spring Boot application
   - Click on the `EnvFile` tab
   - Check "Enable EnvFile"
   - Click `+` → Select your `.env` file
   - Apply and OK

**Alternative (Without Plugin):**
- Manually set environment variables in Run Configuration:
  - `Run` → `Edit Configurations...`
  - Add environment variables in the "Environment variables" field

### 3. Build the Application

Using Maven wrapper (recommended):

```bash
# Windows
.\mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

Or using Maven directly:

```bash
mvn clean install
```

### 4. Run the Application

#### Option 1: Using Maven Wrapper (Recommended)

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Option 2: Using Maven

```bash
mvn spring-boot:run
```

#### Option 3: Run as JAR

```bash
# Build JAR
mvn clean package -DskipTests

# Run JAR
java -jar target/TempConverter-0.0.1-SNAPSHOT.jar
```

### 5. Verify Installation

The application will start on `http://localhost:8080`. You should see console output indicating successful startup:

```
Started TempConverterApplication in X.XXX seconds
```

## 🎯 Usage Guide

### Web Interface

#### Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

#### Converting Temperatures

1. **Enter Temperature Value**
   - Type a number in the input field (decimals supported)
   - Example: `25.5`, `100`, `-40`

2. **Select Unit**
   - Choose **Celsius (°C)** or **Fahrenheit (°F)** as the source unit
   - The conversion will automatically convert to the opposite unit

3. **Convert**
   - Click the "Convert" button or press **Enter**
   - The result appears instantly with the conversion formula

#### Saving to History

After converting a temperature:

1. **Add Nickname (Optional)**
   - Enter a memorable name in the "Nickname" field
   - Examples: "Summer temperature", "Oven setting", "Body temp"
   - Leave blank if no nickname needed

2. **Save**
   - Click the "Save to History" button
   - The conversion is saved to the database with timestamp

#### Managing History

- **View History**: All saved conversions appear in the right panel
- **Nicknames Display**: Shows with a tag icon (🏷️) above the conversion
- **Delete Single Item**: Click the trash icon on any history item
- **Clear All History**: Click the "Clear All" button to remove all items
- **Order**: History is sorted by most recent first

#### Dark Mode

- Click the **moon/sun icon** in the top-right corner
- Preference is saved in browser localStorage
- All colors and contrasts are optimized for both modes

#### API Documentation

- Click the **API documentation icon** (📄) in the top-right corner
- Opens Swagger UI in a new tab for interactive API testing

### API Endpoints

The application exposes a RESTful API with the following endpoints:

#### 📚 Interactive API Documentation (Swagger)

**Access Swagger UI:**
```
http://localhost:8080/swagger-ui/index.html
```

**OpenAPI Specification:**
- JSON: `http://localhost:8080/api-docs`
- YAML: `http://localhost:8080/api-docs.yaml`

**Features:**
- ✅ Interactive "Try it out" functionality
- ✅ Complete request/response examples
- ✅ Schema definitions and validation rules
- ✅ Export to Postman, Insomnia, etc.

---

#### 1. Convert Temperature

**Endpoint:** `POST /api/temperature/convert`

**Description:** Converts a temperature value from Celsius to Fahrenheit or vice versa.

**Request Body:**
```json
{
  "value": 25.5,
  "fromUnit": "CELSIUS"
}
```

**Response:**
```json
{
  "inputValue": 25.5,
  "inputUnit": "CELSIUS",
  "outputValue": 77.9,
  "outputUnit": "FAHRENHEIT",
  "formula": "°F = (°C × 9/5) + 32"
}
```

#### 2. Save Conversion to History

**Endpoint:** `POST /api/temperature/save`

**Description:** Saves a conversion to the database with an optional nickname.

**Request Body:**
```json
{
  "inputValue": 25.5,
  "inputUnit": "CELSIUS",
  "outputValue": 77.9,
  "outputUnit": "FAHRENHEIT",
  "nickname": "Summer temperature"
}
```

**Response:**
```json
{
  "id": 1,
  "inputValue": 25.5,
  "inputUnit": "CELSIUS",
  "outputValue": 77.9,
  "outputUnit": "FAHRENHEIT",
  "nickname": "Summer temperature",
  "timestamp": "2025-12-15T10:30:45"
}
```

#### 3. Get Conversion History

**Endpoint:** `GET /api/temperature/history`

**Description:** Retrieves all saved conversions, ordered by most recent first.

**Response:**
```json
[
  {
    "id": 2,
    "inputValue": 100,
    "inputUnit": "CELSIUS",
    "outputValue": 212,
    "outputUnit": "FAHRENHEIT",
    "nickname": "Boiling point",
    "timestamp": "2025-12-15T11:00:00"
  },
  {
    "id": 1,
    "inputValue": 25.5,
    "inputUnit": "CELSIUS",
    "outputValue": 77.9,
    "outputUnit": "FAHRENHEIT",
    "nickname": "Summer temperature",
    "timestamp": "2025-12-15T10:30:45"
  }
]
```

#### 4. Delete Single History Item

**Endpoint:** `DELETE /api/temperature/history/{id}`

**Description:** Deletes a specific conversion from history.

**Example:**
```http
DELETE /api/temperature/history/1
```

**Response:** `200 OK`

#### 5. Clear All History

**Endpoint:** `DELETE /api/temperature/history`

**Description:** Deletes all conversions from the database.

**Response:** `200 OK`

## 🔢 Conversion Formulas

The application uses the following standard formulas:

### Celsius to Fahrenheit
```
°F = (°C × 9/5) + 32
```

**Example:**
- 0°C = 32°F (Freezing point)
- 100°C = 212°F (Boiling point)

### Fahrenheit to Celsius
```
°C = (°F - 32) × 5/9
```

**Example:**
- 32°F = 0°C (Freezing point)
- 212°F = 100°C (Boiling point)

## 📁 Project Structure

```
TempConverter/
├── src/
│   ├── main/
│   │   ├── java/com/chamage/tempconverter/
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java          # Swagger configuration
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java         # Serves static content
│   │   │   │   └── TemperatureController.java  # REST API endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ConversionRequest.java      # Convert request DTO
│   │   │   │   ├── ConversionResponse.java     # Convert response DTO
│   │   │   │   └── SaveConversionRequest.java  # Save request DTO
│   │   │   ├── model/
│   │   │   │   └── Conversion.java             # JPA entity
│   │   │   ├── repository/
│   │   │   │   └── ConversionRepository.java   # Spring Data JPA
│   │   │   ├── service/
│   │   │   │   └── TemperatureService.java     # Business logic
│   │   │   └── TempConverterApplication.java   # Main application
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css               # Custom styles + dark mode
│   │       │   ├── js/
│   │       │   │   └── app.js                  # Frontend logic
│   │       │   └── index.html                  # Main UI
│   │       └── application.properties          # Spring configuration
│   └── test/
│       └── java/com/chamage/tempconverter/
│           └── TempConverterApplicationTests.java
├── .env                                         # Environment variables (not in git)
├── .env.example                                 # Example environment file
├── .gitignore                                   # Git ignore rules
├── mvnw                                         # Maven wrapper (Unix)
├── mvnw.cmd                                     # Maven wrapper (Windows)
├── pom.xml                                      # Maven dependencies
└── README.md                                    # This file
```

## 🐛 Troubleshooting

### Database Connection Issues

**Problem:** Application fails to connect to PostgreSQL

**Solutions:**

1. **Verify PostgreSQL is running:**
   ```bash
   # Windows
   Get-Service postgresql*
   
   # Linux
   sudo systemctl status postgresql
   
   # Mac
   brew services list
   ```

2. **Check database exists:**
   ```sql
   -- In psql terminal
   \l
   ```

3. **Verify credentials in `.env` file**
   - Ensure username and password are correct
   - Check database URL and port

4. **Test connection manually:**
   ```bash
   psql -U postgres -d tempconverter
   ```

### Port Already in Use

**Problem:** Port 8080 is occupied by another application

**Solution:** Change the port in `.env`:
```properties
SERVER_PORT=8081
```

Or temporarily override:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Maven Build Errors

**Problem:** Build fails with dependency errors

**Solutions:**

1. **Clean and rebuild:**
   ```bash
   mvn clean install -U
   ```

2. **Skip tests if failing:**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Clear Maven cache:**
   ```bash
   # Windows
   Remove-Item -Recurse -Force "$env:USERPROFILE\.m2\repository"
   
   # Linux/Mac
   rm -rf ~/.m2/repository
   ```

### Environment Variables Not Loading

**Problem:** Application can't read `.env` file

**Solutions:**

1. **Verify .env file exists in project root**
2. **Check IntelliJ EnvFile plugin is installed and configured**
3. **Manually set environment variables in Run Configuration**
4. **Use spring-boot-dotenv dependency** (already configured in `pom.xml`)

### Dark Mode Not Persisting

**Problem:** Dark mode preference resets on page reload

**Solution:** Clear browser localStorage and try again:
```javascript
// In browser console
localStorage.clear();
location.reload();
```

### Swagger UI Not Loading

**Problem:** Cannot access Swagger documentation

**Solution:** Ensure you're using the correct URL:
```
http://localhost:8080/swagger-ui/index.html
```

## 🚧 Future Enhancements

- [ ] **Kelvin Support**: Add third temperature scale
- [ ] **Batch Conversion**: Convert multiple temperatures at once
- [ ] **Export History**: Download as CSV, JSON, or PDF
- [ ] **Search & Filter**: Search history by nickname or date range
- [ ] **User Authentication**: Personal accounts with separate histories
- [ ] **Temperature Charts**: Visualize conversion trends
- [ ] **Unit Preferences**: Set default units
- [ ] **Keyboard Shortcuts**: Quick actions with hotkeys
- [ ] **Share Conversions**: Generate shareable links
- [x] **Dark Mode Toggle** ✅
- [x] **Nickname Support** ✅
- [x] **Swagger Documentation** ✅

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the **MIT License**.

## 👨‍💻 Author

**Built with ❤️ by Chamage**

- Using Spring Boot, PostgreSQL, and Bootstrap
- Designed for simplicity, performance, and great UX

## 📞 Support

Having issues or questions?

- 📧 Email: your-email@example.com
- 🐛 Report bugs via GitHub Issues
- 💬 Discussions on GitHub

---

**⭐ If you find this project useful, please consider giving it a star!**

