package com.mcdonald.mcdonald.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mcdonald.mcdonald.Models.ResponseMessage;
import com.mcdonald.mcdonald.Models.UserModel;
import com.mcdonald.mcdonald.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> addUserService(UserModel user) {
        try {

            UserModel userExist = userRepository.findByUserName(user.getUserName());
            if (userExist == null) {
                userRepository.save(user);
                responseMessage.setSuccess(true);
                responseMessage.setMessage("User registred");
                return ResponseEntity.status(200).body(responseMessage);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("User already exists with this user name");
                return ResponseEntity.status(200).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }

    public String loginService(UserModel user) {
        UserModel userFromDB = userRepository.findByUserName(user.getUserName());
        if (userFromDB != null) {
            if (userFromDB.getPassword().equals(user.getPassword())) {
                return "Login successfully";
            } else {
                return "Invalid username or password";
            }
        } else {
            return "Invalid username or password";
        }
    }

    public List<UserModel> getAllUsersService() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUserByIdService(UUID userId) {
        try {
            Optional<UserModel> userFromDB = userRepository.findById(userId);
            if (userFromDB.isPresent()) {
                return ResponseEntity.status(200).body(userFromDB.get());
            } else {
                ResponseMessage responseMessage = new ResponseMessage();
                responseMessage.setSuccess(false);
                responseMessage.setMessage("No user has been found with this id");
                return ResponseEntity.status(404).body(responseMessage);
            }
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }

    public ResponseEntity<ResponseMessage> deleteUserService(UUID userId) {
        try {
            Optional<UserModel> userFromDB = userRepository.findById(userId);
            if (userFromDB.isPresent()) {
                userRepository.deleteById(userId);
                responseMessage.setSuccess(true);
                responseMessage.setMessage("User has been deleted");
                return ResponseEntity.status(200).body(responseMessage);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("No user has been found with this id");
                return ResponseEntity.status(404).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }

}
