## API End Points

### Shopping Cart API calls

### Get Shopping Cart Items by User ID

- **URL:** `/api/shoppingCart/{userId}`
- **Method:** `GET`
- **Response Body:**
  ```json
  [
  {
    "id": 101,
    "itemQuantity": 2,
    "itemName": "T-shirt",
    "itemPrice": 25.50
  },
  {
    "id": 102,
    "itemQuantity": 1,
    "itemName": "Jeans",
    "itemPrice": 45.75
  }
  ]
  ```

### Update an Item in the Shopping Cart

- **URL:** `/api/shoppingCart/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
  "id": 1,
  "itemId": 101,
  "quantity": 3,
  "price": 75.00,
  "userId": 10
  }
  ```

###  Update Quantity of a Shopping Cart Item 

- **URL:** `/api/shoppingCart/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
  "itemId": 101,
  "userId": 1,
  "quantity": 3
  }
  ```

### Delete an Item from the Shopping Cart

- **URL:** `/api/shoppingCart/{id}`
- **Method:** `DELETE`

### Add an Item to the Shopping Cart

- **URL:** `/api/shoppingCart`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
  "itemId": 101,
  "quantity": 2,
  "price": 50.00,
  "userId": 10
  }
  ```
### Fetching Item Details

- **URL:** `http://localhost:8080/api/items/{itemId}`

  ```

 ### Order Endpoints

### Create a New Order 

- **URL:** `/api/order`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
  "userId": 10,
  "shippingAddress": "Lorem-ipsum",
  "totalAmount":150.54
  }
  ```

 - **Response Body:**
 ```json
  {
  "id": 201,
  "userId": 10,
  "status": PENDING,
  "shippingAddress": "Lorem-ipsum",
  "totalAmount":150.54,
  "paymentId": 0
  }
  ```
