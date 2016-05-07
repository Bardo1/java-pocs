package com.edwise.pocs.java8predicatemethods;

import com.edwise.pocs.java8predicatemethods.model.BookCharacter;
import com.edwise.pocs.java8predicatemethods.model.BookCharacter.Weapon;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.edwise.pocs.java8predicatemethods.BookCharacterPredicate.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PredicateMethodsTest {

    private List<BookCharacter> bookCharacters;

    @Before
    public void setUp() {
        bookCharacters = Arrays.asList(
                new BookCharacter("Gandalf", Integer.MAX_VALUE, Weapon.STAFF, false),
                new BookCharacter("Aragorn", 88, Weapon.SWORD, true),
                new BookCharacter("Gimli", 140, Weapon.AXE, false),
                new BookCharacter("Legolas", 2931, Weapon.BOW, false),
                new BookCharacter("Boromir", 41, Weapon.SWORD, true),
                new BookCharacter("Frodo", 51, Weapon.RING, false),
                new BookCharacter("Sam", 33, Weapon.SWORD, false)
        );
    }

    @Test
    public void testPredicateInlineYoungAndUseSword() {
        List<BookCharacter> youngsAndSwords =
                bookCharacters.stream()
                              .filter(bChar -> bChar.getAge() < 90 && Weapon.SWORD.equals(bChar.getMainWeapon()))
                              .collect(Collectors.toList());

        youngsAndSwords.forEach(System.out::println);
    }

    @Test
    public void testPredicateInlineRefactorYoungAndUseSword() {
        List<BookCharacter> youngsAndSwords =
                bookCharacters.stream()
                              .filter(bChar -> bChar.getAge() < 90)
                              .filter(bChar -> Weapon.SWORD.equals(bChar.getMainWeapon()))
                              .collect(Collectors.toList());

        youngsAndSwords.forEach(System.out::println);
    }

    @Test
    public void testPredicateDefinedInMethodsYoungAndUseSword() {
        List<BookCharacter> youngsAndSwords =
                bookCharacters.stream()
                              .filter(isYoung().and(useSword()))
                              .collect(Collectors.toList());

        youngsAndSwords.forEach(System.out::println);
    }

    @Test
    public void testPredicateDefinedInMethodsIsHumanOrUseSword() {
        List<BookCharacter> humanOrSwords =
                bookCharacters.stream()
                              .filter(isHuman().or(useSword()))
                              .collect(Collectors.toList());

        humanOrSwords.forEach(System.out::println);
    }

    @Test
    public void testPredicateDefinedInMethodsNotUseSword() {
        List<BookCharacter> notUseSword =
                bookCharacters.stream()
                              .filter(useSword().negate())
                              .collect(Collectors.toList());

        notUseSword.forEach(System.out::println);
    }

    @Test
    public void testPredicateAsParameterYoungAndUseSword() {
        BookCharacterChecker bookCharacterChecker = new BookCharacterChecker();

        boolean result = bookCharacterChecker.checkThisAndValid(bookCharacters.get(0), bChar -> bChar.getAge() > 90);

        assertThat(result).isTrue();
    }


    @Test
    public void testPredicateEqualMethod() {
        BookCharacter aragorn = new BookCharacter("Aragorn", 88, Weapon.SWORD, true);
        Predicate<BookCharacter> equalToAragorn = Predicate.isEqual(aragorn);

        List<BookCharacter> result = bookCharacters.stream()
                                                   .filter(equalToAragorn.negate())
                                                   .collect(Collectors.toList());

        assertThat(result).doesNotContain(aragorn).hasSize(6);
    }
}