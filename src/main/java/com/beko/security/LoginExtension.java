package com.beko.security;

import com.beko.beans.dao.UserDAO;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import com.smartfoxserver.v2.extensions.SFSExtension;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by ankovalenko on 4/25/2015.
 */
public class LoginExtension extends SFSExtension {

    private static final String CONFIG_PATH = "/extensions/__lib__/config/app-context.xml";
    protected static GenericApplicationContext context;

    @Override
    public void init() {
        initSpring();
        UserDAO userDAO =context.getBean(UserDAO.class);
        addEventHandler(SFSEventType.USER_LOGIN, new LoginEventHandler(userDAO));
        addRequestHandler("newUserLogin", new NewUserLoginHandler(userDAO));
        trace(ExtensionLogLevel.INFO, "INIT LOGIN EXTENSION");
    }

    private void initSpring(){
        try{
            context = new GenericApplicationContext();
            XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context);
            xmlReader.loadBeanDefinitions(new ClassPathResource(CONFIG_PATH));
            context.refresh();
        }
        catch(Exception e){
            trace(ExtensionLogLevel.INFO, "ERROR TO CREATE CONTEXT");
            trace(ExtensionLogLevel.ERROR, e.getMessage());
        }
    }

    public GenericApplicationContext getContext() {
        return context;
    }
}
