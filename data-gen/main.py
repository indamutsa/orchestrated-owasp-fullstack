import uuid
from faker import Faker
import psycopg2
import random
import bcrypt

# Initialize faker
fake = Faker()

# Database connection parameters
db_params = {
    "host": "db",
    "port": 5432,
    "dbname": "mydb",
    "user": "postgres",
    "password": "secret"
}

# Connect to the database
conn = psycopg2.connect(**db_params)

# Create a cursor object
cur = conn.cursor()

# Define roles
roles = [1, 2, 3]



# Define users
users = ["lissette", "grace", "arsene", "markus", "dane", "samantha"]
for user in users:
    user_id = str(uuid.uuid4())
    email = f"{user}@indamutsa.net"
    user_roles = random.sample(roles, random.randint(1, 3))
    password = "indamutsa"
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    decoded_password = hashed_password.decode('utf-8')  # Decode the byte string
    
    user = (
        user_id,
        user,
        decoded_password,
        email,
    )

    # SQL query to insert the user
    insert_user_query = """
    INSERT INTO users (id, username, password, email)
    VALUES (%s, %s, %s, %s);
    """

    # Execute the query
    cur.execute(insert_user_query, user)

    # SQL query to insert the roles
    for role_id in user_roles:
        insert_role_query = """
        INSERT INTO user_roles (user_id, role_id)
        VALUES (%s, %s);
        """
        # Execute the query
        cur.execute(insert_role_query, (user_id, role_id))

    # Generate and insert 50 items for each user
    for _ in range(10):
        item = (
            str(uuid.uuid4()),
            fake.name(),
            fake.text(),
            user_id  # Add user_id here
        )

        # SQL query to insert the item
        insert_item_query = """
        INSERT INTO item (id, name, description, user_id)
        VALUES (%s, %s, %s, %s);
        """

        # Execute the query
        cur.execute(insert_item_query, item)
        

# Commit the transaction
conn.commit()

# Close the cursor and connection
cur.close()
conn.close()