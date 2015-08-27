package com.beko.security;

import com.beko.beans.dao.UserDAO;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSUserException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
/**
 * Created by ankovalenko on 5/5/2015.
 */
public class NewUserLoginHandler extends BaseClientRequestHandler {

    private UserDAO userDAO;

    public NewUserLoginHandler(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public void handleClientRequest(User user, ISFSObject params) {
        String login = params.getUtfString("nick");
        String userId = (String) user.getSession().getProperty("authKey");
        com.beko.beans.entity.User currentUser = userDAO.findById(userId);
        if(currentUser.isNewUser()){
            currentUser.setNewUser(false);
            currentUser.setLogin(login);
            userDAO.update(currentUser);
            ISFSObject resObj = SFSObject.newInstance();
            resObj.putBool("userNameChanged", true);
            user.setName(login);
            send("newUserLogin", resObj, user);
        }
        else{
            new SFSUserException("you are already has login:"+currentUser.getLogin());
        }
    }
}
