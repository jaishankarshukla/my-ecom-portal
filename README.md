My E-Commerce Portal

Functionality Available 
**Add a product with Images**
**Search a Product**
**Add a product in Cart**
**Review Cart**
**Place Order** (Be careful, you will not receive the order as you have not paid for it.)

#Note: This is an ongoing project and I will be adding many features as I go along. Please email me if you have questions.

✅ Build & Run Everything
# Make sure the network exists
podman network create mynet

# Then build and run all services
podman-compose up --build -d

# Build the services in nocache mode while you are frequently changing files
podman compose -f podman-compose.yaml build --no-cache

# Create mynet network name before running the services.
 podman network create mynet
 
🌐 Access Points
Service	URL (host)	Notes
React frontend	http://localhost:3000
	Your app UI
Spring Boot backend	http://localhost:8080
	REST API
pgAdmin	http://localhost:8088
	Database UI
Postgres DB	localhost:5432	For local clients or pgAdmin
🧩 Container Networking Inside Podman

Inside containers:

myecomapp can reach the DB as my-app-database:5432

myecomwebapp can reach the backend as myecomapp:8080

From your host/browser:

Use http://localhost:8080 (because Podman publishes the port)

🧰 Optional Enhancement — .env File

You can externalize configuration by creating a .env file next to podman-compose.yaml:

POSTGRES_USER=admin
POSTGRES_PASSWORD=999999
POSTGRES_DB=ecomdb
PGADMIN_DEFAULT_EMAIL=pgadmin@pgadmin.com
PGADMIN_DEFAULT_PASSWORD=888888
REACT_APP_API_URL=http://localhost:8080


And reference them in your YAML like:

environment:
  POSTGRES_USER: ${POSTGRES_USER}
  POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}