package ru.edjll.backend.dto.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.backend.entity.City;
import ru.edjll.backend.entity.Country;
import ru.edjll.backend.validation.exists.Exists;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDtoForUpdate {

    @NotEmpty(message = "{city.title.notEmpty}")
    private String title;

    @NotNull(message = "{city.countryId.notNull}")
    @Exists(table = "country", column = "id", message = "{country.id.exists}")
    private Long countryId;

    public CityDtoForUpdate(@NotNull City city) {
        this.title = city.getTitle();
        this.countryId = city.getCountry().getId();
    }

    public City toCity() {
        City city = new City();
        Country country = new Country();

        country.setId(countryId);
        city.setTitle(title);
        city.setCountry(country);

        return city;
    }
}
