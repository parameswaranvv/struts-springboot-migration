package example.struts1;

import lombok.extern.slf4j.Slf4j;
import org.apache.struts.action.ActionServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean actionServlet() {
        ActionServlet servlet = new ActionServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "*.do");
        bean.addInitParameter("config", "/WEB-INF/struts-config.xml");
        bean.setLoadOnStartup(1);
        bean.setName("action");
        return bean;
    }
/*
    @Bean
    public ServletRegistrationBean tilesServletRegistrationBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new TilesServlet(),
                "*.tiles");
        Map<String, String> params = new HashMap<String, String>();
        params.put("definitions‐config", "/WEB‐INF/resources/tiles‐defs.xml");
        registrationBean.setInitParameters(params);
        registrationBean.setLoadOnStartup(3);
        return registrationBean;
    }*/
}
