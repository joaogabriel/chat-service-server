package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.UserDocument;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {

    public static UserVO toChatUserVO(UserDocument userDocument) {
        return UserVO.builder()
                .token(userDocument.token)
                .name(userDocument.name)
                .build();
    }

    public static List<UserVO> toChatUserVO(List<UserDocument> userDocuments) {
        return userDocuments.stream()
                .map(UserAdapter::toChatUserVO)
                .collect(Collectors.toList());
    }

}
