package tech.ineb.sport.manager.api.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ineb.sport.lib.common.models.Account;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Service @AllArgsConstructor @Slf4j
public class BaseAccountService implements AccountService {
  private final PlatformService platformService;

  @Override @Transactional
  public Account<?> findOrCreate(Account<?> account) {
    //todo: fixed account
    final PlatformDTO platform = platformService.findByCode(account.getPlatformCode());
    return null;
  }
}
