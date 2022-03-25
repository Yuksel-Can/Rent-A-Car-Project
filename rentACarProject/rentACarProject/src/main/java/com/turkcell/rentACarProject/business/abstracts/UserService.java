package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.UserListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateUserRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteUserRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateUserRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface UserService {

    DataResult<List<UserListDto>> getAll();

    Result add(CreateUserRequest createUserRequest);
    Result update(UpdateUserRequest updateUserRequest);
    Result delete(DeleteUserRequest deleteUserRequest);

    DataResult<GetUserDto> getById(int userId);
}
