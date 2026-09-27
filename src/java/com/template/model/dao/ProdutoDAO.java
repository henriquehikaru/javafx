package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.ProdutoDTO;
import static com.template.util.DialogUtil.showError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO {

    private static final Logger logger =
            Logger.getLogger(ProdutoDAO.class.getName());

    // Cadastrar produto
    public void cadastrarProduto(ProdutoDTO produto) {
        String sql = "INSERT INTO produto (descricao, categoria, preco) VALUES (?, ?, ?)";

        try (Connection c = new Conexao().conectaDB();
             PreparedStatement ps = c.prepareStatement(sql)) {



            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCategoria());
            ps.setDouble(3, produto.getPreco());

            ps.executeUpdate();

            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar produto", e);
            showError("Erro ao cadastrar produto");
        }
    }

    // Listar produtos
    public ArrayList<ProdutoDTO> selecionarProduto() {

        ArrayList<ProdutoDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM produto";

        try (Connection c = new Conexao().conectaDB();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ProdutoDTO produto = new ProdutoDTO();

                produto.setId_produto(rs.getInt("id_produto"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPreco(rs.getDouble("preco"));

                lista.add(produto);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao selecionar produtos", e);
            showError("Erro ao selecionar produtos");
        }

        return lista;
    }

    // Alterar produto
    public void alterarProduto(ProdutoDTO produto) {
        String sql = "UPDATE produto SET descricao = ?, categoria = ?, preco = ? WHERE id_produto = ?";

        try (Connection c = new Conexao().conectaDB();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCategoria());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getId_produto());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar produto", e);
            showError("Erro ao alterar produto");
        }
    }

    // Excluir produto
    public void excluirProduto(ProdutoDTO produto) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection c = new Conexao().conectaDB();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, produto.getId_produto());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir produto", e);
            showError("Erro ao excluir produto");
        }
    }
}