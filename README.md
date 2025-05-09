
# 🧩 Full Stack Project – Angular + Spring Boot

This project is a full-stack application consisting of:

- **Backend**: Spring Boot (Java 21, Spring Security, JPA, JWT, MySQL)
- **Frontend**: Angular (Node.js, TypeScript)

It provides user authentication, a secure REST API, and a modern Angular frontend.

---

## 📁 Project Structure
```
project-root/
├── back/                    # Spring Boot API (mddapi)
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── openclassrooms/
│       │           └── mddapi/
│       │               ├── MddApiApplication.java
│       │               ├── configs/
│       │               │   ├── ApplicationConfiguration.java
│       │               │   ├── JwtAuthenticationFilter.java
│       │               │   ├── OpenApiConfig.java
│       │               │   └── SecurityConfiguration.java
│       │               ├── controller/
│       │               │   ├── ArticleController.java
│       │               │   ├── AuthenticationController.java
│       │               │   ├── CommentaireController.java
│       │               │   ├── ThemeController.java
│       │               │   └── UserController.java
│       │               ├── dtos/
│       │               │   ├── article/
│       │               │   │   ├── ArticleDto.java
│       │               │   │   ├── AuthorDto.java
│       │               │   │   ├── CreateArticleDto.java
│       │               │   │   ├── ThemeDto.java
│       │               │   │   ├── ThemeListDto.java
│       │               │   │   └── UpdateArticleDto.java
│       │               │   ├── commentaire/
│       │               │   │   └── CommentaireDto.java
│       │               │   └── user/
│       │               │       ├── LoginUserDto.java
│       │               │       ├── MeDto.java
│       │               │       ├── RegisterUserDto.java
│       │               │       ├── UpdateUserDto.java
│       │               │       └── UserDto.java
│       │               ├── exceptions/
│       │               │   └── GlobalExceptionHandler.java
│       │               ├── models/
│       │               │   ├── Article.java
│       │               │   ├── Commentaire.java
│       │               │   ├── SuiviTheme.java
│       │               │   ├── Theme.java
│       │               │   └── User.java
│       │               ├── repository/
│       │               │   ├── ArticleRepository.java
│       │               │   ├── CommentaireRepository.java
│       │               │   ├── SuiviThemeRepository.java
│       │               │   ├── ThemeRepository.java
│       │               │   └── UserRepository.java
│       │               ├── responses/
│       │               │   └── LoginResponse.java
│       │               └── services/
│       │                   ├── ArticleService.java
│       │                   ├── AuthenticationService.java
│       │                   ├── CommentaireService.java
│       │                   ├── JwtService.java
│       │                   ├── ThemeService.java
│       │                   └── UserService.java
│       └── resources/
│           ├── application.properties
│           └── data.sql
└── test/
└── java/
└── com/
└── openclassrooms/
└── mddapi/
└── MddApiApplicationTests.java

├── front/                    # Angular client
│   └── src/
│       ├── app/
│       │   ├── guards/
│       │   │   ├── auth.guard.ts
│       │   │   └── auth-redirect.guard.ts
│       │   ├── interceptors/
│       │   │   └── jwt.interceptor.ts
│       │   ├── interfaces/
│       │   │   ├── article.ts
│       │   │   ├── author.ts
│       │   │   ├── commentaire.ts
│       │   │   ├── create-article.ts
│       │   │   ├── index.ts
│       │   │   ├── list-theme.ts
│       │   │   ├── me.ts
│       │   │   └── theme.ts
│       │   ├── pages/
│       │   │   ├── account/
│       │   │   ├── article-detail/
│       │   │   ├── article-list/
│       │   │   ├── commentaire/
│       │   │   ├── create-article/
│       │   │   ├── home/
│       │   │   ├── login/
│       │   │   ├── navbar/
│       │   │   ├── register/
│       │   │   └── theme-list/
│       │   └── services/
│       ├── assets/
│       ├── environments/
│       └── styles/
└── README.md
```
# ⚙️ Prerequisites #
✅ Backend (Spring Boot):
Java 21+</br>
Maven 3.6+</br>
MySQL</br>
✅ Frontend (Angular):
Node.js 16+</br>
Angular CLI:</br>
npm install -g @angular/cli</br>
# 🚀 Clone the Project #
```bash
git clone https://github.com/kenchi-san/Developpez-une-application-full-stack-complete.git
```
```bash
cd ~/Developpez-une-application-full-stack-complete
```
# 🔙 Backend Setup #
Create MySQL Database
```bash
mysql -u root -p mdd < back/src/main/resources/data.sql
```
```
Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mddapi?allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# update the follow line in false in prod
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.springframework=DEBUG
logging.level.com.openclassrooms.starterjwt=DEBUG
server.port=8081
security.jwt.secret-key=
security.jwt.expiration-time=3600000
```
Install and Run Backend

cd back</br>
```bash
mvn clean install
mvn spring-boot:run

```
Backend is running at: http://localhost:8081</br>
# 🖥️ Frontend Setup #
## Install dependencies ##
```bash
cd ../front
npm install
Run Angular app
ng serve
```
Frontend is running at: http://localhost:4200

---

## 📄 API Documentation (Swagger)

Once the backend is running, you can access the Swagger UI for API documentation here:

🔗 [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

This interface allows you to explore and test the available endpoints interactively.

✅ Done
You now have the full stack app up and running!
---

