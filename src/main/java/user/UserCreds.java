package user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class UserCreds {
        private String email;
        private String password;

    public static UserCreds from(User user){
        return new UserCreds(user.getEmail(),user.getPassword());
    }
}
