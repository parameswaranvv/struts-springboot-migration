# struts-spring-example

##Migrating a Struts 2 application to Spring Boot

##Steps:

* __pom.xml changes__

__Dependencies__
```xml
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <version>9.0.22</version>
        </dependency>
        <dependency>
            <groupId>org.apache.struts</groupId>
            <artifactId>struts2-core</artifactId>
            <version>2.5.20</version>
        </dependency>
        <dependency>
            <groupId>org.apache.struts</groupId>
            <artifactId>struts2-spring-plugin</artifactId>
            <version>2.5.20</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-core</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-beans</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-context</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.1.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-web -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
``` 

__Plugins__
```xml
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.1.7.RELEASE</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
```
__Properties__
```xml
    <properties>
        <maven.compiler.source>1.6</maven.compiler.source>
        <maven.compiler.target>1.6</maven.compiler.target>
    </properties>
```

* Remove web.xml and add a Spring Boot application class. 
* Add a Spring Configuration to register the Struts Filter Dispatcher
```java
@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<StrutsPrepareAndExecuteFilter> filterRegistrationBean() {
        FilterRegistrationBean<StrutsPrepareAndExecuteFilter> registrationBean = new FilterRegistrationBean<StrutsPrepareAndExecuteFilter>();
        StrutsPrepareAndExecuteFilter struts = new StrutsPrepareAndExecuteFilter();
        registrationBean.setFilter(struts);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setOrder(1);
        registrationBean.setInitParameters(Collections.singletonMap("actionPackages", "example.actions"));
        return registrationBean;
    }
}
```
* Annotate all the Action Classes and depending components to be autowired as `@Component`.
* Add a file in `src/main/resources`. Name: `struts.properties`
```properties
struts.convention.exclude.parentClassLoader=false
struts.convention.action.fileProtocols=jar,code-source
```
* Replace the actions and results from struts.xml with corresponding annotations in the Action classes. 

`HelloAction.java`
```java
@Service
@Namespaces({
        @Namespace("/")
})
public class HelloAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(HelloAction.class);

    private String message;

    @Action(value = "hello", results = {@Result(name = "success", location = "/WEB-INF/content/hello-success.jsp")})
    public String execute() throws Exception {
        log.info("In execute method of class Hello");
        message = "Hello from Struts 2 with no XML configuration.";
        return SUCCESS;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

```

`RegisterAction.java`

```java
@Service
@Namespaces({
        @Namespace("/")
})
public class RegisterAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(RegisterAction.class);

    @Autowired
    private Person personBean;

    @Action(value = "register-input", results = {@Result(name = "input", location = "/WEB-INF/content/register-input.jsp")})
    public String input() throws Exception {
        log.info("In input method of class RegisterAction");
        return INPUT;
    }

    @Action(value = "register", results = {@Result(name = "success", location = "/WEB-INF/content/register-success.jsp")})
    public String execute() throws Exception {
        log.info("In execute method of class RegisterAction");
        return SUCCESS;
    }

    public Person getPersonBean() {
        return personBean;
    }

    public void setPersonBean(Person person) {
        personBean = person;
    }
}
```

* Remove the `struts.xml` file