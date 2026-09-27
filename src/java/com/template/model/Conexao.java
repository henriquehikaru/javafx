package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    static String conexao = "jdbc:postgresql://localhost:5432/db_produto";
    static String usuario = "postgres";
    static String senha = "postgres";

    // Tenta estabelecer uma conexão com o BD Local
    public Connection conectaDB() {
        try {
            return DriverManager.getConnection(conexao, usuario, senha);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
