package bank.rest.controllers.userregistration.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private String notificationChannel;

}
