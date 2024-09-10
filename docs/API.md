# Car API Documentation

## Base URL

http://localhost:8080

### Endpoints

### 1. Create Car

**Endpoint:** 'POST /api/v1/manufacturers/{manufacturerId}/models/{modelId}/cars'

**Description:**
Create new car.

**Request Body:**

---json
{
"year": 2024,
"categories" : ["PICKUP"]
},

Response:

201 Created
{
"carId": 1,

"manufacturer": {
"id": 1,
"name": Dodge
}

"model":{
"id": 1,
"name": "Ram"
}

"year": 2024,
"categories" : ["PICKUP"]
}

### 2. Delete Car By ID

**Endpoint:** 'DELETE /api/v1/manufacturers/{manufacturerId}/models/{modelId}/cars/{carId}'

**Description:**
Delete Car By ID.

Path Parameters :
'carId' - Unique identifier.

Response:
204 No Content
404 Not Found
{
"error": "Car not Found"
}

### 3. Update Car

**Endpoint:** PUT /api/v1/manufacturers/{manufacturerId}/models/{modelId}/cars/{carId}'

**Description:**
Update details.

Path Parameters:
'carId' = Unique identifier.

**Request Body:**

---json
{

"year": 2025,
"categories": ["SEDAN"]

}

Response:
200 OK

{
"id": 1,

"manufacturer": {
"id": 2,
"name": "Toyota"
},

"model": {
"id": 2,
"name": "Corolla"
},

"year": 2025,
"categories": ["SEDAN"]
}

### 4. Get Car By ID

**Endpoint:** 'GET /api/v1/manufacturers/{manufacturerId}/models/{modelId}/cars/{carId}'

**Description:**
Get car details by ID.

Path Parameters:
'carId' - Unique identifier.

Response:

200 OK
{
"id": 1,

"manufacturer": {
"id": 1,
"name": "Toyota"
},

"model": {
"id": 1,
"name": "Camry"
},

"year": 2024,
"categories": ["SEDAN"]
}

404 Not Found
{
"error": "Car with ID 1 not found"
}

## 5. Search Cars

**Endpoint:** 'GET /api/v1/search'

**Description:**
Search for cars based on various query parameters.

**Query Parameters:**

- manufacturer (optional) - Filter cars by manufacturer name.
- model (optional) - Filter cars by model name.
- minYear (optional) - Filter cars by minimum year of manufacture.
- maxYear (optional) - Filter cars by maximum year of manufacture.
- categories (optional) - Filter cars by body type.

Response:
200 OK
---json
{

"id": 1,

"manufacturer": {
"id": 1,
"name": "Dodge"
},

"model": {
"id": 1,
"name": "Ram"
},

"year": 2024,
"categories": ["PICKUP"]
}
]

404 Not Found
---json
{
"error": "No cars found matching the criteria"
}

## Manufacturer Endpoints

- `POST /api/v1/manufacturers` - Create.
- `DELETE /api/v1/manufacturers/{manufacturerId}` - Delete manufacturer by ID.
- `PUT /api/v1/manufacturers/{manufacturerId}` - Update manufacturer by ID.
- `GET /api/v1/manufacturers/{manufacturerId}` - Get a manufacturer by ID.
- `GET /api/v1/manufacturers?query={query}` - Search manufacturers by name.

## Model Endpoints

- `POST /api/v1/manufacturers/{manufacturerId}/models` - Create.
- `DELETE /api/v1/manufacturers/{manufacturerId}/models/{modelId}` - Delete By ID.
- `PUT /api/v1/manufacturers/{manufacturerId}/models/{modelId}` - Update by ID
- `GET /api/v1/manufacturers/{manufacturerId}/models/{modelId}` - Get by ID
- `GET /api/v1/manufacturers/{manufacturerId}/models?query={query}` - Search models by name for a specific manufacturer.

## Category Endpoints

- `POST /api/v1/cars/{carId}/categories` - Create.
- `DELETE /api/v1/cars/{carId}/categories/{categoryId}` - Delete By ID.
- `PUT /api/v1/cars/{carId}/categories/{categoryId}` - Update By ID.
- `GET /api/v1/cars/{carId}/categories/{categoryId}` - Get By ID.
- `GET /api/v1/cars/{carId}/categories/{categoryId}` - Search category by name for specific car.
