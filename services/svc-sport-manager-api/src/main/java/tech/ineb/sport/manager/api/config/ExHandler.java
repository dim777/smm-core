package tech.ineb.sport.manager.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@ControllerAdvice @Slf4j
public class ExHandler {
  @ExceptionHandler(value = IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String handleBadRequest(IllegalArgumentException ex) {
    log.error("Illegal Argument = {}", ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(value = IllegalStateException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public String handleUnAuthorized(IllegalStateException ex) {
    log.error("Illegal State = {}", ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleDefaultError(RuntimeException ex) {
    log.error("Default Error = {}", ex.getMessage());
    return ex.getMessage();
  }

  //fixme: remove
//  @ExceptionHandler({FileNotFoundEx.class, MultipleFilesFoundEx.class})
//  public ResponseEntity<?> handleFileNotFound(FileNotFoundEx ex) {
//    log.error("File not found = {}", ex.getMessage());
//    return ResponseEntity
//        .notFound()
//        .build();
//  }
//
//  @ExceptionHandler({FailedFileFoundEx.class})
//  public ResponseEntity<?> handleFailedFile(FailedFileFoundEx ex) {
//    log.error("Failed file not found = {}", ex.getMessage());
//    return ResponseEntity
//        .notFound()
//        .build();
//  }
//
//  @ExceptionHandler({NotImplementedException.class})
//  public ResponseEntity<?> handleNotImplemented(NotImplementedException ex) {
//    log.error("Method not implemented = {}", ex.getMessage());
//    return ResponseEntity
//        .unprocessableEntity()
//        .build();
//  }
}
