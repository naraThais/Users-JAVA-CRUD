package org.example;

import static spark.Spark.*;

public class UserApi {

    public static void main(String[] args) {
        port(8080);

        // Criar a tabela de usuários no banco (caso ainda não exista)
        Database.createTable();

        // Endpoint GET para retornar todos os usuários
        get("/users", (req, res) -> {
            return Database.getUsers();
        });

        // Endpoint GET para retornar um usuário específico por ID
        get("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            String users = Database.getUsers();
            return users;
        });

        // Endpoint POST para criar um novo usuário
        post("/users", (req, res) -> {
            String name = req.queryParams("name");
            String email = req.queryParams("email");

            User newUser = new User(0, name, email); // ID será gerado automaticamente no banco
            Database.insertUser(newUser);

            res.status(201); // Código de sucesso para criação
            return "{\"message\": \"User created successfully!\"}";
        });

        // Endpoint PUT para atualizar um usuário existente
        put("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            String name = req.queryParams("name");
            String email = req.queryParams("email");

            User updatedUser = new User(id, name, email);
            Database.updateUser(updatedUser);
            return "{\"message\": \"User updated successfully!\"}";
        });

        // Endpoint DELETE para excluir um usuário
        delete("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Database.deleteUser(id);
            return "{\"message\": \"User deleted successfully!\"}";
        });
    }
}
