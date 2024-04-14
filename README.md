# Project iTem

![alt text](images/dashboard-web.png)

### Project Overview

This application is a comprehensive full-stack solution, meticulously designed for robust performance and scalability. It integrates a variety of technologies including Java, Spring Boot, Angular, PostgreSQL, TypeScript, and HTML/CSS, providing a seamless user experience and efficient data management.

![alt text](images/image.png)

## Application Architecture

- **Backend:** The server-side is powered by Java 17, leveraging Spring Boot for its framework, which simplifies the bootstrapping and development of new Spring applications. The application follows RESTful design principles, facilitating clear and effective communication between the client and server.

- **Frontend:** The client-side is developed with Angular, utilizing TypeScript to enhance JavaScript functionalities and ensuring more stable code. Angular’s framework supports rich interaction and dynamic content management. HTML and CSS are used to craft the user interface, offering an intuitive and responsive design.

- **Database:** PostgreSQL serves as the relational database management system, chosen for its advanced features and ability to handle high volumes of data. SQL is used to manage and manipulate the data effectively.

- **Deployment:** The application is deployed using Docker and Kubernetes, which provide containerization and orchestration capabilities. Helm charts are used to manage the deployment of the application, ensuring consistency and scalability. We also use an Ingress controller to route external traffic to the application.

## Key Features

- **Robust Data Handling:** Utilizes PostgreSQL for efficient data storage and retrieval, supporting complex queries with ease.
- **Secure RESTful APIs:** Ensures secure data transmission with REST architecture, making the app reliable for handling data operations.
- **User Authentication:** Implements user authentication and authorization, ensuring secure access to the application.
- **Dynamic User Interface:** Employs Angular for a dynamic and interactive user interface, enhancing the user experience.
- **Containerization:** Utilizes Docker for containerization, enabling easy deployment and scaling of the application.
- **Orchestration:** Leverages Kubernetes for orchestration, ensuring efficient management of containers and resources.
- **Helm Charts:** Utilizes Helm charts for managing the deployment of the application, ensuring consistency and scalability.
- **Ingress Controller:** Uses an Ingress controller to route external traffic to the application, enabling seamless access.
- **OWASP Top 10:** Addresses OWASP Top 10 vulnerabilities, ensuring the application is secure and protected against common threats.
- **Responsive Design:** The application is designed to be fully responsive, providing an optimal viewing experience across a wide range of devices.

## Getting Started

To engage with this project, users should have a fundamental understanding of full-stack development, particularly with the technologies employed in this application. This includes familiarity with Java and Spring Boot for the backend, Angular for the frontend, and PostgreSQL for database operations.

## Working branches

- **manual**: This branch contains the manual deployment of the application.

```bash
git checkout manual
```

- **Docker compose**: This branch contains the deployment of the application using Docker Compose.

```bash
git checkout docker-compose-works
```

- **Kubernetes**: This branch contains the deployment of the application using Kubernetes.

```bash
git checkout minikube-works
```

### Kubernetes Dashboard

![alt text](images/dashboard-kube.png)

### Web Application

Open the browser and navigate to `http://item.local/` to view the application.

![alt text](images/login.png)

![alt text](images/dashboard-web.png)

![alt text](images/view.png)

![alt text](images/update.png)

![alt text](images/delete.png)

![alt text](images/profile.png)

### Database

- View the database by navigating to `http://item.local/adminer`.
  ![alt text](images/db-init.png)

  ![alt text](images/db-tables.png)

  ![alt text](images/db-items.png)

## Contact

If you want to contact me you can reach me at `aindamut@gmail.com`.

## License

This project uses the following license: `MIT`.
Essentially, you are free to use, modify, and distribute the code as you see fit. However, you must include the original copyright and license in any copy of the project.
