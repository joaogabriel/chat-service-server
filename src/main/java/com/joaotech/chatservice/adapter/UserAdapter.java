package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.User;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {

    public static UserVO toChatUserVO(User user) {
        return UserVO.builder()
                .token(user.token)
                .name(user.name)
                .build();
    }

    public static List<UserVO> toChatUserVO(List<User> users) {
        return users.stream()
                .map(UserAdapter::toChatUserVO)
                .collect(Collectors.toList());
    }

}
