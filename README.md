# product-service

**Pre-requisite:**
***The host machine should have docker installed.***

1. Checkout the project to your desired directory.
2. CD into the directory
3. Run the command "docker-compose up"
4. The process will pull and build the images, and start the product service @port 8080 and the mongo db @port:27017

**Once the servics are up and running**
***To Access the service:***

1. Open your favourite browser
2. Type: http://localhost:8080/swagger-ui.html and hit enter
3. You will be presented with the Swagger page.
4. Expand "Product Controller"
5. Click "GET /products/{id}" to expand the Get Product By ID service.
6. Click "Try it Out".
7. Enter the value: "13860428" in the id text box.
8. Click "Execute"

**On Success:**

***Server Response: 200***
***Response Body: ***

{
  "id": "13860428",
  "name": "The Big Lebowski (Blu-ray)",
  "price": {
    "id": "5d0fe36fd5fa594daf00e355",  <---- this will change
    "productId": "13860428",
    "value": 34.54,
    "currencyCode": "USD"
  }
}

