import uuid
from faker import Faker
import psycopg2

# Initialize faker
fake = Faker()

# Database connection parameters
db_params = {
    "host": "localhost",
    "port": 5432,
    "dbname": "mydb",
    "user": "postgres",
    "password": "secret"
}

# Connect to the database
conn = psycopg2.connect(**db_params)

# Create a cursor object
cur = conn.cursor()

# Generate and insert 50 items
for _ in range(50):
    item = (
        str(uuid.uuid4()),
        fake.name(),
        fake.text()
    )

    # SQL query to insert the item
    insert_query = """
    INSERT INTO item (id, name, description)
    VALUES (%s, %s, %s);
    """

    # Execute the query
    cur.execute(insert_query, item)

# Commit the transaction
conn.commit()

# Close the cursor and connection
cur.close()
conn.close()
