package pl.springExercises.users.exception;

public class GroupDoesntExistException extends RuntimeException {

    public GroupDoesntExistException() {
        super("Grupa nie istnieje");
    }
}
