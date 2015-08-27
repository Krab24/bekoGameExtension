package com.beko.security;

import com.beko.beans.dao.UserDAO;
import com.beko.beans.entity.User;
import com.beko.utils.URLUtil;
import com.beko.utils.VKAuthUtil;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSConstants;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import java.util.Map;

/**
 * Created by ankovalenko on 4/25/2015.
 */
public class LoginEventHandler extends BaseServerEventHandler {

    private UserDAO userDAO;

    public LoginEventHandler(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public void handleServerEvent(ISFSEvent isfsEvent) throws SFSException {
        ISession session = (ISession) isfsEvent.getParameter(SFSEventParam.SESSION);
        if(isfsEvent.getType() == SFSEventType.USER_LOGIN){
            SFSObject params = (SFSObject) isfsEvent.getParameter(SFSEventParam.LOGIN_IN_DATA);
            if(params!=null){
                String url = params.getUtfString("authParams");
                if(url!=null && !url.isEmpty()){
                    try {
                        Map<String, String> authParams = URLUtil.extractParametersFromUrl(url);
                        if(!authParams.isEmpty()){
                            boolean isValidTokenForUser = false;
                            if(authParams.containsKey("viewer_id") && authParams.containsKey("access_token")){
                                isValidTokenForUser = VKAuthUtil.isVKParamsValid(authParams.get("viewer_id"), authParams.get("access_token"));
                            }
                            boolean isValidAuthKey = false;
                            if(authParams.containsKey("auth_key") && authParams.containsKey("viewer_id")){
                                isValidAuthKey = VKAuthUtil.isVKAuthKeyValid(authParams.get("auth_key"), authParams.get("viewer_id"));
                            }
                            if(!isValidTokenForUser || !isValidAuthKey){
                                throw new SFSLoginException("user token or auth key is invalid!");
                            }
                            else{
                                ISFSObject outData = (ISFSObject) isfsEvent.getParameter(SFSEventParam.LOGIN_OUT_DATA);
                                initUser(authParams.get("auth_key"), outData, session);
                            }
                        }
                        else{
                            throw new SFSLoginException("Null or empty auth params!");
                        }

                    }
                    catch (Exception e) {
                        throw new SFSLoginException("Bad uri params!");
                    }
                }
                else{
                    throw new SFSLoginException("Null or empty auth params!");
                }
            }
            else{
                throw new SFSLoginException("User not sended auth data");
            }
        }
    }

    private void initUser(String authKey, ISFSObject outData, ISession session){
        session.setProperty("authKey", authKey);
        User user = userDAO.findById(authKey);
        if(user==null || user.getLogin()==null){
            createNewUser(authKey);
            outData.putBool("isNewUser", true);
        }
        else{
            outData.putBool("isNewUser", false);
            String userName = user.getLogin();
            if(user.getLogin()!=null){
                outData.putUtfString(SFSConstants.NEW_LOGIN_NAME, userName);
            }
        }
    }

    private void createNewUser(String authKey){
        User newUser = new User();
        newUser.setId(authKey);
        userDAO.create(newUser);
    }
}
