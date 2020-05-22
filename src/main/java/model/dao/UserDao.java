package model.dao;

import com.boob.repository.JpaRepository;
import model.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {

}
