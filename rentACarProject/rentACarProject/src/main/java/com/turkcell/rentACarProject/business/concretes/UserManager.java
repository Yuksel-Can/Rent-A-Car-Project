package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.userDtos.gets.GetUserDto;
import com.turkcell.rentACarProject.business.dtos.userDtos.lists.UserListDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserEmailNotValidException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserNotFoundException;
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

    private final UserDao userDao;
    private final ModelMapperService modelMapperService;

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

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<GetUserDto> getById(int userId) throws UserNotFoundException {

        checkIfUserIdExists(userId);

        User user = this.userDao.getById(userId);

        GetUserDto result = this.modelMapperService.forDto().map(user, GetUserDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + userId);
    }


    @Override
    public boolean checkIfUserIdExists(int userId) throws UserNotFoundException {
        if(!this.userDao.existsByUserId(userId)){
          throw new UserNotFoundException(BusinessMessages.UserMessages.USER_ID_NOT_FOUND + userId);
        }
        return true;
    }

    @Override
    public boolean checkIfUserEmailNotExists(String email) throws UserAlreadyExistsException {
        if(this.userDao.existsByEmail(email)){
            throw new UserAlreadyExistsException(BusinessMessages.UserMessages.USER_EMAIL_ALREAY_EXISTS + email);
        }
        return true;
    }

    @Override
    public boolean checkIfUserEmailNotExistsForUpdate(int userId, String email) throws UserEmailNotValidException {
        if(this.userDao.existsByEmailAndUserIdIsNot(email, userId)){
            throw new UserEmailNotValidException(BusinessMessages.UserMessages.USER_EMAIL_ALREAY_EXISTS + email);
        }
        return true;
    }

}
