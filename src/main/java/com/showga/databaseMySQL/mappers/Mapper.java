package com.showga.databaseMySQL.mappers;

// encapsulate all the login for mapping for our application
// implement this interface with Beans
public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);
}
