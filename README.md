
# Payroll Processing System

This project is a Spring Boot application designed to manage employee records uploaded via CSV or text files. The application provides endpoints for uploading employee data and generating reports in JSON format. 

## Features

- Upload multiple CSV or text files containing employee records.
- Process records and generate reports dynamically.
- Return nested JSON output for reports through a GET API.


## Endpoints

### 1. Upload Employee Records
**URL:** `/data-orb/upload`  
**Method:** `POST`  
**Description:** Accepts multiple CSV or text files for processing.


### 2. Get Reports
**URL:** `/data-orb/records`  
**Method:** `GET`  
**Description:** Returns all reports in nested JSON format.

Example Response:
```json
{
    "Total Employees": 10,
    "Monthly Joins": {
        "01-2024": [
            {
                "empID": "E001",
                "firstName": "John",
                "lastName": "Doe",
                "designation": "Developer",
                "event": "ONBOARD",
                "eventDate": "01-2024",
                "value": ""
            }
        ]
    },
    "Monthly Exits": {
        "02-2024": [
            {
                "empID": "E002",
                "firstName": "Jane",
                "lastName": "Smith",
                "designation": "Manager",
                "event": "EXIT",
                "eventDate": "02-2024",
                "value": ""
            }
        ]
    },
    "Monthly Salary Report": {
        "01-2024": 50000.0,
        "02-2024": 45000.0
    }
}
```

## Prerequisites

- Java 17+
- Maven 3.6+

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/employee-management.git
   cd employee-management
   ```
2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
3. Access the application at `http://localhost:8080`.
