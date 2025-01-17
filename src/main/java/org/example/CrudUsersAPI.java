package org.example;

import static spark.Spark.*;

public class CrudUsersAPI {
    public static void main(String[] args) {
        port(4567);  // Define a porta em que a API vai rodar

        get("/hello", (req, res) -> "Hello World!");  // Defina uma rota de exemplo

        // Aqui você pode adicionar o código para manipulação de banco de dados e outras rotas
    }
}
