# spring-security-thyemleaf-jpa-postgres-customized-bootstrap
A simple Spring Boot demo app to demonstrate how to use Spring Security, Thymeleaf, JPA, Postgres and Customized Bootstrap all together.
In this application we have installed and used Node.js and different node modules with spring boot without running a seperate nodejs server and just by using Maven.
Primarily we have done this here in this project for theming bootstrap(i.e to update default styles and component changes). 
Actually to achieve it you need to use it's built-in Sass variables using 'scss' but '.scss' files are not supported by Java by default and we need to use node.js under the hood for such requirements.
Apart from that we have also configured and used spring-security in this project to demonstate how we can give access to some particular routes to some particular type of users only while some other routes to every users.
Like '/admin' route to only users with 'Admin' role, '/user' route to only users with 'User' role and '/','/home','/about','/signup' and '/login' routes to every user without even authentication.   

To run this application simply clone or download the project and run following maven commands.
# mvn clean install
# mvn spring-boot:run

[***Note :- When ever you update bootstrap default behaviour by making changes in "site.scss" file you will have to run both commands again.
As when you run "mvn clean install" then only scss file is converted into corresponding css file which Java can understand(as it don't support scss files directly yet).]
