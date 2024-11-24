
# User Management Application

This is a Spring Boot application for managing user profiles, authentication, and authorization.

## Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- Spring Boot 2.5.4 or higher

## Getting Started

### Clone the repository

```sh
git clone https://github.com/your-repo/user-management.git
cd user-management
```

### Build the project

```sh
mvn clean install
```

### Run the application

```sh
mvn spring-boot:run
```

### Run the application with Docker

To run the application using Docker, follow these steps:

1. **Build the Docker image**

  ```sh
  docker build -t user-management .
  ```

2. **Run the Docker container**

  ```sh
  docker run -p 8081:8081 user-management
  ```

3. **Stop the Docker container**

  To stop the running container, use the following command:

  ```sh
  docker stop <container_id>
  ```

  Replace `<container_id>` with the actual container ID, which you can find using:

  ```sh
  docker ps
  ```

## API Endpoints

### Authentication

#### Sign Up

- **URL:** `/auth/signup`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "User signed up successfully",
    "data": {
      "userId": 1,
      "email": "user@example.com"
    }
  }
  ```

#### Sign In

- **URL:** `/auth/signIn`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "User signed in successfully",
    "data": {
      "token": "jwt-token",
      "refreshToken": "refresh-token"
    }
  }
  ```

#### Refresh Token

- **URL:** `/auth/token/refresh`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "refreshToken": "refresh-token"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "Token refreshed successfully",
    "data": {
      "token": "new-jwt-token"
    }
  }
  ```

### User Management

#### Get All User Profiles

- **URL:** `/users`
- **Method:** `GET`
- **Response:**
  ```json
  [
    {
      "userId": 1,
      "email": "user1@example.com",
      "postalNumber": "12345",
      "phoneNumber": "123-456-7890",
      "addressPart1": "123 Main St",
      "addressPart2": "Apt 4B",
      "addressPart3": "Springfield"
    },
    {
      "userId": 2,
      "email": "user2@example.com",
      "postalNumber": "67890",
      "phoneNumber": "098-765-4321",
      "addressPart1": "456 Elm St",
      "addressPart2": "Suite 5A",
      "addressPart3": "Shelbyville"
    }
  ]
  ```

#### Get User Profile by ID

- **URL:** `/users/{id}/profile`
- **Method:** `GET`
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "User profile retrieved successfully",
    "data": {
      "userId": 1,
      "email": "user@example.com",
      "postalNumber": "12345",
      "phoneNumber": "123-456-7890",
      "addressPart1": "123 Main St",
      "addressPart2": "Apt 4B",
      "addressPart3": "Springfield"
    }
  }
  ```

#### Create User Profile

- **URL:** `/users`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "postalNumber": "12345",
    "phoneNumber": "123-456-7890",
    "addressPart1": "123 Main St",
    "addressPart2": "Apt 4B",
    "addressPart3": "Springfield"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 201,
    "message": "User profile created successfully",
    "data": {
      "userId": 1,
      "email": "user@example.com",
      "postalNumber": "12345",
      "phoneNumber": "123-456-7890",
      "addressPart1": "123 Main St",
      "addressPart2": "Apt 4B",
      "addressPart3": "Springfield"
    }
  }
  ```

#### Update User Profile by ID

- **URL:** `/users/{id}/profile`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "postalNumber": "67890",
    "phoneNumber": "098-765-4321",
    "addressPart1": "456 Elm St",
    "addressPart2": "Suite 5A",
    "addressPart3": "Shelbyville"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "User profile updated successfully",
    "data": {
      "userId": 1,
      "email": "user@example.com",
      "postalNumber": "67890",
      "phoneNumber": "098-765-4321",
      "addressPart1": "456 Elm St",
      "addressPart2": "Suite 5A",
      "addressPart3": "Shelbyville"
    }
  }
  ```

#### Delete User Profile by ID

- **URL:** `/users/{id}/profile`
- **Method:** `DELETE`
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "User profile deleted successfully"
  }
  ```

#### Change Password

- **URL:** `/users/{id}/password`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "oldPassword": "old-password",
    "newPassword": "new-password"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "Password changed successfully"
  }
  ```

#### Forgot Password

- **URL:** `/users/forgot-password`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "user@example.com"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "Forgot password process initiated"
  }
  ```

#### Verify Forgot Password

- **URL:** `/users/forgot-password/verify`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "verificationCode": "123456",
    "newPassword": "new-password"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "Forgot password process verified"
  }
  ```

#### Verify Email

- **URL:** `/users/verify-email`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "verificationCode": "123456"
  }
  ```
- **Response:**
  ```json
  {
    "statusCode": 200,
    "message": "Email verified successfully"
  }
  ```

## Running Tests

To run all the tests, use the following command:

```sh
mvn clean test
```
### Test Report

To generate a test report, use the following command:

```sh
mvn allure:report
```

To view the test report, use the following command:

```sh
mvn allure:serve
```

To save the allure report in docx, make sure to install *allure-docx* & run the allure-docx:
```shell
pip install allure-docx
allure-docx /path/to/allure-results results.docx # path to allure-results folder is usually target/allure-results
```


### Test Coverage

To generate a test coverage report, use the following command:

```sh
mvn jacoco:report
```


## License

This project is licensed under the MIT License.
