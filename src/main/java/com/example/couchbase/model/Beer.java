package com.example.couchbase.model;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Data
public class Beer {

    @Field
    private String name;

    @Field
    private float abv;

    @Field("brewery_id")
    private String brewery;

    @Field
    private String description;
}
