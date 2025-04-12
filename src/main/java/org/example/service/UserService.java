package org.example.service;

import org.example.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    //更新图片
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
