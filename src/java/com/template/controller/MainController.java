package com.template.controller;

import com.template.model.dao.ProdutoDAO;
import com.template.model.dto.ProdutoDTO;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;

public class MainController {

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtPreco;

    @FXML
    private TableView<ProdutoDTO> tblProdutos;

    @FXML
    private TableColumn<ProdutoDTO, Integer> colId;

    @FXML
    private TableColumn<ProdutoDTO, String> colDescricao;

    @FXML
    private TableColumn<ProdutoDTO, String> colCategoria;

    @FXML
    private TableColumn<ProdutoDTO, Double> colPreco;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        carregarProdutos();
        carregarCampos();
    }

    @FXML
    private void carregarProdutos() {
        ProdutoDAO objProdutoDAO = new ProdutoDAO();

        ArrayList<ProdutoDTO> listaProdutos =
                objProdutoDAO.selecionarProduto();

        tblProdutos.setItems(
                FXCollections.observableArrayList(listaProdutos)
        );
    }

    @FXML
    private void carregarCampos() {
        ProdutoDTO objProdutoDTO = tblProdutos.getSelectionModel().getSelectedItem();

        if (objProdutoDTO != null) {
            txtDescricao.setText(objProdutoDTO.getDescricao());
            txtCategoria.setText(objProdutoDTO.getCategoria());
            txtPreco.setText(Double.toString(objProdutoDTO.getPreco()));
        }
    }

    @FXML
    private boolean validarCampos() {
        if (txtDescricao.getText().isEmpty()) {
            DialogUtil.mostrarAlerta(Alert.AlertType.WARNING, "Validação", "Informe a descrição.");
            txtDescricao.requestFocus();
            return false;
        }

        if (txtCategoria.getText().isEmpty()) {
            DialogUtil.mostrarAlerta(Alert.AlertType.WARNING, "Validação", "Informe a categoria.");
            txtCategoria.requestFocus();
            return false;
        }

        if (txtPreco.getText().isEmpty()) {
            DialogUtil.mostrarAlerta(Alert.AlertType.WARNING, "Validação", "Informe o preço.");
            txtPreco.requestFocus();
            return false;
        }

        return true;
    }

    @FXML
    private boolean validarPreco() {
        try {
            Double.parseDouble(txtPreco.getText());
            return true;
        } catch (NumberFormatException e) {
            DialogUtil.mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Preço inválido. Use apenas números.");
            txtPreco.requestFocus();
            return false;
        }
    }

    @FXML
    private void limpaCampos() {
            // txtDescricao.setText(null);
            // txtCategoria.setText(null);
            // txtPreco.setText(null);

            txtDescricao.clear();
            txtCategoria.clear();
            txtPreco.clear();

            tblProdutos.getSelectionModel().clearSelection();
    }

    @FXML
    private void salvarProduto() {

        if (!validarCampos()) return;
        if (!validarPreco()) return;

        ProdutoDTO produtoSelecionado = tblProdutos.getSelectionModel().getSelectedItem();

        ProdutoDAO dao = new ProdutoDAO();

        if (produtoSelecionado != null) {

            produtoSelecionado.setDescricao(txtDescricao.getText());
            produtoSelecionado.setCategoria(txtCategoria.getText());
            produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText()));

            dao.alterarProduto(produtoSelecionado);

            DialogUtil.mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto atualizado com sucesso!");

        } else {

            ProdutoDTO novoProduto = new ProdutoDTO();
            novoProduto.setDescricao(txtDescricao.getText());
            novoProduto.setCategoria(txtCategoria.getText());
            novoProduto.setPreco(Double.parseDouble(txtPreco.getText()));

            dao.cadastrarProduto(novoProduto);

            DialogUtil.mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso!");
        }

        limpaCampos();
        carregarProdutos();
    }

    @FXML
    private void excluirProduto() {

        ProdutoDTO produtoSelecionado = tblProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            DialogUtil.mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um produto para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja realmente excluir este produto?");

        if (confirmacao.showAndWait().get() == javafx.scene.control.ButtonType.OK) {

            ProdutoDAO dao = new ProdutoDAO();
            dao.excluirProduto(produtoSelecionado);

            DialogUtil.mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto excluído com sucesso!");

            limpaCampos();
            carregarProdutos();
        }
    }
}