🧰 1+. Project Setup Instructions
✅ Prerequisites
Java 17+

Maven

MySQL or PostgreSQL

IDE (IntelliJ/Eclipse)

Postman or Swagger for API testing

Maven

MySQL or PostgreSQL

IntelliJ or Eclipse

🔧 Steps to Run

git clone https://github.com/PradeepCodes/OnlineBusTicketBookingApplicationProject.git
cd OnlineBusTicketBookingApplicationProject
Configure DB in application.properties

For local: CREATE DATABASE busticketDb;

./mvnw spring-boot:run


2. Database Configuration
🔧 Local Development — MySQL
properties

spring.datasource.url=jdbc:mysql://localhost:3306/busticketDb
spring.datasource.username=root
spring.datasource.password=1234
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
🚀 Deployment — PostgreSQL (Render)
properties

spring.datasource.url=jdbc:postgresql://dpg-d13v49ggjchc73fjpim0-a.oregon-postgres.render.com:5432/employee_management_am10?sslmode=require
spring.datasource.username=pradeep
spring.datasource.password=jDo54X1Qm2YmiOZSvgs2nvtzZTKGcVYh

3. API Endpoints Overview
👤 User APIs
POST /api/users/register – Register new user
GET /api/users – Hello test endpoint

🚌 Bus APIs
GET /api/buses – List all

GET /api/buses/{id} – Get by ID

GET /api/buses/search?source=A&destination=B

POST /api/buses – Add new

PUT /api/buses/{id} – Update

DELETE /api/buses/{id} – Delete

🎟️ Booking APIs
POST /api/bookings/book/{busId}?seats=n

GET /api/bookings/my

DELETE /api/bookings/cancel/{id}


4. Security & Login Credentials
🔑 Default Admin Credentials
Use these credentials to log in as an admin (if login functionality is integrated):

Email: admin@bus.com

5. Deployment Information
🔗 Deployment URL
Access the running project via:

🌍 Live App:
https://onlinebusticketbookingapplicationproject.onrender.com

📘 6. API Testing (Swagger)
🔗 Swagger UI URL
Explore and test APIs easily using Swagger UI:

📄 Swagger:
https://onlinebusticketbookingapplicationproject.onrender.com/swagger-ui/index.html

