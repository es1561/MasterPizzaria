/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controll.CtrCaixa;
import Controll.CtrCliente;
import Controll.CtrItemPedido;
import Controll.CtrPedido;
import Controll.CtrProduto;
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

    private void disableButtons(boolean flag)
    {
        btn_novo.setDisable(flag);
        btn_editar.setDisable(flag);
        btn_apagar.setDisable(flag);
    }
    
    private void disableFields(boolean flag)
    {
        cb_cliente.setDisable(flag);
        cb_produto.setDisable(flag);
        btn_add.setDisable(flag);
        btn_rem.setDisable(flag);
        list_item.setDisable(flag);
    }
    
    private void disableSearch(boolean flag)
    {
        btn_busca.setDisable(flag);
        tb_filtro.setDisable(flag);
        table_pedido.setDisable(flag);
    }
    
    private void clearFields()
    {
        cb_cliente.setValue(null);
        cb_produto.setValue(null);
        
        tb_entrega.clear();
        tb_peso.clear();
        tb_total.clear();
        tb_filtro.clear();
        
        list_item.getItems().clear();
    }
    
    private void unlockNovo()
    {
        disableButtons(true);
        disableFields(false);
        disableSearch(true);
        
        clearFields();
        
        btn_novo.setDisable(false);
        btn_novo.setText("Salvar");
    }
    
    private void reset()
    {
        disableButtons(false);
        disableFields(true);
        disableSearch(false);

        clearFields();
        
        btn_editar.setDisable(true);
        btn_apagar.setDisable(true);
        
        btn_novo.setText("Novo");
    }
    
    private void refreshPedido()
    {
        double peso = CtrItemPedido.instancia().getPesoTotal(list_item.getItems());
        double total = CtrItemPedido.instancia().getValorTotal(list_item.getItems());
        double entrega = CtrPedido.instancia().getEntrega(peso);
        
        tb_peso.setText(Double.toString(peso));
        tb_total.setText(Double.toString(total));
        tb_entrega.setText(Double.toString(entrega));
        
        list_item.refresh();
        
        CtrItemPedido.finaliza();
        CtrPedido.finaliza();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Object caixa = CtrCaixa.instancia().searchByToday();
        
        if(caixa != null && CtrCaixa.instancia().isOpen(caixa))
        {
            c_cod.setCellValueFactory(new PropertyValueFactory<Object,Integer>("codigo"));
            c_cliente.setCellValueFactory(new PropertyValueFactory<Object,String>("cliente"));
            c_peso.setCellValueFactory(new PropertyValueFactory<Object,Double>("peso"));
            c_entrega.setCellValueFactory(new PropertyValueFactory<Object,Double>("entrega"));
            c_valor.setCellValueFactory(new PropertyValueFactory<Object,Double>("valor"));

            cb_cliente.setItems(CtrCliente.instancia().searchAll());
            CtrCliente.finaliza();

            cb_produto.setItems(CtrProduto.instancia().searchAll());
            CtrProduto.finaliza();
            CtrCaixa.finaliza();
            
            reset();
        }
        else
        {
            disableButtons(true);
            disableFields(true);
            disableSearch(true);
            
            btn_limpa.setDisable(true);
        }
        
    }    

    @FXML
    private void ClickNovo(ActionEvent event)
    {
        if(btn_novo.getText().compareTo("Novo") != 0)
        {
            Object cli = cb_cliente.getValue();
            double peso = Double.valueOf(tb_peso.getText());
            double entrega = Double.valueOf(tb_entrega.getText());
            double total = Double.valueOf(tb_total.getText()) + entrega;
            
            CtrPedido.instancia().insert(cli, peso, entrega, total, list_item.getItems());
            CtrPedido.finaliza();
            
            reset();
        }
        else
            unlockNovo();
    }

    @FXML
    private void ClickEditar(ActionEvent event)
    {
        
    }

    @FXML
    private void ClickApagar(ActionEvent event)
    {
        reset();
    }

    @FXML
    private void ClickLimpa(ActionEvent event)
    {
        reset();
    }

    @FXML
    private void ChangeCliente(ActionEvent event)
    {
        
    }

    @FXML
    private void ClickRemove(ActionEvent event)
    {
        if(cb_produto.getValue() != null && list_item.getItems().size() > 0)
        {
            CtrItemPedido.instancia().remove(cb_produto.getValue(), list_item.getItems());
            CtrItemPedido.finaliza();
            refreshPedido();
        }
    }

    @FXML
    private void ClickAdd(ActionEvent event)
    {
        if(cb_produto.getValue() != null)
        {
            CtrItemPedido.instancia().add(cb_produto.getValue(), list_item.getItems());
            CtrItemPedido.finaliza();
            refreshPedido();
        }
    }

    @FXML
    private void ClickBuscar(ActionEvent event)
    {
        table_pedido.setItems(CtrPedido.instancia().searchAll());
        CtrPedido.finaliza();
    }
    
}
