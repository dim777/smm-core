package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.Account;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface AccountService {
  Account<?> findOrCreate(Account<?> account);
}
