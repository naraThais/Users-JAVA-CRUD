package org.example;

import static spark.Spark.*;

public class CrudUsersAPI {
    public static void main(String[] args) {
        port(4567);
        get("/hello", (req, res) -> "Hello World!");
    }
}
