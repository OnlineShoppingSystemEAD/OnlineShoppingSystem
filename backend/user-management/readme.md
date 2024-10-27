# User Management Service

This project is a User Management Service built with Java and Spring Boot. It provides various endpoints to manage user profiles, including creating, updating, deleting, and managing user authentication.

## Prerequisites

- Java 11 or higher
- Maven
- Spring Boot

## Endpoints

### Get User Profile by ID
- **URL:** `/users/{id}`
- **Method:** `GET`
- **Description:** Retrieves the user profile by ID.
- **Response:** `ReqRes`

### Create User Profile
- **URL:** `/users`
- **Method:** `POST`
- **Description:** Creates a new user profile.
- **Request Body:**
  - `name`: the user's name
  - `email`: the user's email address
- **Response:** `ReqRes`

### Update User Profile by ID
- **URL:** `/users/{id}/profile`
- **Method:** `PUT`
- **Description:** Updates a user profile by ID.
- **Request Body:**
  - `postalNumber`: the user's postal number
  - `phoneNumber`: the user's phone number
  - `addressPart1`: the first part of the user's address
  - `addressPart2`: the second part of the user's address
  - `addressPart3`: the third part of the user's address
- **Response:** `ReqRes`

### Delete User Profile by ID
- **URL:** `/users/{id}/profile`
- **Method:** `DELETE`
- **Description:** Deletes a user profile by ID.
- **Response:** `ReqRes`

### Change User Password
- **URL:** `/users/{id}/password`
- **Method:** `PUT`
- **Description:** Changes a user's password.
- **Request Body:**
  - `oldPassword`: the user's current password
  - `newPassword`: the user's new password
- **Response:** `ReqRes`

### Forgot Password
- **URL:** `/users/forgot-password`
- **Method:** `POST`
- **Description:** Initiates the forgot password process.
- **Request Body:**
  - `email`: the user's email address
- **Response:** `ReqRes`

### Verify Forgot Password
- **URL:** `/users/forgot-password/verify`
- **Method:** `POST`
- **Description:** Verifies the forgot password process.
- **Request Body:**
  - `email`: the user's email address
  - `verificationCode`: the verification code sent to the user's email
  - `newPassword`: the user's new password
- **Response:** `ReqRes`

### Verify Email
- **URL:** `/users/verify-email`
- **Method:** `POST`
- **Description:** Verifies the user's email.
- **Request Body:**
  - `email`: the user's email address
  - `verificationCode`: the verification code sent to the user's email
- **Response:** `ReqRes`

## Running the Application

To run the application, use the following command:

```bash
mvn spring-boot:run