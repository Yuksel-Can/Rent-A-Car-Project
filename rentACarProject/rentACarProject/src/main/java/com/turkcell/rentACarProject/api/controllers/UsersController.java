package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.dtos.userDtos.gets.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.userDtos.lists.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getAll")
    public DataResult<List<UserListDto>> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetUserDto> getById(@RequestParam int userId) throws UserNotFoundException {
        return this.userService.getById(userId);
    }

}
