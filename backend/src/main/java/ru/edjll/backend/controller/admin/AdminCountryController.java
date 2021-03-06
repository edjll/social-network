package ru.edjll.backend.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.backend.dto.country.CountryDtoForSave;
import ru.edjll.backend.dto.country.CountryDtoForUpdate;
import ru.edjll.backend.entity.Country;
import ru.edjll.backend.service.CountryService;
import ru.edjll.backend.validation.exists.Exists;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@RestController
@RequestMapping("/admin/countries")
@Validated
public class AdminCountryController {

    private final CountryService countryService;

    public AdminCountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Country> get(
            @RequestParam @NotNull @PositiveOrZero Integer page,
            @RequestParam @NotNull @Positive Integer size,
            @RequestParam(required = false) Optional<String> idDirection,
            @RequestParam(required = false) Optional<String> titleDirection,
            @RequestParam(defaultValue = "0") Long id,
            @RequestParam(defaultValue = "") String title
    ) {
        return countryService.getAll(page, size, idDirection, titleDirection, id, title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestBody @Valid CountryDtoForSave countryDtoForSave
    ) {
        countryService.save(countryDtoForSave);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable @Positive Long id,
            @RequestBody @Valid CountryDtoForUpdate countryDtoForUpdate
    ) {
        countryService.update(id, countryDtoForUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable @Positive @Exists(table = "country", column = "id") Long id
    ) {
        countryService.delete(id);
    }
}
