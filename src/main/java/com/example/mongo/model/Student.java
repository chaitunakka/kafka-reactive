package com.example.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

//@RedisHash
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    private String id;
    @Indexed
    private String name;
    private Gender gender;
}
