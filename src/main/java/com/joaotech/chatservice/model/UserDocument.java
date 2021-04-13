package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "User")
public class UserDocument {

    @Id
    private String id;
    public String token;
    public String name;
    public String color;

    public String getId() {
        return id;
    }

}
