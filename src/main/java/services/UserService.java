package services;

import dao.UserDAOImp;
import model.entity.UserEntity;

import java.util.List;

public class UserService {
    
    private UserDAOImp userDAOImp = new UserDAOImp();
    
    public UserService() {}

    public UserEntity get(int id) { return userDAOImp.getById(id); }

    public UserEntity getByEmail(String email) { return userDAOImp.getByEmail(email); }

    public String getPassword(int id) { return userDAOImp.getPassword(id); }

    public void create(UserEntity user) { userDAOImp.create(user); }

    public void delete(UserEntity user) { userDAOImp.delete(user); }

    public void update(UserEntity user) { userDAOImp.update(user); }

    public List<UserEntity> getAll() { return userDAOImp.getAll(); }
}
