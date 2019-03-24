# Teachme Infer Engine

## Introduction
Expert systems, or recommendation systems in this context, are programs that provide user with special advises or give assistance in a complex area where human experience is important. Expert systems consist of an infer engine, a user interface and a knowledge database. Professional inserts his knowledge into database. The user, that wants to get advice for his problem, uses interface and sends his requirements. Infer engine analyzes requirements and derives advises from the knowledge database. Proposed work shows the step-by-step process of developing such an expert system. The process was based on user-oriented design and were performed two usability studies. As a result was obtained usable framework that can connect students and teachers.


## Usage

Works together with Neo4j Database. Before starting, please, install the database and set ports in ```src\main\resources\application.properties```

1. Clone the repository https://github.com/EniesLobby/teachmeback.git
2. Inside root folder run 
```
mvn spring-boot:run
```
3. REST server is accessable through http://localhost:3000
