package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    boolean existsByUserId(int userId);
    boolean existsByEmail(String email);
    boolean existsByEmailAndUserIdIsNot(String email,int userId);



}
