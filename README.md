<h1 align="center">Bug Tracker - The modern developer's bug tracking platform.</h1>

<p align="center">

  <img src="src/main/javascript/bug-tracker/src/assets/images/logo.png" alt="angular-logo" width="240px" height="170px"/>
  <br>
  <i>Bug Tracker is to keeps track of reported software bugs in software development projects.
    <br> implemented using Java, JavaScript and other languages.</i>
  <br>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Framework-Spring-informational?style=flat&logo=spring&logoColor=white&color=47B93EFF" />
  <img src="https://img.shields.io/badge/Framework-React-informational?style=flat&logo=react&logoColor=white&color=61DBFB" />
</p>

<hr>

Bug Tracker is a web application that keeps track of reported software bugs in software development projects and allows bug reports to be read, added or modified based on users’ role in an organization, and details on the identity of the person who reported the bug; as well as the users who are working on fixing it.

### Features:
* Add, Remove and Modify bugs.
* Categorize bugs into groups and projects.
* See who is currently working on a bug.
* Assign bugs to the users.
* Generate a thorough bug report.

Development
------------
### Back-end:
Bug Tracker is created with a back-end powered by Spring technologies (Spring MVC, Spring Data JPA, Spring Security). The application is also connected to a relational database (MySQL) to store data. To Secure the application, Bug Tracker utilizes JSON Web Token for user validation purposes. The backend api is fully tested using Postman.
### Front-end:
The application front-end is created by various JavaScript libraries such as React.js and Redux. In additon to CSS and HTML, the UI of the application  is implemented by Boostrap and Material UI.

Demo
-----------
### Choose Your Theme:
https://user-images.githubusercontent.com/64161548/147793106-b31c9769-11f1-46db-a336-03cb1d8711ad.mp4

### Fully Responsive UI:
![Untitled design](https://user-images.githubusercontent.com/64161548/147794001-3ffb1961-670d-4de1-bcdb-5b57d1f140ca.gif)

### Organize The Tasks:
<img width="1440" alt="Screen Shot 2021-12-30 at 6 08 01 PM" src="https://user-images.githubusercontent.com/64161548/147794175-e84f2c70-69e8-4bbc-8039-7237a2dabc6f.png">

Install
-----------
* To run the application, clone it using the command:
```
$ git clone https://github.com/PouriaAmini/BugTracker.git
```
* Navigate to the root directory and execute the following commands:
```
$ docker build -f Dockerfile -t bugtracker .
$ docker run -p 3000:3000 -t bugtracker
```
* Navigate to:
```
http://localhost:3000
```
