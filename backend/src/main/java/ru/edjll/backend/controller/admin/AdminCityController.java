package ru.edjll.backend.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.backend.dto.city.CityDto;
import ru.edjll.backend.dto.city.CityDtoForSave;
import ru.edjll.backend.dto.city.CityDtoForUpdate;
import ru.edjll.backend.entity.City;
import ru.edjll.backend.service.CityService;
import ru.edjll.backend.validation.exists.Exists;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@RestController
@RequestMapping("/admin/cities")
@Validated
public class AdminCityController {

    private final CityService cityService;

    public AdminCityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CityDto> get(
            @RequestParam @NotNull @PositiveOrZero Integer page,
            @RequestParam @NotNull @Positive Integer size,
            @RequestParam(required = false) Optional<String> idDirection,
            @RequestParam(required = false) Optional<String> titleDirection,
            @RequestParam(required = false) Optional<String> countryDirection,
            @RequestParam(defaultValue = "-1") Integer id,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String country
    ) {
        return cityService.getAll(page, size, idDirection, titleDirection, countryDirection, id, title, country);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestBody @Valid CityDtoForSave cityDtoForSave
    ) {
        cityService.save(cityDtoForSave);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable @Positive Long id,
            @RequestBody @Valid CityDtoForUpdate cityDtoForUpdate
    ) {
        cityService.update(id, cityDtoForUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable @Positive(message = "{city.id.positive}") @Exists(table = "city", column = "id") Long id
    ) {
        cityService.delete(id);
    }
}
