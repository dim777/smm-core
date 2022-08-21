package tech.ineb.sport.lib.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
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
public class AthleteDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String country;
  private Integer ftp;
  private Set<AthletePlatformDTO> platforms = new HashSet<>();
}
