package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.UserTable;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserTable userTab;
    
    public Long getUserId(String username) {
	return userTab.getUserId(username);
    }
    
    public boolean isExisting(String userEmail) {
	return userTab.isExisting(userEmail);
    }
    
    public long createUser(String username, String email, String password) {
	password = encode(password);
	email = email.toLowerCase();
	return userTab.insert(username, email, password);
    }
    
    public static String encode(String pswd) {
	return new String(Base64.encode(pswd.getBytes()));
    }
}

