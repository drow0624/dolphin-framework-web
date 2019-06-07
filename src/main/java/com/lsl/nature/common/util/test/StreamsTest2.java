package com.lsl.nature.common.util.test;

import java.util.Arrays;
import java.util.Optional;

public class StreamsTest2 {

    public static void main(String[] args) {
        Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));
        Arrays.asList("d", "f", "g").forEach(System.out::println);

        Optional< String > fullName = Optional.ofNullable( null );

        System.out.println( "Full Name is set? " + fullName.isPresent() );
        System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
        System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey "));


        Optional< String > firstName = Optional.of( "Tom" );
        System.out.println( "First Name is set? " + firstName.isPresent() );
        System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) );
        System.out.println( firstName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
    }
}
