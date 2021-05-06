## 695 Project - People


People is a Stateful app built with [Spring Boot](http://spring.io/projects/spring-boot), [MongoDB](https://www.mongodb.com/) and [React](https://reactjs.org/).

Features:
- Routing
- User authentication: Register/Login/Logout
- Editing user profile
- Searching users 
- Following users
- Unfollow users from followed list
- Adding and deleting photos
- Creating and deleting posts
- Creating and deleting comments


**Example user Credentials:**
- username: test
- password: 123456.Test

## Requirements

1. Java 15

2. npm 6.14.6

3. MongoDB 4.4.1

3. Intellij Idea 2020.2.4(Ultimate Edition)

## Start the app

#### 1. Connect the DB

Make sure your MongoDB Driver connected to port `27017`

#### 2. Start the Server

Go to the root directory of the Spring Boot app:

```bash
$ cd 695-Project
```

Start the Server:

```bash
$ mvn spring-boot:run
```
The Server is running on port `8080`.

Once you started the Server, the data will automatically be added to the Database.

#### 3. Start the Client

To start the Client you need to enter the `695-Project/frontend` folder:

```bash
$ cd 695-Project/frontend
```

Install all dependencies:

```bash
$ npm install
```

Run the app in the development mode:

```bash
$ npm start
```

Open [http://localhost:3000](http://localhost:3000) to view it in the browser.




## App screenshots

1. **Home Page**

 ![App Screenshot](path)

2. **Followed Page**


3. **Photos Page**

 




Team: worker