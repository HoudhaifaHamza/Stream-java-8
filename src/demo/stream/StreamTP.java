package demo.stream;

import demo.constants.Gender;
import demo.model.User;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTP {

    static User[] users = {
            new User(1, "Houdhaifa", "Hamza", 33,"421520143645", Gender.MALE),
            new User(2, "Wajdi", "Hamza", 37, "421330143645", Gender.MALE),
            new User(3, "Salem", "Hamza", 31, "421520141022", Gender.FEMALE),
            new User(4, "Monjia", "Arfa", 65, "426300143645", Gender.FEMALE),
            new User(5, "Hamza", "Faleh", 63, "021520143645", Gender.MALE),
            new User(6, "Salem", "Pazel", 72, "421520115645", Gender.MALE),
            new User(7, "Fathia", "Raouhi", 54, "421520745", Gender.FEMALE),
            new User(8, "Kaouther", "Ghanem", 28, "421870143645", Gender.FEMALE),
            new User(9, "Sarah", "Lamti", 18, "421503143645", Gender.FEMALE),
            new User(10, "Faten", "Dali", 39, "111520143645", Gender.FEMALE),
            new User(11, "Wajdi", "Zribi", 24, "421520143699", Gender.MALE),
            new User(12, "Omar", "Varimi", 17, "721520143645", Gender.MALE),
            new User(13, "Walid", "Mamlouk", 38, "321520143645", Gender.MALE),
            new User(14, "Hasnaa", "Sehli", 102, "421520147845", Gender.FEMALE),
            new User(15, "Ahmed", "Chouchane", 92, "741520143645", Gender.MALE),
            new User(16, "Amal", "Berradi", 34, "421520185475", Gender.FEMALE),
            new User(17, "Ons", "Jmiyi", 33, "421520143475", Gender.FEMALE),
            new User(18, "Teyssir", "Nafti",  33, "426520173645", Gender.MALE),
            new User(19, "Nessrine", "Zaki", 37, "421520143674", Gender.FEMALE),
            new User(20, "Badis", "Ben Ahmed", 37, "421520110645", Gender.MALE),
            new User(21, "Mohammed", "Ben Mohammed", 24, "421528843645", Gender.MALE),
            new User(22, "Cyrine", "Mazni", 24, "421892143645", Gender.FEMALE)
    };


    static List<String> listSimpleAndPalandromeWords = Arrays.asList("Solos", "Anna", "Civic", "Civic", "Ben Mohammed", "Kayak", "Mohammed", "Level", "Madam", "Mom", "Noon", "Racecar", "Radar", "Redder",
            "Refer", "Jmiyi", "Solos", "Repaper", "Sagas", "Chouchane", "Rotator", "Rotor", "Solos", "Civic", "Sagas", "Zaki", "Refer", "Solos", "Zribi", "Solos", "Stats", "Tenet", "Varimi", "Wow", "Solos");

    static List<Integer> listNumbers = Arrays.asList(1, 2, 3, 4, 56, 102, -5, 74, 6954, 201, 22, 44, 7, 6, 4, 1, 22, 44, 3, 458, 774, 224, 66, 44, 54, 21, 25, 14, 20);


    static List<User> usersList = Arrays.asList(users);

    static Integer maxNumber = listNumbers.stream()
            .max(Comparator.comparing(x -> x))
            .orElseThrow(NoSuchElementException::new);

    static Set<Integer> uniqueNumbers = listNumbers.stream()
            .collect(Collectors.toSet());


    static List<String> listAgesUsers = usersList.stream()
            .map(x -> x.getFirstname())
            .collect(Collectors.toList());

    static Integer maxAgeOfUsers = usersList.stream()
            .mapToInt(x -> x.getAge())
            .max().orElseThrow(NoSuchElementException::new);

    static List<String> uniqueLastNamesSorted = usersList.stream()
            .sorted(Comparator.comparing(x -> x.getLastname()))
            .map(x -> x.getLastname())
            .distinct()
            .collect(Collectors.toList());

    static Double averageAges = usersList.stream()
            .mapToLong(x -> x.getAge())
            .average().orElseThrow(NoSuchElementException::new);

    static Boolean isAllMacth = usersList.stream()
            .allMatch(x -> x.getAge() < 103);

    static Boolean isAnyMacth = usersList.stream()
            .anyMatch(x -> x.getAge() < 17);


    static String allHamzaFamilyJoining = usersList.stream()
            .filter(x -> "Hamza".equals(x.getLastname()))
            .map(x -> x.getFirstname().concat(" "+x.getLastname()))
            .collect(Collectors.joining(", "));


    static String firstHaveAgeGreaterThanFifty = usersList.stream()
            .filter(x -> x != null && x.getAge() > 50)
            .findFirst()
            .map(x -> x.getFirstname() + " " + x.getLastname())
            .orElseThrow(NoSuchElementException::new);


    static Map<Integer, List<String>> firstNamesByAge = usersList.stream()
            .collect(Collectors.groupingBy(x -> x.getAge(), Collectors.mapping(x -> x.getFirstname() + " " + x.getLastname()
                    , Collectors.toList())));


    static Map<Gender, List<String>> listNamesByGender = usersList.stream()
            .collect(Collectors.groupingBy(x -> x.getGender(),Collectors.mapping(x -> x.getFirstname() + " " + x.getLastname() , Collectors.toList())));

    static List<String> onlyPalandromesWordsNoDistinctSortedUppercase = listSimpleAndPalandromeWords.stream()
            .map(String::toUpperCase)
            .filter(x -> x.length() > 1)
            .filter(x -> IntStream.range(0, x.length() / 2).allMatch(i -> x.charAt(i) == x.charAt(x.length() - i - 1)))
            .sorted()
            .collect(Collectors.toList());

    static Long mostPalandromeWordOcc = onlyPalandromesWordsNoDistinctSortedUppercase.stream()
            .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getValue)
            .orElseThrow(NoSuchElementException::new);

    static Map<String, Long> mostPalandromeWord = onlyPalandromesWordsNoDistinctSortedUppercase.stream()
            .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
            .entrySet()
            .stream()
            .filter(x -> x.getValue() == mostPalandromeWordOcc)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    static Map.Entry<String, Long> mostPalandromeWordSimplified = onlyPalandromesWordsNoDistinctSortedUppercase.stream()
            .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .get();

    static DoubleSummaryStatistics statsAgesUsers = usersList.stream()
            .mapToDouble(x -> x.getAge())
            .summaryStatistics();

    static Map<Boolean, List<String>> getPartionnedLastNamesAndFirstNamesByIfContainsH = usersList.stream()
            .map(x -> x.getFirstname().concat(" "+x.getLastname()))
            .collect(Collectors.partitioningBy(x -> x.chars().anyMatch(i -> "Hh".contains(Character.valueOf(((char) i)).toString()))));

    static Map<Boolean, List<String>> getPartionnedLastNamesAndFirstNamesByIfContainsHamza = usersList.stream()
            .map(x -> x.getFirstname().concat(" "+x.getLastname()))
            .collect(Collectors.partitioningBy(x -> x.contains("Hamza")));

    static Map<String, String> getNamesByValidNumbers = usersList.stream()
            .distinct()
            .filter(x -> x.getPhone().startsWith("42") && x.getPhone().length() == 12 )
            .collect(Collectors.toMap( x -> x.getPhone() ,x -> x.getFirstname().concat(" "+ x.getLastname() )));


    static List<Double> rondomListOfIntegers = Stream.generate(Math::random)
            .limit(20)
            .collect(Collectors.toList());

    static List<Integer> randomIntegers = new Random().ints(10, 0, 30).boxed().collect(Collectors.toList());

    static List<Integer> randomIntegers2 = IntStream.range(0,20)
            .mapToObj(i -> ThreadLocalRandom.current().nextInt(0, 20))
            .collect(Collectors.toList());


    public static void main(String[] args) {

        System.out.println(getNamesByValidNumbers);
        System.out.println(mostPalandromeWordSimplified);
        /**
         * etc...
         */
    }

}


