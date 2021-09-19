package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.UserModel;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {

    public static UserVO toChatUserVO(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        return UserVO.builder()
                .token(userModel.token)
                .name(userModel.name)
                .build();
    }

    public static List<UserVO> toChatUserVO(List<UserModel> userModels) {
        return userModels.stream()
                .map(UserAdapter::toChatUserVO)
                .collect(Collectors.toList());
    }

}
