package ru.edjll.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.backend.dto.city.CityDtoForSave;
import ru.edjll.backend.dto.city.CityDtoForUpdate;
import ru.edjll.backend.entity.City;
import ru.edjll.backend.service.CityService;
import ru.edjll.backend.validation.exists.Exists;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/city")
@Validated
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Collection<City> getCities(@RequestParam(required = false) Optional<Long> countryId) {
        return cityService.getAll(countryId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<City> getById(
            @PathVariable
            @NotNull(message = "{city.id.notNull}")
            @Positive(message = "{city.id.positive}")
            @Exists(table = "city", column = "id", message = "{city.id.exists}") Long id
    ) {
        return cityService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<City> getPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) Optional<String> idDirection,
            @RequestParam(required = false) Optional<String> titleDirection,
            @RequestParam(required = false) Optional<String> countryDirection,
            @RequestParam(defaultValue = "0") Long id,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String country
    ) {
        return cityService.getAll(page, size, idDirection, titleDirection, countryDirection, id, title, country);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid CityDtoForSave cityDtoForSave) {
        cityService.save(cityDtoForSave);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid CityDtoForUpdate cityDtoForUpdate) {
        cityService.update(cityDtoForUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam
            @NotNull(message = "{city.id.notNull}")
            @Positive(message = "{city.id.positive}")
            @Exists(table = "city", column = "id", message = "{city.id.exists}") Long id
    ) {
        cityService.delete(id);
    }
}
