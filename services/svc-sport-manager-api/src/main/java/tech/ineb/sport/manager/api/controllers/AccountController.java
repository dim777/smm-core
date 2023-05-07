package tech.ineb.sport.manager.api.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ineb.sport.lib.common.models.Account;
import tech.ineb.sport.manager.api.services.AccountService;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@RestController @RequestMapping(("/api/v1/account"))
@AllArgsConstructor @Slf4j
public class AccountController {
  private final AccountService accountService;

  /**
   * Get latest info from provider account
   *
   * @param account sport data provider code
   * @return account
   */
  public ResponseEntity<Account<?>> sync(@RequestBody Account<?> account) {
//    accountService.
//    return responseEntity.getBody();
    return null;
  }
}
