# struts-spring-example

##Migrating a Struts 1 application to Spring Boot

##Steps:

* __pom.xml changes__

__Dependencies__
```xml
    <dependencies>
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
            <artifactId>struts-core</artifactId>
            <version>1.3.10</version>
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
        <dependency>
            <groupId>org.apache.tiles</groupId>
            <artifactId>tiles-core</artifactId>
            <version>2.0.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
            <scope>provided</scope>
        </dependency>

    </dependencies>
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

* Keep `web.xml` as is. It is required because the Struts 1.x ActionServlet eagerly looks for the presence
of the web.xml.
* Add a `ServletRegistration` Bean in the Spring Boot Application class, that configures the
`struts-config.xml` in the actionServlet.
```java
@Configuration
public class FilterRegistrationConfig {

    @Bean
    public ServletRegistrationBean actionServlet() {
        ActionServlet servlet = new ActionServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "*.do");
        bean.addInitParameter("config", "/WEB-INF/struts-config.xml");
        bean.setLoadOnStartup(1);
        bean.setName("action");
        return bean;
    }
}
```
* Instead of annotating each Action as Spring Component (which is not possible because the 
Action classes are managed and loaded by the Struts Classloader and not the Spring Class loader),
we would create an abstract base class which would be inherited by all the Actions.
```java
public abstract class ApplicationContextAwareAction extends Action {

    protected AutowireCapableBeanFactory ctx;

    @Override
    public void setServlet(ActionServlet servlet) {
        super.setServlet(servlet);
        WebApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(servlet.getServletContext());
        ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

}
```

__Sample Action Class__
```java
public class LoginAction extends ApplicationContextAwareAction {

	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);


	@Value("${helloworld.message}")
	private String helloWorldMessage;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("Autowired message for Hello world: " + helloWorldMessage);
		String path = "error";
		LoginForm lf = (LoginForm) form;
		String userName = lf.getUserName();
		String password = lf.getPassword();
		if((userName != null && "admin".equals(userName)) &&   // 
				(password != null && "admin".equals(password))){
			path = "success";
			request.setAttribute("userName", userName);
		}
		return mapping.findForward(path);
	}
	
}
```