package example.struts1.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
