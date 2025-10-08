package sv.edu.udb.dto; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Integer age;
}


