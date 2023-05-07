package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.Account;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface AccountService {
  Account<?> findOrCreate(Account<?> account);
}
