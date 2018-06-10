package pl.springExercises.users.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptionService {
    private PasswordEncoder passwordEncoder;

    public PasswordEncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //szyfrowanie hasła
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    //porównanie hasła z bazy
    public boolean matches(String password, String hash){
        return passwordEncoder.matches(password, hash);
    }
}
