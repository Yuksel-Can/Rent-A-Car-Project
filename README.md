<div align="center"><h1> :sparkles: Rent A Car Project :sparkles: </h1> </div>
<div align="center"><h2> 🚥 Araç Kiralama Projesi 🚥 </h2> </div>

:point_right:  'Rent-A-Car-Project' is a public web project developed with the Java Spring Framework. Tools: **Java Spring-Hibernate-ORM-Maven-Spring Boot-AOP-DTO-JPA-RestAPI-JSON-Dev Tools-PostgreSQL**
<br/>
 ``` 'Rent-A-Car-Project', Java Spring Framework ile geliştirilmiş genel bir web projesidir. Araçlar: **Java Spring-Hibernate-ORM-Maven-Spring Boot-AOP-DTO-JPA-RestAPI-JSON-Dev Tools-PostgreSQL**```
<br/>

<b><h2> :star: Click for Test on Live Server: :point_right: <a href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/">LIVE TEST</a> :point_left: </h2></b>
``` Canlı Sunucuda Test Etmek İçin Tıklayınız: (Yüklenmesi biraz sürmektedir, yüklenene kadar bekleyiniz) ```
<b><h2> :star: Click for Backend Codes: :point_right: <a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject">BACKEND CODE </a> :point_left: </h2></b> 
``` Backend Kodları İçin Tıklayınız: ```
<b><h2> :star: Click for Database Script Codes: :point_right: <a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/PostgreSQL/Database-Script.txt">SCRIPT CODE</a> :point_left: </h2></b>
``` Veritabanı Script Kodları İçin Tıklayınız: ```
![alt project_images1](https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/Cars/images1.jpg?raw=true)

---
## `File Structure` 

### ` 🏗️ N-Layered Architecture`
  
<ul>
        <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/api">API</a> - The package that connects with the user
            <ul>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/api/controllers">Controllers</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/api/models">Models</a> </li>
            </ul>
        </li>
        <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business">Business</a> - The package with business rules
            <ul>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/abstracts">Abstracts</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/adapters/posAdapters">Adapters/PosAdapters</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/concretes">Concretes</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/constants/messaaages">Constants/Messages</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/dtos">Dtos</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/outServices">OutServices</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/business/requests">Requests</a> </li>
            </ul>
        </li>
        <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/core">Core</a> - The package common layer of the project
            <ul>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/core/outServices">OutService</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/core/posServices">PosService</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/core/utilities">Utilities</a> </li>
            </ul>
        </li>
        <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/dataAccess/abstracts">DataAccess</a> - The package containing database related operations
            <ul>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/dataAccess/abstracts">Abstracts</a> </li>
            </ul>
        </li>
        <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/entities">Entities</a> - The package in which the assets are kept
            <ul>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/entities/abstracts">Abstracts</a> </li>
                <li><a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject/entities/concretes">Concretes</a> </li>
            </ul>
        </li>
    </ul>

---

  
  ## ` 🛠️ Language and Tools` 
<p align="left"> <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>
<a href="https://www.postgresql.org/" target="_blank"> <img src="https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg" width="40"height="40"/>
<a href="https://projectlombok.org/" target="_blank"> <img src="https://avatars.githubusercontent.com/u/45949248?s=200&v=4" alt="projectlombok" width="40" height="40"/> 
<a href="https://hibernate.org/" target="_blank"> <img src="https://cdn.freebiesupply.com/logos/large/2x/hibernate-logo-png-transparent.png" width="40" height="40"/> 
<a href="https://swagger.io/" target="_blank"> <img src="https://seeklogo.com/images/S/swagger-logo-A49F73BAF4-seeklogo.com.png" width="40" height="40"/> 
<a href="https://spring.io/projects/spring-data-jpa" target="_blank"> <img src="https://huongdanjava.com/wp-content/uploads/2018/01/spring-data.png" width="40"height="40"/>
<a href="https://id.heroku.com/" target="_blank"> <img src="https://cdn-icons-png.flaticon.com/512/873/873120.png" width="40" height="40"/> 
</a>
</p>
 
 ---
  
  ## ` 📊 ER Diagram with PostgreSQL`
   ### <a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/PostgreSQL/Database-Script.txt">Click</a> for script codes.
<p align="center"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/Diagrams/ER-Diagram.jpg?raw=true"></p>
  
---
 
  ## ` 📊 Class Diagram`
   ### <a href="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/Diagrams/UML%20Class%20Diagram.png?raw=true">Click</a> for view the class diagram.
<p align="center"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/Diagrams/UML%20Class%20Diagram.png?raw=true"></p>
  
---
 
  ## ` 🔭 Swagger Screenshots and Endpoints`
 ### <p align="center"> <a href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/"> 🌟 ❗ ↘️ Click on the pictures to test ↙️ ❗ 🌟 </a> </p>
 <p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/rental-cars-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/rental-cars-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/payments-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/payments-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/individual-customers-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/individual-customers-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/corporate-customers-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/corporate-customers-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/colors-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/colors-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/cars-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/cars-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/car-maintenances-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/car-maintenances-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/car-crashes-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/car-crahes-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/brands-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/brands-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/additionals-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/additionals-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/cities-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/cities-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/users-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/users-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/ordered-additionals-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/ordered-additionals-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/invoices-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/invoices-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/customers-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/customers-controller.png?raw=true"></a></p>
<hr>
<p align="center"><a target="_blank" href="https://rent-a-car-project-yuksel-can.herokuapp.com/swagger-ui/index.html#/credit-cards-controller"><img src="https://github.com/Yuksel-Can/Rent-A-Car-Project/blob/main/project_images/swagger-ss/credit-cards-controller.png?raw=true"></a></p>
 
---
 
To be continued here
 
---
 
 ## ` 🚧 Roadmap`
See the [open issues](https://github.com/Yuksel-Can/Rent-A-Car-Project/issues) for a list of proposed features (and known issues).

---
 
## ` 🤝 Contributing`

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b <feature>/<AmazingFeature>'`)
3. Commit your Changes (`git commit -m '<gitmoji> <?(scope):> <AmazingFeature>'`)
4. Push to the Branch (`git push origin <feature>/<AmazingFeature>`)
5. Open a Pull Request

In commit naming, commit type has written with gitmoji.

---

## ` ⚖️ License (soon)`

Distributed under the MIT License. See `LICENSE` for more information.

---

## ` 📧 Contact`

Yüksel Can ÖZDEMİR 

E-Mail - [yukselcanozdemir@gmail.com](mailto:yukselcanozdemir@gmail.com)

Linkedin - [linkedin.com/Yuksel-Can](https://www.linkedin.com/in/y%C3%BCksel-can-%C3%B6zdemir-1a742b183/)

Project Link: [Rent-A-Car-Project](https://github.com/Yuksel-Can/Rent-A-Car-Project/tree/main/rentACarProject/rentACarProject/src/main/java/com/turkcell/rentACarProject)

---

## ` 🙏 Acknowledgements`

- [Engin Demiroğ](https://www.linkedin.com/in/engindemirog/)

---
