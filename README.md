
# Gift-Card-Catalogue


## **Description**
Gift-Card-Catalogue is a Java-based service designed to manage the catalogue of gift cards for the [American Express Membership Rewards Gift Card Store](https://global.americanexpress.com/rewards/gift-cards). This service provides a collection of REST APIs that allow users to create new gift cards, search for existing gift cards by ID, and query gift cards based on their value or company name. The application is built using Java 17 and is connected to a local PostgreSQL database.

### **Prerequisites**
- [Java 17 +](https://www.oracle.com/uk/java/technologies/downloads/)
- [Maven 3.6+](https://maven.apache.org/install.html)
- [PostgreSQL 13+](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/downloads)

### **Clone the Repository**
```bash
git clone https://github.com/hibs28/giftcard-catalogue.git
cd gift-card-catalogue
```

### **Set Up PostgreSQL Database** ###
1. Start your PostgreSQL database.
2. Create a database named gift_card_catalogue:
```sql
CREATE DATABASE gift_card_catalogue;
```

3. Update your PostgreSQL credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/giftcard
spring.datasource.username=your_username
spring.datasource.password=your_password
```


## Usage/Examples

**Creating a New Gift Card**
- POST /gift_cards
    - Example:
```bash    
curl -X POST http://localhost:3000/gift_cards \
-H "Content-Type: application/json" \
-d '{
  "company_name": "Disney",
  "value": "50",
  "points_cost": "10000"
}'
```
- Request Body:

```json
{
  "company_name": "Disney",
  "value": "50",
  "points_cost": "10000"
}
```
- Response:
```json
{
  "id": "generated-uuid",
  "company_name": "Disney",
  "value": "50",
  "points_cost": "10000"
}
```

**Fetch a Gift Card by ID**
- GET /gift_cards/{id}
    - Example:

```bash
curl -X GET http://localhost:3000/gift_cards/652b740a-93ce-49aa-aeb3-c581fae6f8f1

```
- Response (if found):

```json
{
  "id": "652b740a-93ce-49aa-aeb3-c581fae6f8f1",
  "company_name": "Disney",
  "value": "50",
  "points_cost": "10000"
}
```


- Response (if not found):

```json
{
  "error": "Gift card not found"
}
```

**Delete a Gift Card by ID**
- DELETE `/gift_cards/{id}`
    - Example:
```bash
curl -X DELETE http://localhost:3000/gift_cards/652b740a-93ce-49aa-aeb3-c581fae6f8f1
```
- Response (if deleted):
```json
{
  "message": "Gift card deleted successfully"
}
```

- Response (if not found):
```json
{
  "error": "Gift card not found"
}
```
**List All Gift Cards**
- GET /gift_cards
    - Example:
```bash
curl -X GET http://localhost:3000/gift_cards
```

- Response:
```json
[
  {
    "id": "652b740a-93ce-49aa-aeb3-c581fae6f8f1",
    "company_name": "Disney",
    "value": "50",
    "points_cost": "10000"
  },
  ...
]
```

**Query Gift Cards by Value or Company Name**
- GET /gift_cards?value=50&companyName=Disney
    - Example:
```bash
curl -X GET "http://localhost:3000/gift_cards?value=50&companyName=Disney"

```

- Response:
```json
[
  {
    "id": "652b740a-93ce-49aa-aeb3-c581fae6f8f1",
    "company_name": "Disney",
    "value": "50",
    "points_cost": "10000"
  }
]
```


## Run Locally

Download and Install Prerequisites:

- Java 17
- Maven
- PostgreSQL
- Git


## Clone the Repository and Navigate to the Directory:

```
git clone https://github.com/yourusername/gift-card-catalogue.git
cd gift-card-catalogue
```
## Set Up and Run PostgreSQL:

Start PostgreSQL and create the gift_card_catalogue database as mentioned above.
Build and Run the Application:

```
mvn clean install
mvn spring-boot:run
```

Access the APIs:

Test the endpoints using tools like [Postman](https://www.postman.com/downloads/)
or via the command line using `curl`.## **Accessing the Database**

### **View Preloaded Gift Cards in PostgreSQL**

When the application starts, a set of preloaded gift cards may be inserted into the database. You can view these gift cards directly from the PostgreSQL database using the following steps:

### **Connect to the PostgreSQL Database**

1. **Start the PostgreSQL Command-Line Interface (psql):**
   ```bash
   psql -U your_username -d gift_card_catalogue
   ```

Replace `your_username` with your PostgreSQL username. You will be prompted to enter your password.

2. **List All Gift Cards:**

Once connected to the gift_card_catalogue database, you can run the following SQL query to view all gift cards in the gift_cards table:

```sql
SELECT * FROM gift_cards;
```

3. **Example Output:**

```text
               id                  | company_name | value | points_cost
-----------------------------------+--------------+-------+-------------
652b740a-93ce-49aa-aeb3-c581fae6f8f1 | Disney       | 50    | 10000
a3e8bcfa-b8f9-4c55-a4d9-8b6f7380d6a8 | Amazon       | 100   | 20000
...
```

### **Disconnect from the Database**

To exit the psql command-line interface, simply type:
```bash
\q
```

## Assumptions


* Gift card value is always a whole number same as points_cost.
* The minimum value for a gift card is £5.
* Customers can not create empty gift cards.


## Improvements


* Use enums for the company name instead of a String.
* Add more exception handling when using invalid parameters.
* Add a timestamp to when the gift cards are created.
* Fix the naming convention for gift card as it was inconsistent.
* Change the value to BigDecimal if there is an option for customers to enter their gift card value. (Keeps it consistent as currency is used as BigDecimal)
* Validation for minimum/maximum points. 
