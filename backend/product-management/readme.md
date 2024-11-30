# Online Shopping System - Product Management

This document provides an overview of the API endpoints available in the Product Management module of the Online Shopping System. Each endpoint is described with its HTTP method, URL, parameters, and a brief description.

## Endpoints

### ItemController

#### Get Item by ID
- **URL:** `/api/items/{id}`
- **Method:** `GET`
- **Description:** Retrieve an item by its ID.
- **Parameters:**
  - `id` (path): The ID of the item to retrieve.

#### Get Items by Category
- **URL:** `/api/items/category/{categoryId}`
- **Method:** `GET`
- **Description:** Retrieve items by category ID with pagination support.
- **Parameters:**
  - `categoryId` (path): The ID of the category.
  - `pageNo` (query, default = 0): The page number.
  - `pageSize` (query, default = 16): The page size.

#### Get All Items
- **URL:** `/api/items`
- **Method:** `GET`
- **Description:** Retrieve all items with pagination support.
- **Parameters:**
  - `pageNo` (query, default = 0): The page number.
  - `pageSize` (query, default = 16): The page size.

#### Add Item
- **URL:** `/api/items`
- **Method:** `POST`
- **Consumes:** `multipart/form-data`
- **Description:** Add a new item to a category.
- **Parameters:**
  - `item` (form-data): The item details.
  - `image` (form-data): The image of the item.

#### Update Item
- **URL:** `/api/items/{id}`
- **Method:** `PUT`
- **Consumes:** `multipart/form-data`
- **Description:** Update an existing item.
- **Parameters:**
  - `id` (path): The ID of the item to update.
  - `item` (form-data): The updated item details.
  - `image` (form-data): The updated image of the item.

#### Delete Item
- **URL:** `/api/items/{id}`
- **Method:** `DELETE`
- **Description:** Delete an item by its ID.
- **Parameters:**
  - `id` (path): The ID of the item to delete.

### CategoryController

#### Get All Categories
- **URL:** `/api/categories`
- **Method:** `GET`
- **Description:** Retrieve all categories.

#### Get Category by ID
- **URL:** `/api/categories/{id}`
- **Method:** `GET`
- **Description:** Retrieve a category by its ID.
- **Parameters:**
  - `id` (path): The ID of the category to retrieve.

#### Create Category
- **URL:** `/api/categories`
- **Method:** `POST`
- **Consumes:** `multipart/form-data`
- **Description:** Create a new category.
- **Parameters:**
  - `category` (form-data): The category details.
  - `image` (form-data, optional): The image of the category.
  - `userId` (query): The ID of the user creating the category.
  - `role` (query): The role of the user creating the category.

#### Update Category
- **URL:** `/api/categories/{id}`
- **Method:** `PUT`
- **Consumes:** `multipart/form-data`
- **Description:** Update an existing category.
- **Parameters:**
  - `id` (path): The ID of the category to update.
  - `category` (form-data, optional): The updated category details.
  - `image` (form-data, optional): The updated image of the category.
  - `userId` (query): The ID of the user updating the category.
  - `role` (query): The role of the user updating the category.

#### Delete Category
- **URL:** `/api/categories/{id}`
- **Method:** `DELETE`
- **Description:** Delete a category by its ID.
- **Parameters:**
  - `id` (path): The ID of the category to delete.
  - `userId` (query): The ID of the user deleting the category.
  - `role` (query): The role of the user deleting the category.

## Configuration

Ensure that the following services are properly configured:
- **AmazonS3Service:** For handling file uploads to Amazon S3.
- **ItemService:** For managing item-related operations.
- **CategoryService:** For managing category-related operations.

## Error Handling

All endpoints return a `ResponseDTO` object which contains the status and data or error message. The HTTP status code corresponds to the `status` field in the `ResponseDTO`.

## Example Response
