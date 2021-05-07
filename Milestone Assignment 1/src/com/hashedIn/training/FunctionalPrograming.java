package com.hashedIn.training;

import java.util.ArrayList;
import java.util.List;

public class FunctionalPrograming
{
    public static void main(String args[])
    {
        List<Integer> numbers = List.of(12,65,2,46,2,1,33,22,1);
        List<String> courses = List.of("Spring","Spring Boot","API","Microservices","AWS","Azure","Docker","Kubernetes");

        //printNumbersInListFunctional(numbers);

        //printEvenNumbersInListFunctional(numbers);

        //printOddNumbersInListFunctional(numbers);

        //printSpringCourses(courses);

        //printMoreThan4LetterCourses(courses);

        //printSquaresOfNumbers(numbers);

        //printCubesOfOddNumbers(numbers);

        printNumberOfCharsInEachCourse(courses);
    }

    private static void printNumberOfCharsInEachCourse(List<String> courses)
    {
        courses.stream()
                .map(course -> course+" "+course.length())
                .forEach(System.out::println);
    }

    private static void printCubesOfOddNumbers(List<Integer> numbers)
    {
        numbers.stream()
                .filter(number -> number%2 != 0)
                .map(number -> number*number*number)
                .forEach(System.out::println);
    }

    private static void printSquaresOfNumbers(List<Integer> numbers)
    {
        numbers.stream()
                .map(number -> number * number)
                .forEach(System.out::println);
    }

    private static void printMoreThan4LetterCourses(List<String> courses)
    {
        courses.stream()
                .filter(course -> course.length()>=4)
                .forEach(System.out::println);
    }

    private static void printSpringCourses(List<String> courses)
    {
        courses.stream()
                .filter(course -> course.contains("Spring"))
                .forEach(System.out::println);
    }

    private static void printOddNumbersInListFunctional(List<Integer> numbers)
    {
        numbers.stream()
                .filter(number -> number%2!=0)
                .forEach(System.out::println);
    }

    private static boolean isEven(int number)
    {
        return number%2 == 0;
    }

    private static void printEvenNumbersInListFunctional(List<Integer> integers)
    {
        integers.stream()
                //.filter(FunctionalPrograming::isEven) //Using methods
                .filter(number -> number%2==0) //Using lambda expressions
                .forEach(System.out::println);
    }

    private static void print(int number)
    {
        System.out.println(number);
    }

    private static void printNumbersInListFunctional(List<Integer> integers)
    {
        //integers.stream().forEach(number -> System.out.println(number));//Lambda Expression
        //integers.stream().forEach(FunctionalPrograming::print);//Method Reference
        integers.stream().forEach(System.out::println);//System.out is a class and println is a static method
    }
}
