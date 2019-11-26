/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mega_
 */
public class FXMLCompraController implements Initializable
{
    @FXML
    private Button btn_novo;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_apagar;
    @FXML
    private Button btn_limpa;
    @FXML
    private ListView<Object> list_item;
    @FXML
    private ComboBox<Object> cb_fornecedor;
    @FXML
    private ComboBox<Object> cb_produto;
    @FXML
    private TextField tb_total;
    @FXML
    private Button btn_rem;
    @FXML
    private Button btn_add;
    @FXML
    private TextField tb_filtro;
    @FXML
    private Button btn_busca;
    @FXML
    private TableView<Object> table_pedido;
    @FXML
    private TableColumn<Object, Integer> c_cod;
    @FXML
    private TableColumn<Object, String> c_fornecedor;
    @FXML
    private TableColumn<Object, String> c_data;
    @FXML
    private TableColumn<Object, Double> c_valor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void ClickNovo(ActionEvent event)
    {
    }

    @FXML
    private void ClickEditar(ActionEvent event)
    {
    }

    @FXML
    private void ClickApagar(ActionEvent event)
    {
    }

    @FXML
    private void ClickLimpa(ActionEvent event)
    {
    }

    @FXML
    private void ChangeFornecedor(ActionEvent event)
    {
    }

    @FXML
    private void ClickRemove(ActionEvent event)
    {
    }

    @FXML
    private void ClickAdd(ActionEvent event)
    {
    }

    @FXML
    private void ClickBuscar(ActionEvent event)
    {
    }
    
}
