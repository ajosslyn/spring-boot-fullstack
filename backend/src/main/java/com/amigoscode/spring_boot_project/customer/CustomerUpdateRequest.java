package com.amigoscode.spring_boot_project.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
