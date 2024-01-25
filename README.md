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
        