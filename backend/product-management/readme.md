## API End Points

### Getting number of items to display

- **URL:** `/api/items/`
- **Method:** `GET`
- **Request Body:**
  ```json
  {
    "pageNo": 1,
    "pageSize": 16
  }
  ```

  ### Get a specific item

- **URL:** `/api/items/{id}`
- **Method:** `GET`
- **Request Body:**
  ```json
  {
    "itemId": 1,
  }
  ```

  - **Response Body:**
  ```json
  {
    "itemId": 1,
    "name": "T-shirt",
    "description": "Lorem-ipsum",
    "price":40.65,
    "quantity":5,
    "categoryId":2
  }
  ```

   ### Get set of items by category

- **URL:** `/api/items/category/{categoryId}`
- **Method:** `GET`
- **Request Body:**
  ```json
  {
    "categoryId": 2,
    "pageNo": 1,
    "pageSize":16
  }
  ```
### Create a new item by admin

- **URL:** `/api/admin/items`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
  "name": "T-shirt",
  "description": "Lorem-ipsum",
  "price": 40.65,
  "quantity": 5,
  "categoryId": 2
  }
  ```

### Update an existing item by admin

- **URL:** `/api/admin/items/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
  "name": "Updated T-shirt",
  "description": "Updated description",
  "price": 45.00,
  "quantity": 10,
  "categoryId": 2
  }
  ```
### Delete an existing item by admin

- **URL:** `/api/admin/items/{id}`
- **Method:** `DELETE`
- **Response Body:**
  ```json
  {
  "message": "Item deleted successfully."
  }
  ```
  

  
