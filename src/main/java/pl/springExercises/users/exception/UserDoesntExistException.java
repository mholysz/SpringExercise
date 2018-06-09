package pl.springExercises.users.exception;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException(){
        super("Uzytkownik nie istnieje");
    }
}
