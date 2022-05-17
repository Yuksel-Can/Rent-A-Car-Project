package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.userDtos.gets.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.userDtos.lists.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserEmailNotValidException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;

import java.util.List;

public interface UserService {

    DataResult<List<UserListDto>> getAll();
    DataResult<GetUserDto> getById(int userId) throws UserNotFoundException;

    boolean checkIfUserIdExists(int userId) throws UserNotFoundException;
    boolean checkIfUserEmailNotExists(String email) throws UserAlreadyExistsException;
    boolean checkIfUserEmailNotExistsForUpdate(int userId, String email) throws UserEmailNotValidException;

}
