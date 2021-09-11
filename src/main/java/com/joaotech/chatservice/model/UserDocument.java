package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {

    private String id;
    public String token;
    public String name;

    public String getId() {
        return id;
    }

}
