package com.unifiedresto.platform.handler;
// O handler centraliza o tratamento de erros, captura exceções lançadas pelo
// service ou controller e
// converte essas exceções em respostas HTTP no padrão RFC 7807.”
// ele precisa ter aqui dentro
//globalExceptionHandler = classe anotada + método que captura exceção +
//monta ProblemDetail + devolve Response HTTP
//Global Exception Handler com ProblemDetail (RFC 7807)
import com.unifiedresto.platform.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;
@RestControllerAdvice

public class GlobalExceptionHandler {
    // ===============================
    // 409 - EMAIL DUPLICADO
    // ===============================
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(
            EmailAlreadyExistsException emailAlreadyExistsException,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse  = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        errorResponse.setTitle("Email is already registered.");
        errorResponse.setType(URI.create("https://unifiedresto/errors/email-already-exists"));
        errorResponse.setDetail(emailAlreadyExistsException.getMessage());
        errorResponse.setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse;
    }

    // ===============================
    // 409 - LOGIN DUPLICADO
    // ===============================
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ProblemDetail handleLoginAlreadyExists(
            LoginAlreadyExistsException loginAlreadyExistsException,
            HttpServletRequest  httpRequest
    ) {
        ProblemDetail errorResponse  = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        errorResponse.setTitle("Login already registered");
        errorResponse.setType(URI.create("https://unifiedresto/errors/login-already-exists"));
        errorResponse.setDetail(loginAlreadyExistsException.getMessage());
        errorResponse.setInstance(URI.create( httpRequest.getRequestURI()));
        return errorResponse ;
    }

    // ===============================
    // 409 - CPF DUPLICADO
    // ===============================
    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ProblemDetail handleCpfAlreadyExists(
            CpfAlreadyExistsException cpfAlreadyExistsException,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse  = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        errorResponse.setTitle("CPF already registered");
        errorResponse.setType(URI.create("https://unifiedresto/errors/cpf-already-exists"));
        errorResponse.setDetail(cpfAlreadyExistsException.getMessage());
        errorResponse.setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse ;
    }
    // ======================
       //409 - CNPJ DUPLICADO
     //========================
    @ExceptionHandler(CnpjAlreadyExistsException.class)
    public ProblemDetail handleCnpjAlreadyExists(
            CnpjAlreadyExistsException cnpjAlreadyExistsException,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        errorResponse.setTitle("CNPJ already registered");
        errorResponse.setType(URI.create("https://unifiedresto/errors/cnpj-already-exists"));
        errorResponse.setDetail(cnpjAlreadyExistsException.getMessage());
        errorResponse.setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse;
    }

    // ===============================
    // 401 - LOGIN INVÁLIDO
    // ===============================
    @ExceptionHandler(InvalidLoginException.class)
    public ProblemDetail handleInvalidLogin(
            InvalidLoginException invalidLoginException,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse  = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTitle("Invalid credentials");
        errorResponse.setType(URI.create("https://unifiedresto/errors/invalid-login"));
        errorResponse.setDetail(invalidLoginException.getMessage());
        errorResponse .setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse ;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handleInvalidPassword(
            InvalidPasswordException ex,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTitle("Invalid password");
        errorResponse.setType(URI.create("https://unifiedresto/errors/invalid-password"));
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse;
    }


    // ===============================
    // 404 - RECURSO NÃO ENCONTRADO
    // ===============================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(
            ResourceNotFoundException resourceNotFoundException,
            HttpServletRequest httpRequest
    ) {
        ProblemDetail errorResponse  = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTitle("Resource not found.");
        errorResponse.setType(URI.create("https://unifiedresto/errors/resource-not-found"));
        errorResponse.setDetail(resourceNotFoundException.getMessage());
        errorResponse.setInstance(URI.create(httpRequest.getRequestURI()));
        return errorResponse ;
    }
}


//    //metodo pegar a exercao geral
//   public ResponseEntity<ProblemDetail> handlerRuntime(
//    RuntimeException exception, HttpServletRequest request) {
//      ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//      problem.setTitle("Erro validation");
//      problem.setDetail(exception.getMessage());
//      problem.setType(URI.create("https://api.unifiedresto.com/errors/validation"));
//      problem.setInstance(URI.create(request.getRequestURI()));
//      return ResponseEntity.badRequest().body(problem);
