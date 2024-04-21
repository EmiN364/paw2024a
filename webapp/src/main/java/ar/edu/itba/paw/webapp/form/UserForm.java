package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Puedo usar validaciones propias tanto a nivel de form como a nivel de atributo
// Tmb puedo usar grupos para las validaciones (creacion y edicion)

public class UserForm {

    @NotEmpty
    @Size(min = 6, max = 255)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotEmpty
    @Size(min = 8)
    private String repeatPassword;

    // "bean" != Java Enterprise Beans
    // "bean" == record
    /*
    * "bean":
    *        1. constructor default
    *        2. getters
*            3. setters
    * */
    // record Java cumple con 2 y 3, se le pasan todos los parametros en el constructor

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
