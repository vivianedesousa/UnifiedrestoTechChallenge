package com.unifiedresto.platform.dto;
// fazer a troca de senha em endpoint separado
public class PasswordRequestDTO {
    private String password;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    private String currentPassword;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
}
