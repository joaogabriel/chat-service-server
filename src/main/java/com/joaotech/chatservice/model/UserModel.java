package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
public class UserModel {

    @Column
    private String id;

    @Column("name")
    public String name;

    public String getId() {
        return id;
    }

}
