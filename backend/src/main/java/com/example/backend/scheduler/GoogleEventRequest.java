package com.example.backend.scheduler;

import lombok.Data;

@Data
public class GoogleEventRequest {
    private String title;
    private String description;
    private String startTime;
    private String endTime;


}
