package com.mcdonald.mcdonald.Controllers;

import org.springframework.stereotype.Controller;

import com.mcdonald.mcdonald.GraphQlModels.UserInput;
import com.mcdonald.mcdonald.Models.ResponseMessage;
import com.mcdonald.mcdonald.Models.UserModel;
import com.mcdonald.mcdonald.Services.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @QueryMapping("allUsers")
    public List<UserModel> getUsers() {
        return userService.getAllUsersService();
    }

    @QueryMapping("getUserById")
    public Object getUserById(@Argument UUID userId) {
        ResponseEntity<?> responseEntity = userService.getUserByIdService(userId);
        if (responseEntity.getBody() instanceof UserModel) {
            return responseEntity.getBody();
        } else {
            return responseEntity.getBody();
        }
    }

    @MutationMapping("createUser")
    public ResponseMessage createUser(@Argument UserInput userInput) {
        UserModel user = new UserModel();
        user.setUserName(userInput.getUserName());
        user.setPassword(userInput.getPassword());
        ResponseEntity<ResponseMessage> responseEntity = userService.addUserService(user);
        return responseEntity.getBody();
    }

    @MutationMapping("deleteUser")
    public ResponseMessage deleteUser(@Argument UUID userId) {
        ResponseEntity<ResponseMessage> responseEntity = userService.deleteUserService(userId);
        return responseEntity.getBody();
    }
}
