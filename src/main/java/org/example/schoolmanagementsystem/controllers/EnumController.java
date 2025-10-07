package org.example.schoolmanagementsystem.controllers;

import org.example.schoolmanagementsystem.enums.CitiesEnum;
import org.example.schoolmanagementsystem.enums.CountryEnum;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enums")
@CrossOrigin(origins = "*")
public class EnumController {

    @GetMapping("/countries")
    public List<String> getCountries() {
        return Arrays.stream(CountryEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/cities/{country}")
    public List<String> getCitiesByCountry(@PathVariable String country) {
        return Arrays.stream(CitiesEnum.values())
                .filter(city -> city.getCountry().equalsIgnoreCase(country))
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
