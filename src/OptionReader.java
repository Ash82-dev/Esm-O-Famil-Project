import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class OptionReader {
    File name = new File("Data base/names.txt");
    File family = new File("Data base/last names.txt");
    File city = new File("Data base/cities.txt");
    File country = new File("Data base/countries.txt");
    File food = new File("Data base/food.txt");
    File cloth = new File("Data base/clothes.txt");
    File car = new File("Data base/cars.txt");
    File flower = new File("Data base/flowers.txt");
    File fruit = new File("Data base/fruits.txt");
    File animal = new File("Data base/animals.txt");
    File object = new File("Data base/objects.txt");

    static String[] names = new String[99];
    static String[] families = new String[83];
    static String[] cities = new String[70];
    static String[] countries = new String[66];
    static String[] foods = new String[136];
    static String[] clothes = new String[68];
    static String[] cars = new String[48];
    static String[] flowers = new String[45];
    static String[] fruits = new String[48];
    static String[] animals = new String[60];
    static String[] objects = new String[69];

    public String[] getNames() {return names;}

    public String[] getFamilies() {return families;}

    public String[] getCities() {return cities;}

    public String[] getCountries() {return countries;}

    public String[] getFoods() {return foods;}

    public String[] getClothes() {return clothes;}

    public String[] getCars() {return cars;}

    public String[] getFlowers() {return flowers;}

    public String[] getFruits() {return fruits;}

    public String[] getAnimals() {return animals;}

    public String[] getObjects() {return objects;}

    public OptionReader() {
        try {
            Scanner scanner = new Scanner(name);
            for (int i = 0; i < 99; i++) {
                names[i] = scanner.next();
            }

            scanner = new Scanner(family);
            for (int i = 0; i < 83; i++) {
                families[i] = scanner.next();
            }

            scanner = new Scanner(city);
            for (int i = 0; i < 70; i++) {
                cities[i] = scanner.next();
            }

            scanner = new Scanner(country);
            for (int i = 0; i < 66; i++) {
                countries[i] = scanner.next();
            }

            scanner = new Scanner(food);
            for (int i = 0; i < 136; i++) {
                foods[i] = scanner.next();
            }

            scanner = new Scanner(cloth);
            for (int i = 0; i < 68; i++) {
                clothes[i] = scanner.next();
            }
            scanner = new Scanner(car);
            for (int i = 0; i < 48; i++) {
                cars[i] = scanner.next();
            }
            scanner = new Scanner(flower);
            for (int i = 0; i < 45; i++) {
                flowers[i] = scanner.next();
            }
            scanner = new Scanner(fruit);
            for (int i = 0; i < 48; i++) {
                fruits[i] = scanner.next();
            }
            scanner = new Scanner(animal);
            for (int i = 0; i < 60; i++) {
                animals[i] = scanner.next();
            }
            scanner = new Scanner(object);
            for (int i = 0; i < 69; i++) {
                objects[i] = scanner.next();
            }
        } catch (FileNotFoundException ignored) {}
    }
}