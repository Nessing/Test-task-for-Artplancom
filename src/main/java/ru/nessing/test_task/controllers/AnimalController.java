package ru.nessing.test_task.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.nessing.test_task.entities.Animal;
import ru.nessing.test_task.repositories.AnimalRepository;
import ru.nessing.test_task.services.AnimalService;

import java.util.Set;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    @GetMapping("/get_my_animals")
    @PreAuthorize("hasAuthority('user:read')")
    public Set<Animal> getUserAnimals(Authentication auth) {
        return animalService.getUserAnimals(auth.getName());
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('user:read')")
    public Animal getAnimal(@RequestBody Long id) {
        return animalRepository.getAnimalById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:read')")
    public Animal createAnimal(@RequestBody Animal animal, Authentication auth) {
        return animalService.createAnimalUser(auth.getName(), animal);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user:read')")
    public Animal updateAnimal(@RequestBody Animal animal) {
        return animalService.updateAnimalUser(animal);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasAuthority('user:read')")
    public Set<Animal> removeAnimal(@RequestBody Animal animal, Authentication auth) {
        return animalService.removeAnimalUser(auth.getName(), animal);
    }
}
