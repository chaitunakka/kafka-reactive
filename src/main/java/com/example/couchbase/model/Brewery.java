package com.example.couchbase.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
public class Brewery {

    @Field
    private String name;

    @Field
    private String city;

    @Field
    private String state;

    @Field
    private String code;

    @Field
    private String country;

    @Field
    private String description;
}
