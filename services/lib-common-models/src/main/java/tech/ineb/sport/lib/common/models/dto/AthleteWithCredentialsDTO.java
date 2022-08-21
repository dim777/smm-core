package tech.ineb.sport.lib.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AthleteWithCredentialsDTO {
  private UUID id;
  private CredentialsDTO credentials;
}
