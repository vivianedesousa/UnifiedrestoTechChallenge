package com.unifiedresto.platform.exception;
//Recurso não encontrado (404)
// ❌ Atualização com ID inexistente
// Onde usar ResourceNotFoundException
//Use somente quando:
//findById(id) não encontra nada
//existsById(id) retorna false
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
