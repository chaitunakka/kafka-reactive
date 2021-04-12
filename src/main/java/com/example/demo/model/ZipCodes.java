package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("zips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipCodes {

    @Id
    String id;
    String city;
    String loc;
    String pop;
    String state;
}
