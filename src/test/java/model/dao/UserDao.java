package model.dao;

import com.boob.repository.JpaRepository;
import model.entity.User;
import model.entity.UserType;

public interface UserDao extends JpaRepository<User, Integer> {

}
