package com.shiro.repository;

import com.shiro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMapper extends JpaRepository<User,String> {
    /**
     * 获得密码
     * @param username 用户名
     */
    @Query("select u.password from User u where u.userName=?1")
    String getPassword(String username);

    /**
     * 获得角色权限
     * @param username 用户名
     * @return user/admin
     */
    @Query(nativeQuery = true,value="select role from role r where r.user_id=?1")
    Set<String> getRole(String username);
}
