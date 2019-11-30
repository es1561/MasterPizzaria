/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controll.CtrCaixa;
import Controll.CtrCompra;
import Controll.CtrFornecedor;
import Controll.CtrItemCompra;
import Controll.CtrProduto;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mega_
 */
public class FXMLCompraController implements Initializable
{
    private Object selected_item;
    
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
    private TableColumn<Object, Date> c_data;
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
        cb_fornecedor.setDisable(flag);
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
        cb_fornecedor.setValue(null);
        cb_produto.setValue(null);

        tb_total.clear();
        tb_filtro.clear();
        
        list_item.getItems().clear();
    }
    
    private void loadFields()
    {
        CtrCompra.instancia().load(selected_item, cb_fornecedor, tb_total, list_item);
        CtrCompra.finaliza();
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
    
    private void unlockEditar()
    {
        disableButtons(true);
        disableFields(false);
        disableSearch(true);
        
        btn_editar.setDisable(false);
        btn_editar.setText("Salvar");
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
        btn_editar.setText("Editar");
    }
    
    private void refreshPedido()
    {
        double total = CtrItemCompra.instancia().getValorTotal(list_item.getItems());
        
        tb_total.setText(Double.toString(total));
        
        list_item.refresh();
        
        CtrItemCompra.finaliza();
        CtrCompra.finaliza();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Object caixa = CtrCaixa.instancia().searchByToday();
        
        if(caixa != null && CtrCaixa.instancia().isOpen(caixa))
        {
            c_cod.setCellValueFactory(new PropertyValueFactory<Object,Integer>("codigo"));
            c_fornecedor.setCellValueFactory(new PropertyValueFactory<Object,String>("fornecedor"));
            c_data.setCellValueFactory(new PropertyValueFactory<Object,Date>("data"));
            c_valor.setCellValueFactory(new PropertyValueFactory<Object,Double>("valor"));

            cb_fornecedor.setItems(CtrFornecedor.instancia().searchAll());
            CtrFornecedor.finaliza();

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
            Object forn = cb_fornecedor.getValue();
            double total = Double.valueOf(tb_total.getText());
            
            CtrCompra.instancia().insert(forn, list_item.getItems(), total);
            CtrCompra.finaliza();
            
            reset();
        }
        else
            unlockNovo();
    }

    @FXML
    private void ClickEditar(ActionEvent event)
    {
        if(btn_editar.getText().compareTo("Editar") != 0)
        {
            Object forn = cb_fornecedor.getValue();
            double total = Double.valueOf(tb_total.getText());
            
            CtrCompra.instancia().update(CtrCompra.instancia().getCodigo(selected_item), forn, list_item.getItems(), total);
            CtrCompra.finaliza();
            
            reset();
            selected_item = table_pedido.getSelectionModel().getSelectedItem();
        }
        else
            unlockEditar();
    }

    @FXML
    private void ClickApagar(ActionEvent event)
    {
        
    }

    @FXML
    private void ClickLimpa(ActionEvent event)
    {
        reset();
    }

    @FXML
    private void ChangeFornecedor(ActionEvent event)
    {
        
    }

    @FXML
    private void ClickRemove(ActionEvent event)
    {
        if(cb_produto.getValue() != null && list_item.getItems().size() > 0)
        {
            CtrItemCompra.instancia().remove(cb_produto.getValue(), list_item.getItems());
            CtrItemCompra.finaliza();
            refreshPedido();
        }
    }

    @FXML
    private void ClickAdd(ActionEvent event)
    {
        if(cb_produto.getValue() != null)
        {
            CtrItemCompra.instancia().add(cb_produto.getValue(), list_item.getItems());
            CtrItemCompra.finaliza();
            refreshPedido();
        }
    }

    @FXML
    private void ClickBuscar(ActionEvent event)
    {
        table_pedido.setItems(CtrCompra.instancia().searchAll());
        CtrCompra.finaliza();
    }

    @FXML
    private void ClickTable(MouseEvent event)
    {
        if(table_pedido.getSelectionModel().getSelectedIndex() >= 0)
        {
            selected_item = table_pedido.getSelectionModel().getSelectedItem();
            loadFields();
            disableButtons(true);
            btn_editar.setDisable(false);
            btn_apagar.setDisable(false);
        }
    }
    
}
