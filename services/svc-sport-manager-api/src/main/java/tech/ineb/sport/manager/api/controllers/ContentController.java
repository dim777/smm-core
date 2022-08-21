package tech.ineb.sport.manager.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(("/api/v1/content"))
@AllArgsConstructor @Slf4j
public class ContentController {

  @RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Get public content", response = ResponseEntity.class)
  public ResponseEntity<String> home(){
    log.debug("");
    return ResponseEntity
        .ok("Sdfdfdf dfdfdf");
  }
}
