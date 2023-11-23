package com.example.mongo_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Investigation {
    @Id
    private Long id_investigation;
    private String name;
    private int duration;
    private String result;
}
