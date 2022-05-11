package com.travel.proj.config.sessionConfig;

import com.travel.proj.model.User;
import com.travel.proj.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@WebListener
public class SessionConfig implements HttpSessionListener {

    @Autowired
    private static UserRepository repository;

    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public synchronized static String getSessionidCheck(String sessionName, String compareEmail){

        String result = "";
        for( String key : sessions.keySet() ){
            HttpSession hs = sessions.get(key);
            User user = new User();
            if(hs != null) {
                user = (User) hs.getAttribute(sessionName);
                if(user != null && user.getEmail().equals(compareEmail)) {
                    result = key.toString();
                }
            }
        }
        removeSessionForDoubleLogin(result);
        return result;
    }

    public static void removeSessionForDoubleLogin(String userEmail){
        log.info("remove userEmail : " + userEmail);
        if(userEmail != null && userEmail.length() > 0){
            sessions.get(userEmail).invalidate();
            sessions.remove(userEmail);
        }
    }
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(65);
        sessions.put(se.getSession().getId(),se.getSession());

    }

    public void sessionDestroyed(HttpSessionEvent se) {
        if(sessions.get(se.getSession().getId()) != null){
            sessions.get(se.getSession().getId()).invalidate();
            sessions.remove(se.getSession().getId());

        }
    }
}
