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

        @Value("${app.title:defaultTitle}")
        private String title; 
        
        @Value("#{${app.title:defaultTitle}.length()}")
        private String titleLength;

Rest API Standards

    REpresentational State Transfer API

    Resource : Employees
    EndPoint: /emps
                                                                                        ClientSide          ServerSide
    CRUD        Paths       Http-Methods    ReqBody     RespBody    SuccessStatus       FailureStatus       FailureStatus
    ----------------------------------------------------------------------------------------------------------------------
    Create      /emps       POST            empObj      empObj      201 - CREATED       400 - BAD REQUEST   500 -I S E  
    Retrive     /emps       GET             NA          emps[]      200 - OK                                500 -I S E
                /emps/{id}  GET             NA          empObj      200 - OK            404 - NOT FOUND     500 -I S E
    Update      /emps/{id}  PUT             empObj      empObj      202 - ACCEPTED      400 - BAD REQUEST   500 -I S E    
    Delete      /emps/{id}  DELETE          NA          NA          204 - NO CONTENT    404 - NOT FOUND     500 -I S E

Spring Web Rest API

    @RestController =   @Controller + @ResponseBody

    ResponseEntity = ResponseBody + HttpStatus

    Each action of RestController is to return a ResponseEntity

    @RequestMapping(url)
        @GetMapping
        @PutMapping
        @DeleteMapping
        @PostMapping

    The Model Class is marked with @XmlRoot

    @Consumes
    @Produces

 JPA - Annotations

        @Entity
        @Table

        @Inheretence(strategy = JOINED/SINGLE_TABLE/TABLE_PER_CLASS)

            Employee    eid,fullName,salary
                |
                --------------------------
                |                       |
                ContractEmployee        Manager
                    duration                allowence

            SINGLE_TABLE    allemps eid,fullName,sal,dur,allow

            JOINED          emps    eid,fullName,sal
                            cemps   eid,dur
                            mgrs    eid,allow

            TABLE_PER_CLASS emps1    eid,fullName,sal
                            cemps1   eid,fullName,sal,dur
                            mgrs1    eid,fullName,sal,allow

        @Embedable

        @Id
        @EmbededId
        @GeneratedValue
        @Column
        @Transiant
        @Enum
        @Embeded
        @OneToOne
        @OneToMany
        @ManyToOne
        @ManyToMany

Spring Data JPA

   this module provide auto-implemented repositories.

   CrudRepository
    |- JpaRepository
            save(entity)
            findAll()
            findById(id)
            deleteById(id)
            existsById(id)

    public interface EmployeeRepo extends JpaRepository<Employee,Long> {

        Optional<Employee> findByMailId(String mailId);
        boolean existsByMailId(String mailId);
        List<Employee> findAllByFirstName(String firstName);

        @Query("SELECT e FROM Employee e WHERE e.joinDate BETWEEN :start and :end")
        List<Employee> getAllInJoinDateRange(LocalDate start,LocalDate end);

        @Query("SELECT e.fullName,e.basicPay FROM Employee e")
        List<Object[]> getNamesAndBasicPays();
    }

Project Lombok

    @Getter
    @Setter
    @NoArgConstructor
    @Data           //generates all getters,setters and no-arg-constructor
    @Value
    @EqualsAndHashcode
    @AllArgConstructor
    @ToString
    
javax.validators

    @NotNull
    @NotBlank
    @Max
    @Min
    @Size
    @Present
    @Past
    @Future
    @Email
    @Pattern
    ...etc.,

