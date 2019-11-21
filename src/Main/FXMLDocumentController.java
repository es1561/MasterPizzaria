/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Aluno
 */
public class FXMLDocumentController implements Initializable
{
    private final String main_color = "EA7514";
    private final String back_color = "B75808";
    private final String fore_color = "F49546";
    
    @FXML
    private Button btn_caixa;
    @FXML
    private Button btn_compra;
    @FXML
    private Button btn_venda;
    @FXML
    private Button btn_categoria;
    @FXML
    private Button btn_produto;
    @FXML
    private Button btn_fornecedor;
    @FXML
    private Button btn_cliente;
    @FXML
    private VBox vbox_node;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    private void resetButton()
    {
        btn_caixa.setStyle("-fx-background-color:" + main_color);
        btn_compra.setStyle("-fx-background-color:" + main_color);
        btn_venda.setStyle("-fx-background-color:" + main_color);
        btn_categoria.setStyle("-fx-background-color:" + main_color);
        btn_produto.setStyle("-fx-background-color:" + main_color);
        btn_fornecedor.setStyle("-fx-background-color:" + main_color);
        btn_cliente.setStyle("-fx-background-color:" + main_color);
    }
    
    private void loadNode(String fxml)
    {
        try
        {   
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/" + fxml));
            vbox_node.getChildren().clear();
            vbox_node.getChildren().add(newLoadedPane);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void ClickCaixa(ActionEvent event)
    {
        resetButton();
        btn_caixa.setStyle("-fx-background-color:" + fore_color);
        loadNode("FXMLCaixa.fxml");
    }

    @FXML
    private void ClickCompra(ActionEvent event)
    {
        resetButton();
        btn_compra.setStyle("-fx-background-color:" + fore_color);
        
    }

    @FXML
    private void ClickVenda(ActionEvent event)
    {
        resetButton();
        btn_venda.setStyle("-fx-background-color:" + fore_color);
        loadNode("FXMLVenda.fxml");
    }

    @FXML
    private void ClickCategoria(ActionEvent event)
    {
        resetButton();
        btn_categoria.setStyle("-fx-background-color:" + fore_color);
    }

    @FXML
    private void ClickProduto(ActionEvent event)
    {
        resetButton();
        btn_produto.setStyle("-fx-background-color:" + fore_color);
    }

    @FXML
    private void ClickFornecedor(ActionEvent event)
    {
        resetButton();
        btn_fornecedor.setStyle("-fx-background-color:" + fore_color);
    }

    @FXML
    private void ClickCliente(ActionEvent event)
    {
        resetButton();
        btn_cliente.setStyle("-fx-background-color:" + fore_color);
    }

}
