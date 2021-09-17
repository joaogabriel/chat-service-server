package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("User")
public class UserModel {

    @Column("id")
    private String id;

    @Id
    @Column("token")
    public String token;

    @Column("name")
    public String name;

    public String getId() {
        return id;
    }

}
