# subscriber

contains services to create users,get users,subscribe users and get subscribers of an users. 

# Controllers

1>	SubscriberAPIController.java

# Available APIs

POST : http://localhost:8080/user
GET : http://localhost:8080/user
GET : http://localhost:8080/user/{userId}/subscribers
PUT : http://localhost:8080/user/{userId}/subscribe/{subscribeUserId}

## Running the tests

*note : test cases are not yet included

## Coding and architecture

Spring boot architecture of web services

## Deployment

Deployment will be done on apache tomcat(currently assumed port 8080 for API urls).

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Sani Vishwakarma** - *Initial work* 

## Importable Postman collection Link
https://www.getpostman.com/collections/51aebf4c78ef548c5716
