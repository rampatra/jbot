package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.facebook.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author ramswaroop
 * @version 01/08/2017
 */
@Service
@Scope("prototype")
public class FbService {
    
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
