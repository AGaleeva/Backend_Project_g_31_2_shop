package de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2 способ - размещение аннотации на самом классе исключения
// Минус - не можем отправить клиенту какое-то информативное сообщение
// Плюс - это глобальный обработчик ошибок, который реагирует на ошибки,
// выброшенные в любом месте приложения.
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SecondTestException extends RuntimeException {

    public SecondTestException(String message) {
        super(message);
    }
}
