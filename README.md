# Project iTem

### Project Overview

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

Check out the `docker-compose-works` branch:

```bash
git checkout docker-compose-works
```

- Navigate to the `parent` directory:

```bash
cd orchestrated-owasp-fullstack
```

- Start docker compose by running the following command:

```bash
docker-compose up -d
```

Open the browser and navigate to `http://localhost:4200/` to view the application.

You can run the following command to stop the docker compose:

```bash
docker-compose down -v --remove-orphans --rmi all
```

The above command will stop the docker compose and remove all the volumes, orphans and images.

## Contact

If you want to contact me you can reach me at `aindamut@gmail.com`.

## License

This project uses the following license: `MIT`.
Essentially, you are free to use, modify, and distribute the code as you see fit. However, you must include the original copyright and license in any copy of the project.
