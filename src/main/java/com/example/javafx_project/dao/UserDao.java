package com.example.javafx_project.dao;

import com.example.javafx_project.entities.User;

public interface UserDao {

    User getUserByUsername(String username);
}
