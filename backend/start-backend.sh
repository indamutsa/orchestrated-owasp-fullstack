docker rm -f postgres adminer

# Start the PostgreSQL container
docker run --name postgres -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=mydb -d -p 5432:5432 postgres

# Check if PostgreSQL container started successfully
if [ $? -ne 0 ]; then
    echo "Failed to start PostgreSQL container"
    exit 1
fi

sleep 10

# Start the Adminer container
docker run --name adminer -p 8099:8080 --link postgres:db -d adminer

# Check if Adminer container started successfully
if [ $? -ne 0 ]; then
    echo "Failed to start Adminer container"
    exit 1
fi

# Wait for PostgreSQL to start
echo "Waiting for PostgreSQL to start"
sleep 10

mvn clean install

# Copy the commands.sql file to the container
docker cp commands.sql postgres:/commands.sql

# Run the commands.sql file
docker exec -it postgres bash -c "PGPASSWORD=secret psql -U postgres -f /commands.sql"
if [ $? -ne 0 ]; then
    echo "Failed to run SQL commands"
    exit 1
fi

# Start the backend
mvn clean spring-boot:run
if [ $? -ne 0 ]; then
    echo "Failed to start backend"
    exit 1
fi

echo "Backend started successfully"
