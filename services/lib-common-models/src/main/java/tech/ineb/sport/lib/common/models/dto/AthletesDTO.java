package tech.ineb.sport.lib.common.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Getter
@Setter
public class AthletesDTO implements Iterable<AthleteDTO> {
  private Set<AthleteDTO> athletes;

  public AthletesDTO() {
    this.athletes = new HashSet<>();
  }

  public void add(final AthleteDTO athleteDTO) {
    athletes.add(athleteDTO);
  }

  public void remove(final AthleteDTO athleteDTO) {
    athletes.remove(athleteDTO);
  }

  @Override
  public Iterator<AthleteDTO> iterator() {
    return athletes.iterator();
  }
}