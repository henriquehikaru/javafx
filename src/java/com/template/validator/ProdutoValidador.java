package com.template.validator;

import static com.template.util.DialogUtil.showError;
import java.util.regex.Pattern;


public class ProdutoValidador {

    public boolean validarProduto(String descricao, String categoria, Double preco) {
        if (descricao.isEmpty() && categoria.isEmpty() && preco == null) {
            showError("Preencha todos os campos!");
            return false;
        }
        return true;
    }

    public boolean validarDescricao(String descricao) {
        return Pattern.matches("^[A-ZÀ-ÖØ-ÝÇ]+(?:\\s[A-ZÀ-ÖØ-ÝÇ]+)*$", descricao);
    }

    public boolean validarCategoria(String categoria) {
        return Pattern.matches("^[A-ZÀ-ÖØ-ÝÇ]+(?:\\s[A-ZÀ-ÖØ-ÝÇ]+)*$", categoria);
    }
}
