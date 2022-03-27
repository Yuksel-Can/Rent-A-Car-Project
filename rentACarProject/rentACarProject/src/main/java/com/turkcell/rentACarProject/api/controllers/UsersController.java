package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.dtos.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
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

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public DataResult<List<UserListDto>> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetUserDto> getById(@RequestParam int userId) throws BusinessException {
        return this.userService.getById(userId);
    }
}
