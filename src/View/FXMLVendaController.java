/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controll.CtrCliente;
import Controll.CtrPedido;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLVendaController implements Initializable
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
    private ComboBox<Object> cb_cliente;
    @FXML
    private ComboBox<Object> cb_produto;
    @FXML
    private TextField tb_peso;
    @FXML
    private TextField tb_entrega;
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
    private TableColumn<Object, String> c_cliente;
    @FXML
    private TableColumn<Object, Double> c_peso;
    @FXML
    private TableColumn<Object, Double> c_entrega;
    @FXML
    private TableColumn<Object, Double> c_valor;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        c_cod.setCellValueFactory(new PropertyValueFactory<Object,Integer>("codigo"));
        
        cb_cliente.setItems(CtrCliente.instancia().searchAll());
        CtrCliente.finaliza();
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
    private void ChangeCliente(ActionEvent event)
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
