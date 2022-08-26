package hw9.domain;

import java.util.List;

public class Student {
    private String name;
    private Boolean human;
    private Integer age;
    private String[] family;
    private Animals animals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isHuman() {
        return human;
    }

    public void setHuman(Boolean human) {
        this.human = human;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getFamily() {
        return family;
    }

    public void setFamily(String[] family) {
        this.family = family;
    }

    public Animals getAnimals() {
        return animals;
    }

    public void setAnimals(Animals animals) {
        this.animals = animals;
    }

    public static class Animals {
        private String dog;
        private String cat;

        public String getDog() {
            return dog;
        }

        public void setDog(String dog) {
            this.dog = dog;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }
    }
}
