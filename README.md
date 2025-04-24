# MQ Routing Application

Une application fullstack (Spring Boot + Angular) développée pour le département Paiement d'une banque, permettant de :

- Lire et stocker des messages déposés sur une file IBM MQ
- Consulter ces messages depuis une interface web
- Gérer les partenaires liés aux échanges MQ

---

##  Stack Technique

### Backend (Java)
- Spring Boot
- JMS (IBM MQ)
- MapStruct
- H2 ou PostgreSQL (au choix)
- Swagger / OpenAPI
- Docker

### Frontend (Angular)
- Angular 17
- Angular Material
- Proxy Angular pour accès backend

---

##  Fonctionnalités

### Routage de messages
- Listener JMS asynchrone
- Buffer mémoire + persistance par batch (`saveAll`)
- Détail et suivi des statuts

### Partenaires MQ
- Ajout / suppression via IHM Angular
- Champs requis : alias, type, direction, application, flow type, description

### Interface Web
- Navigation entre "Messages" et "Partenaires"
- Liste + formulaire pour chaque entité
- Dialog Angular Material

---

##  Lancement

### 1. Démarrer IBM MQ en local

```bash
docker run -d --name ibm-mq \
  -e LICENSE=accept \
  -e MQ_QMGR_NAME=QM1 \
  -p 1414:1414 \
  -p 9443:9443 \
  ibmcom/mq:latest
```

 Accès interface MQ : [https://localhost:9443/ibmmq/console](https://localhost:9443/ibmmq/console)  
(par défaut : utilisateur `admin`, mot de passe `passw0rd`)

### 2. Démarrer le backend

```bash
mvn spring-boot:run
```

Swagger : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 3. Démarrer le frontend Angular

```bash
cd angular-frontend
npm install
ng serve --proxy-config proxy.conf.json
```

IHM Angular : [http://localhost:4200](http://localhost:4200)

---

##  Structure du projet

```
mq-routing-application/
├── docker-compose.yml
├── pom.xml
├── README.md
├── start-mq.bat / stop-mq.bat
├── src/
│   └── main/
│       ├── java/com/banque/mq/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── model/
│       │   ├── exception/
│       │   ├── listener/
│       │   ├── mapper/
│       │   ├── repository/
│       │   ├── service/
│       │   └── MqRouterApplication.java
│       └── resources/
│           ├── application.yml
│           └── config.mqsc
```

---

##  Auteur
farid.belkacem@CACIP.fr

Développé par Farid BELKACEM dans le cadre d'une mission pour le département Paiement d'une banque.
