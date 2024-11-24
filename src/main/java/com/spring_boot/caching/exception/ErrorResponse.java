package com.spring_boot.caching.exception;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse<T>{
    private Integer code;
    private String reason;
}
