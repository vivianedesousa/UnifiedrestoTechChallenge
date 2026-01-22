package com.unifiedresto.platform.handler;
import com.unifiedresto.platform.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ProblemDetail;
import jakarta.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
  private GlobalExceptionHandler handler;
  private HttpServletRequest httpRequest;
   @BeforeEach
        void setup() {
            handler = new GlobalExceptionHandler();
            httpRequest = Mockito.mock(HttpServletRequest.class);
            Mockito.when(httpRequest.getRequestURI()).thenReturn("/test");
        }

        @Test
        void testEmailAlreadyExistsException() {
            ProblemDetail problemDetail = handler.handleEmailAlreadyExists(
                    new EmailAlreadyExistsException(), httpRequest
            );
            assertEquals("Email is already registered.", problemDetail.getTitle());
        }

        @Test
        void testLoginAlreadyExistsException() {
            ProblemDetail problemDetail = handler.handleLoginAlreadyExists(
                    new LoginAlreadyExistsException(), httpRequest
            );

            assertEquals("Login already registered", problemDetail.getTitle());
        }

        @Test
        void testCpfAlreadyExistsException() {
            ProblemDetail problemDetail = handler.handleCpfAlreadyExists(
                    new CpfAlreadyExistsException(), httpRequest
            );
            assertEquals("CPF already registered", problemDetail.getTitle());
        }


    @Test
    void testCnpjAlreadyExistsException() {
        ProblemDetail problemDetail = handler.handleCnpjAlreadyExists(
                new CnpjAlreadyExistsException(), httpRequest
        );

        assertEquals("CNPJ already registered",problemDetail.getTitle());
     }


    @Test
        void testInvalidLoginException() {
            ProblemDetail problemDetail = handler.handleInvalidLogin(
                    new InvalidLoginException("Invalid"), httpRequest
            );
            assertEquals("Invalid credentials", problemDetail.getTitle());
        }

    @Test
    void testInvalidPasswordException() {
        ProblemDetail problemDetail = handler.handleInvalidPassword(
                new InvalidPasswordException(), httpRequest
        );


        assertEquals("Invalid password", problemDetail.getTitle());
    }


        @Test
        void testResourceNotFoundException() {
            ProblemDetail problemDetail = handler.handleResourceNotFound(
                    new ResourceNotFoundException("Not found"), httpRequest
            );
            assertEquals("Resource not found.", problemDetail.getTitle());
        }
    }

