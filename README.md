# Project iTem

## Project Overview

This application is a comprehensive full-stack solution, meticulously designed for robust performance and scalability. It integrates a variety of technologies including Java, Spring Boot, Angular, PostgreSQL, TypeScript, and HTML/CSS, providing a seamless user experience and efficient data management.

![alt text](images/image.png)

## Application Architecture

- **Backend:** The server-side is powered by Java 17, leveraging Spring Boot for its framework, which simplifies the bootstrapping and development of new Spring applications. The application follows RESTful design principles, facilitating clear and effective communication between the client and server.

- **Frontend:** The client-side is developed with Angular, utilizing TypeScript to enhance JavaScript functionalities and ensuring more stable code. Angular’s framework supports rich interaction and dynamic content management. HTML and CSS are used to craft the user interface, offering an intuitive and responsive design.

- **Database:** PostgreSQL serves as the relational database management system, chosen for its advanced features and ability to handle high volumes of data. SQL is used to manage and manipulate the data effectively.

## Key Features

- **Responsive Design:** The application is designed to be fully responsive, providing an optimal viewing experience across a wide range of devices.
- **Secure RESTful APIs:** Ensures secure data transmission with REST architecture, making the app reliable for handling data operations.
- **Robust Data Handling:** Utilizes PostgreSQL for efficient data storage and retrieval, supporting complex queries with ease.

## Getting Started

To engage with this project, users should have a fundamental understanding of full-stack development, particularly with the technologies employed in this application. This includes familiarity with Java and Spring Boot for the backend, Angular for the frontend, and PostgreSQL for database operations.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed the required versions of `Java (version 17)`, `Node.js (version v20.12.1)`, and `npm (v10.5.0)`.
- You have a basic understanding of `full-stack development` using `Java`, `Spring Boot`, `Angular`, and `PostgreSQL`.
- Have a basic understanding of `RESTful APIs` and `SQL`.
- You should have enough Knowledge around OWASP Top 10 vulnerabilities. The application takes care into account of the following vulnerabilities:
  - Injection
  - Broken Authentication
  - Sensitive Data Exposure
  - XML External Entities (XXE)
  - Broken Access Control
  - Security Misconfiguration
  - Cross-Site Scripting (XSS)
  - Insecure Deserialization
  - Using Components with Known Vulnerabilities
  - Insufficient Logging & Monitoring
- Have a basic understanding of `TypeScript` and `HTML/CSS`.

## Installing and Running the Project

To install and run the project, follow these steps:

- Clone the repository:

```bash
git clone https://github.com/indamutsa/orchestrated-owasp-fullstack.git
```

Check out the `manual` branch:

```bash
git checkout manual
```

### Backend

1. Start the PostgreSQL server.
2. Start Adminer to view and manage the database.
3. Navigate to the backend directory:

```bash
cd backend
```

4. Start the backend server:

```bash
./start-backend
```

### Database

Populate the database with data:

1. Navigate to the data-gen directory:

```bash
cd ../data-gen
```

2. Create a Python virtual environment and activate it:

```bash
python3 -m venv pyenv
source pyenv/bin/activate
```

3. Run the main Python script to populate the database:

```bash
python main.py
```

### Frontend

1. Navigate to the frontend directory:

```bash
cd ../frontend
```

2. Install the necessary npm packages:

```bash
npm install
```

3. Start the frontend server:

```bash
ng serve
```

4. Open your web browser and navigate to `http://localhost:4200/` to view the application.

## Contact

If you want to contact me you can reach me at `aindamut@gmail.com>`.

## License

This project uses the following license: `MIT`.
Essentially, you are free to use, modify, and distribute the code as you see fit. However, you must include the original copyright and license in any copy of the project.
