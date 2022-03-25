package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.dtos.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.UserListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateUserRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteUserRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateUserRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
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
    public Result add(CreateUserRequest createUserRequest) {

        User user  = this.modelMapperService.forRequest().map(createUserRequest, User.class);

        //this.userDao.save(user);

        return new SuccessResult("User added");

    }

    @Override
    public Result update(UpdateUserRequest updateUserRequest) {

        User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);

        //this.userDao.save(user);

        return new SuccessResult(("User updated"));

    }

    @Override
    public Result delete(DeleteUserRequest deleteUserRequest) {

        //this.userDao.deleteById(deleteUserRequest.getUserId());

        return new SuccessResult("User deleted");

    }

    @Override
    public DataResult<GetUserDto> getById(int userId) {

        User user = this.userDao.getById(userId);

        GetUserDto result = this.modelMapperService.forDto().map(user, GetUserDto.class);

        return new SuccessDataResult<>(result, "User listed");

    }
}
