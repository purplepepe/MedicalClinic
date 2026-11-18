package com.maksles.medicalclinic.dto;

// record = niezmienna klasa na dane a jackson sam zmapuje {"password": "..."}
public record ChangePasswordCommand(String oldPassword, String newPassword) {
}
