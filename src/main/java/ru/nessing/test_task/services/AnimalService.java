package ru.nessing.test_task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nessing.test_task.entities.Animal;
import ru.nessing.test_task.entities.User;
import ru.nessing.test_task.repositories.AnimalRepository;
import ru.nessing.test_task.repositories.SexRepository;
import ru.nessing.test_task.repositories.TypeOfAnimalRepository;
import ru.nessing.test_task.repositories.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final SexRepository sexRepository;
    private final TypeOfAnimalRepository typeOfAnimalRepository;

    public User getUserByName(String name) {
        return userRepository.getUserByName(name).get();
    }

    public Set<Animal> getUserAnimals(String user) {
        User user1 = getUserByName(user);
        System.out.println(user1);
        return getUserByName(user).getAnimals();
    }

    public Animal createAnimalUser(String nameUser, Animal animal) {
        Animal createAnimal = createAnimal(animal);
        User user = userRepository.getUserByName(nameUser).get();
        animalRepository.save(createAnimal);
        Set<Animal> animals = getUserAnimals(nameUser);
        animals.add(animalRepository.getAnimalByName(animal.getName()));
        userRepository.save(user);
        return createAnimal;
    }

    public Animal updateAnimalUser(Animal animal) {
        if (animalRepository.getAnimalByName(animal.getName()) == null) {
            return null;
        }
        Animal createAnimal = createAnimal(animal);
        createAnimal.setId(animalRepository.getAnimalByName(animal.getName()).getId());
        animalRepository.save(createAnimal);
        return createAnimal;
    }

    public Set<Animal> removeAnimalUser(String nameUser, Animal animal) {
        Animal animalGet = animalRepository.getAnimalByName(animal.getName());
        User user = userRepository.getUserByName(nameUser).get();
        Set<Animal> animals = getUserAnimals(nameUser);
        animals.remove(animalGet);
        animalRepository.delete(animalGet);
        animals.remove(animalRepository.getAnimalByName(animal.getName()));
        userRepository.delete(user);
        return animals;
    }

    private Animal createAnimal(Animal animal) {
        Animal createAnimal = new Animal();
        createAnimal.setTypeOfAnimal(animal.getTypeOfAnimal());
        createAnimal.setName(animal.getName());
        createAnimal.setTypeOfAnimal(
                typeOfAnimalRepository.getTypeOfAnimalByName(animal.getTypeOfAnimal().getName())
        );
        createAnimal.setSex(sexRepository.getSexByName(animal.getSex().getName()));
        createAnimal.setBirthday(animal.getBirthday());
        return createAnimal;
    }
}
