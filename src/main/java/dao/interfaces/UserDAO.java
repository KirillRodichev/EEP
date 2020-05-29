package dao.interfaces;

import model.entity.UserEntity;

public interface UserDAO {
    UserEntity getByEmail(String email);

    String getPassword(int id);
}
