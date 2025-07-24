package ru.shestakov.api.models;

import lombok.Data;

@Data
public class StoreDeleteResponse {
    private Integer code;
    private String type;
    private String message;
}
