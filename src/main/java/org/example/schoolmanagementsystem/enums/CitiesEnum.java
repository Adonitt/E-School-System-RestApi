package org.example.schoolmanagementsystem.enums;

public enum CitiesEnum {
    PRISHTINE("KOSOVO"),
    BESIANE("KOSOVO"),
    FUSHEKOSOVE("KOSOVO"),
    FERIZAJ("KOSOVO"),
    MITROVICE("KOSOVO"),
    VUSHTRRI("KOSOVO"),
    PEJE("KOSOVO"),
    PRIZREN("KOSOVO"),

    TIRANA("ALBANIA"),
    DURRES("ALBANIA"),
    SHKODER("ALBANIA"),

    SKOPJE("NORTH_MACEDONIA"),
    TETOVO("NORTH_MACEDONIA"),

    PODGORICA("MONTENEGRO");

    private final String country;

    CitiesEnum(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
