```bash
docker run --name adminer -p 8099:8080 --link postgres:db -d adminer
```

```bash
docker run --name postgres -e POSTGRES_PASSWORD=secret -d -p 5432:5432 postgres   
```

```sql
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```