@RestController
@RequestMapping("/emps")
public vlass EmployeeApi {

    @Autowired
    private EmployeeService empService;

    @PostMapping
    public ResponseEntity<Employee> add(@RequestBody @Valid Employee emp,BindingResult results) 
            throws InvalidEmployeeException{
        if(results.hasErrors()){
            throw new InvalidEmployeeException(results);
        }

        emp = empService.add(emp);
        return  new ResponseEntity<>(emp,HttpStatus.CREATED);
    }
}

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<String> handleInvalidEmployeeException(InvalidEmployeeException exp){
        BindingResult results = exp.getResults();
        //conver the resutls into errMessage
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exp){
        //log the exception
        return new ResponseEntity<>("Inconvinience Regretteed!",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
        
Micro Services
------------------------------------------------------------------------------

    Case Study BudgetTracking APP
        1. We need to have different consumer or account holders to register
        2. Each accountHolder mst be able to record his spending or earning transactions.
        3. Generate a statement periodically displaying the total spending , the total earning and the balance.


    Monolythical App 

        One build per application that contains all the modules of the application.
    
        1. Scalability
        2. Avialability
        3. Interoperability

            BudgetTracking Application
                1. Profiles Module
                2. Transactions Module
                3. Statement Module

    Microservices

        A microservice is an isolated independently deployable module of a large
        application eco system.

            we will have a spearate deployment for SalesModule
            we will have a spearate deployment for HRModule
            we will have a spearate deployment for DeliveriesModules ...etc.,

        Because each module is a spepart isolated deployment, we can scale them independnetly.
        Each modules can be shut down, maintained and redeployed without stopping other services.
        Each module (microservice) can be developed using any technology we want.

        Chanllenges in Developing and Maintaining Microservices

            - Decomposiiton
            - Inter-Service Communication
            - Single Point Of Contact
            - Monitoring and Maintaining

        Microservice Design Patterns

            Sub-Domain Pattern guides through bounded-context.

                We will decompose the budgetTrackinApp into 3 microservices
                    (a) Profiles-Service
                            AccountHolder
                                accountHolderId
                                fullName
                                mobileNumber
                                mailId
                                userId
                                password

                    (b) Transactions-Service
                            AccountHolder
                                accoountHolderId
                                txns: Set<Txn>

                            Txn
                                dateOfTxn
                                txnId
                                txnAmount
                                txnType
                                owner : AccountHolder

                    (c) Statement-Service                
                            AccountHolder
                                accountHolderId
                                fullName
                                mobileNumber
                                mailId

                            Txn
                                dateOfTxn
                                txnId
                                txnAmount
                                txnType

                            Statement
                                owner : AccountHolder
                                txns: Set<txns>
                                startDate: Date
                                endDate: Date
                                totalSpending
                                totalEarning
                                balance

            Shared Database Pattern

                Having a single DB for all microservices
                in brown field apps

            Database Per Service Pattern

                Each microservice has its own database
                in all green field apps

            Discovery Service Pattern

                discovery-service
                    |
                    |- all microservices will register their address with discovery-service
                    |- the address are retrived from here by the needy microservices

            Data Aggregation Pattern

                Aggregation is about desiging a microservice that can collect info
                from other microservices analyze and aggreagate the data and pass the 
                aggregated data to the client, sacing the client from making multiple requests
                for different parts of the data.

                the 'statement-microservice' is an example for this pattern.

            Client Side Component Pattern

                Each component of the UI/UX application can place
                their individual reqeusts to different microservices parellelly
                and should be receiving the resposnes as well parllelly.

            Distributed Tracing Design Pattern

                Tracing - Service

                    Whenever a request comes to any of the microservices in our app-ecosystem,
                    that request is given a unique ID and is reported to the Tracing-Service
                    every time, the request goes from one service to another service until
                    the final resposne is sent to the clinet. And the tracing-service
                    will record all the track of tehis request along with any performence
                    metrics and log info attached with the request.

            Load Balancing Desing Pattern

                load balalcing means mapping the incoming reqeusts to multiple instacnes of the 
                same microservice based on some (round-robin) algorithm.

                tools like Ribbon / Spring Cloud Load Balancer ..etc., are used to perform load 
                balancing.

            API Gateway Design Pattern

                gateway-service <------------(all reqs)--------------- any-client
                    |
                    | -> forward that request to the respective micro-service
                    | <- receives the response from that micro-service
                    |
                    |----------------------------------(response)---------> client

            Circuit - Breaker Design Pattern

                circuit-breaker-thrushold

                    when the first request could not reach a specific microservice (due to its down-time),
                    a fallback machanisim is triggered.

                    After that the circuit is made open (broken), means that the fallback machanisim
                    will address all the other consiquitive request targetting that microservice.

                    When a request to the sme micro-service is inbound after the thrushold, then the
                    circuit is half-closed, means that a new attemp to reach the microserivce is made,
                            }|- on successful contact, the circuit is closed
                             |- or if that microservice is still unavailable, the circuit continues to be open.

                    tools like Resiliance4j ..etc., are for the purpose.

            External-Cofiguaration Design Pattern

                    repository (github) [contains a list all config files of all microservice]
                        |
                        |                
                config-service
                            |<- when ever a microservice has to start, it will first send a fetech req the
                            |   the config-service
                            |
                            | the config-service will check for the cofnig file in the repo
                            |
                            |<- the config file is passed to the microservcei by the config-service
                            |
                            |<- wheever the config fiels are modified and pushed into the repo
                            |   the config service will automatically notify all the respective microservices
                                    and the microservices will receive the updated config-file and restart all by 
                                    themselves.

Decomposition by sub-domain

    budgettracking 
        profiles service
            AccountHolder Entity
                Long ahId
                String fullName
                String mobile
                String mailId

        txns service
            AccountHolder Entity
                Long ahId
                Double currentBalance
                Set<Txn> txns
            Txn           Entity
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate
                AccountHolder holder

        statement service
            AccountHolder Model
                Long ahId
                String fullName
                String mobile
                String mailId
                Double currentBalance

            Txn           Model
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate

            Statement     Model
                LocalDate start
                LocalDate end
                AccountHolder profile
                Set<Txn> txns
                totalCredit
                totalDebit
                statementBalance

Aggregator Pattern

    req for statement ------------> statement-service ---------------> profile service
                                                    <---account holder data---
                                                    --------------------> txns service
                                                    <----list of txns-------
                                     does the composition and computation
            <---statement obj-------  into statement obj

Discovery Service Design Pattern

                discovery-service
                (spring cloud netflix eureka discovery service)
                        ↑|
                    registration of urls 
                    and retrival of urls
                        |↓
            -------------------------------------
            |               |                   |
    profile-service     txns-service     statement-service

Api Gateway Pattern Design Pattern

    Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
       

Distributed Tracing

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓
             distrubuted tracing service
                    (zipkin-server)

External Configuaration

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓                      ↑↓
             distrubuted tracing service       configuaration-service 
                    (zipkin-server)         (spring cloud config service)
                                                    |
                                                    |
                                                    git-repo
                                                        profile.properties
                                                        txns.properties
                                                        statement.properties
                                                        gateway.properties

Implementing Budget-tracker
                                        
    Step#1  implementing decomposed services and do inter-service communication and aggregator
        in.bta:bta-profiles
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=profiles
                server.port=9100

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/bapsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-txns
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=txns
                server.port=9200

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/batxnsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-statement
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
            configuaration
                spring.application.name=statement
                server.port=9300

    Step#2  implementing discovery service and client side load balancing
        in.bta:bta-discovery
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-server
            configuaration
                @EnableEurekaServer    on Application class

                spring.application.name=discovery
                server.port=9000

                eureka.instance.hostname=localhost
                eureka.client.registerWithEureka=false
                eureka.client.fetchRegistry=false
                eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
                eureka.server.waitTimeInMsWhenSyncEmpty=0

        in.bta:bta-profiles
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-txns
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-statement
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false    

    Step 3: Implement API Gateway Design Pattern
        in.bta:bta-gateway
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-gateway
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                @EnableDiscoveryClient          on Application class

                spring.application.name=gateway
                server.port=9999

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.gateway.discovery.locator.enabled=true
                spring.cloud.gateway.discovery.locator.lower-case-service-id=true
                
        in.bta:bta-discovery
        in.bta:bta-profiles
        in.bta:bta-txns
        in.bta:bta-statement
              
    Step 4: Implement Distributed Tracing Design Pattern
          in.bta:bta-discovery
          
          in.bta:bta-gateway
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*
       
        in.bta:bta-profiles
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        in.bta:bta-txns
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        in.bta:bta-statement
            dependencies
                ++org.springframework.boot:spring-boot-starter-actuator
                ++org.springframework.cloud:spring-cloud-starter-sleuth
                ++org.springframework.cloud:spring-cloud-starter-zipkin : 2.2.8.RELEASE
            
            configuaration
                logger.level.org.springramework.web=debug
                management.endpoints.web.exposure.include=*

        tracing-service
            zipkin-server
                https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec 
                
                java -jar zipkin-server.jar

    Step 5: Implement Circuit Breaker Design Pattern
        in.bta:bta-discovery  
        in.bta:bta-gateway
        in.bta:bta-profiles
        in.bta:bta-txns
            dependencies
                ++org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j
            
            configuaration
                resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInClosedState=4
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInHalfOpenState=2
                resilience4j.circuitbreaker.configs.default.automaticTransitionFromOpenToHalfOpenEnabled=true
                resilience4j.circuitbreaker.configs.default.waitDurationInOpenState= 20s
                resilience4j.circuitbreaker.configs.default.failureRateThreshold= 50
                resilience4j.circuitbreaker.configs.default.eventConsumerBufferSize= 10

        in.bta:bta-statement
           dependencies
                ++org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j
            
            configuaration
                resilience4j.circuitbreaker.configs.default.registerHealthIndicator=true
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInClosedState=4
                resilience4j.circuitbreaker.configs.default.ringBufferSizeInHalfOpenState=2
                resilience4j.circuitbreaker.configs.default.automaticTransitionFromOpenToHalfOpenEnabled=true
                resilience4j.circuitbreaker.configs.default.waitDurationInOpenState= 20s
                resilience4j.circuitbreaker.configs.default.failureRateThreshold= 50
                resilience4j.circuitbreaker.configs.default.eventConsumerBufferSize= 10

    Step 6: External Configuaration Design Pattern
        inTheWorkSpace> md bt-props-repo
            //and then create these files in this directory
                // gateway.properties
                // profiles.properties
                // txns.properties
                // statement.properties
                // move the content of 'application.properties' of each microservice into these respective files
                
            inTheWorkSpace> cd bt-props-repo
            inTheWorkSpace\bt-props-repo> git init           
            inTheWorkSpace\bt-props-repo> git add .
            inTheWorkSpace\bt-props-repo> git commit -m "all service properties"
        
        in.bta:bta-discovery
        in.bta:bta-config
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-config-server
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
            
            configuaration  
                @EnableDiscoveryClient
                @EnableConfigServer             on Application class

                spring.application.name=config
                server.port=9090

                spring.cloud.config.server.git.uri=file:///local/git/repo/path

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5
        
        in.bta:bta-gateway
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=gateway
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/                    
        
        in.bta:bta-profiles
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=profiles
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   

        in.bta:bta-txns
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=txns
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   

        in.bta:bta-statement
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-bootstrap
                ++ org.springframework.cloud:spring-cloud-config-client

            configuaration - bootstrap.properties
                spring.cloud.config.name=statement
                spring.cloud.config.discovery.service-id=config
                spring.cloud.config.discovery.enabled=true
                
                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/   