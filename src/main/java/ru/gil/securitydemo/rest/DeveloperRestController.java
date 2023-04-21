package ru.gil.securitydemo.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gil.securitydemo.model.Developer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/developers")
public class DeveloperRestController {
    private List<Developer> developers = new ArrayList<>(Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Sergey", "Sergeev"),
            new Developer(3L, "Petr", "Petrov")
    ).toList());

    @GetMapping
    @PreAuthorize("hasAuthority(\"developers.read\")")
    public List<Developer> getAll() {
        return developers;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"developers.write\")")
    public Developer getById(@PathVariable Long id) {
        return developers.stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst().orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(\"developers.write\")")
    public Developer createDeveloper(@RequestBody Developer developer) {
        developers.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"developers.write\")")
    public Developer deleteDeveloper(@PathVariable Long id) {

        Developer developer = developers.stream()
                .filter(dev -> dev.getId().equals(id))
                .findFirst().orElse(null);
        if (developer != null) {
            developers.remove(developer);
        }
        return developer;
    }
}
