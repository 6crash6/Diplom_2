package user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class User {
    private String email;
    private String password;
    private String name;

    public static User random(){
        return new User(RandomStringUtils.randomAlphabetic(4, 5)+ "@yandex.ru", "123456", "Serg");
    }

}

