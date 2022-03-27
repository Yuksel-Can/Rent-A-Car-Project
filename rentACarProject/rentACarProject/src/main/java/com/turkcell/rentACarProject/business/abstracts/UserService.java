package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.UserListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateUserRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteUserRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateUserRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface UserService {

    DataResult<List<UserListDto>> getAll();

    Result add(CreateUserRequest createUserRequest) throws BusinessException;
    Result update(UpdateUserRequest updateUserRequest) throws BusinessException;
    Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException;

    DataResult<GetUserDto> getById(int userId) throws BusinessException;

    boolean checkIfUserIdExists(int userId) throws BusinessException;
    boolean checkIfUserEmailNotExists(String email) throws BusinessException;

}
