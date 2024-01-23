Spring Boot - Microservices
---------------------------------------------------------

    Pre-Requisites
        Java 8
        Spring Core - IoC, DI, Spring Beans, Spring Context, SpEL
        Spring Data JPA
        Spring Web MVC (optional)

    Objectives
        Eurekha Registry, Zuul Server, Feign Client ,Ribbon
        Spring Boot initialization, Dependency Injection
        REST Web Service architecture , HTTP Endpoint handling
        cross origin, Errors and Exception Handling,
        Building custom response using Response Entity
        Validating input request using javax.validation
        Logging, Lombak

    Lab SetUp
        JDK 1.8
        STS latest (IDE) 
        Maven 3.x (included IDE)
        MySQL (or any RDMS) (optional)

    Spring Framework

        USP: Dependency Injection
                Constructor Injection
                Field Injection
                Setter Injection

        Spring is a very light platform due to its modularity
            Spring Core
            Spring Bean
            Spring AOP
            Spring Context
            Spring EL
            Spring Data
            Spring Web
            Spring Batch
            Spring ORM
            Spring Security
            Spring Test
            Spring Boot ...etc.,

        Interoparabillity

    Spring Boot

        Embeded Server
        AutoConfig - RAD (Rapid Application Development)

        Spring Boot Runners
            CommandLineRunner
            ApplicationRunner

        @SpringBootApplication = 
                @Configuaration
                @ComponentScan
                @PropertySource
                @EnableAutoConfig

        SpringApplication.run
            1. Load Default Bean Config
            2. ApplicationContext is created
            3. Perform all auto-configs
            4. Execute all Spring Runners (if any)
            5. Start the embeded server (if any)
            6. On Server Shutdown
                a) clear all resources
                b) terminate the applicationContext
                c) terminate the application

        