package ru.edjll.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CityDto {

    private Long id;
    private String title;
    private CountryDto country;

    public CityDto(Long id, String title, Long countryId, String countryTitle) {
        this.id = id;
        this.title = title;
        this.country = new CountryDto(countryId, countryTitle);
    }
}