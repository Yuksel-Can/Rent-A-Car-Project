package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.userDtos.gets.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.userDtos.lists.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;

import java.util.List;

public interface UserService {

    DataResult<List<UserListDto>> getAll();
    DataResult<GetUserDto> getById(int userId) throws BusinessException;

    boolean checkIfUserIdExists(int userId) throws BusinessException;
    boolean checkIfUserEmailNotExists(String email) throws BusinessException;
    boolean checkIfUserEmailNotExistsForUpdate(int userId, String email) throws BusinessException;

}
