package bank.rest.controllers;

import bank.entity.UserDetails;
import bank.repository.UserDetailsRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@JsonSerialize
public class UsersRestController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping
    public List<UserDetails> getUsers() {
        return userDetailsRepository.findAll();
    }


}
