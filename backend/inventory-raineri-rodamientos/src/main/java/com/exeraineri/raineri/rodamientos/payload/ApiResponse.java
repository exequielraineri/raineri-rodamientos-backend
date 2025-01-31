package com.exeraineri.raineri.rodamientos.payload;


public record ApiResponse<T>(
     boolean success,
     int status,
     String message,
     T data
){}
