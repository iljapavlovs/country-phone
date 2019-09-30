//package io.iljapavlovs.homework.exceptions;
//
//import io.iljapavlovs.homework.dtos.ApiErrorDto;
//import io.iljapavlovs.homework.exceptions.CountryNotFoundException;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.apache.http.HttpHeaders;
//import org.apache.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.util.WebUtils;
//
//@ControllerAdvice
//public class RestExceptionHandler {
//  private static final String UNEXPECTED_ERROR = "Exception.unexpected";
//  private final MessageSource messageSource;
//
//  @Autowired
//  public RestExceptionHandler(MessageSource messageSource) {
//    this.messageSource = messageSource;
//  }
//
//  @ExceptionHandler(value = RestException.class)
//  public ResponseEntity<RestMessage> handleIllegalArgument(RestException ex, Locale locale) {
//    String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
//    return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
//  }
//
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public ResponseEntity<RestMessage> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
//    BindingResult result = ex.getBindingResult();
//    List<String> errorMessages = result.getAllErrors()
//        .stream()
//        .map(objectError -> messageSource.getMessage(objectError, locale))
//        .collect(Collectors.toList());
//    return new ResponseEntity<>(new RestMessage(errorMessages), HttpStatus.BAD_REQUEST);
//  }
//
//  @ExceptionHandler(value = Exception.class)
//  public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
//    String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
//    ex.printStackTrace();
//    return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//  }
//}
