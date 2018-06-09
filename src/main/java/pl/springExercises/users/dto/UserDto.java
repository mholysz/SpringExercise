package pl.springExercises.users.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String group;

}
