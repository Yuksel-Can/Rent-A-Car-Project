package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.dtos.userDtos.gets.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.userDtos.lists.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.UserDao;
import com.turkcell.rentACarProject.entities.abstracts.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private UserDao userDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
        this.userDao = userDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<UserListDto>> getAll() {

        List<User> users = this.userDao.findAll();

        List<UserListDto> result = users.stream().map(user -> this.modelMapperService.forDto().map(user, UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,"User listed");

    }

    @Override
    public DataResult<GetUserDto> getById(int userId) throws BusinessException {

        checkIfUserIdExists(userId);

        User user = this.userDao.getById(userId);

        GetUserDto result = this.modelMapperService.forDto().map(user, GetUserDto.class);

        return new SuccessDataResult<>(result, "User listed");

    }


    @Override
    public boolean checkIfUserIdExists(int userId) throws BusinessException {
        if(!this.userDao.existsByUserId(userId)){
          throw new BusinessException("User id not exists, userId: " + userId);
        }


        return true;
    }

    @Override
    public boolean checkIfUserEmailNotExists(String email) throws BusinessException {
        if(this.userDao.existsByEmail(email)){
            throw new BusinessException("User Already exists, email: " + email);
        }
        return true;
    }

    @Override
    public boolean checkIfUserEmailNotExistsForUpdate(int userId, String email) throws BusinessException {
        if(this.userDao.existsByEmailAndUserIdIsNot(email, userId)){
            throw new BusinessException("User Already exists, email: " + email);
        }
        return true;
    }

}
