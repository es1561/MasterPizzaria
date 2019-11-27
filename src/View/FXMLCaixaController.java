/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controll.CtrCaixa;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLCaixaController implements Initializable
{
    @FXML
    private Button btn_abrir;
    @FXML
    private Button btn_atualiza;
    @FXML
    private Button btn_fechar1;
    @FXML
    private Label lb_data;
    @FXML
    private Label lb_status;
    @FXML
    private Label lb_valor;
    @FXML
    private Label lb_entrada;
    @FXML
    private Label lb_saida;
    @FXML
    private Label lb_balanca;
    @FXML
    private TableView<Object> table_caixa;
    @FXML
    private TableColumn<Object, String> c_data;
    @FXML
    private TableColumn<Object, String> c_abertura;
    @FXML
    private TableColumn<Object, String> c_fechamento;
    @FXML
    private TableColumn<Object, Double> c_valor;
    @FXML
    private TableColumn<Object, Double> c_entrada;
    @FXML
    private TableColumn<Object, Double> c_saida;

    /**
     * Initializes the controller class.
     */
     
    private void disableFields(boolean flag)
    {
        lb_valor.setVisible(!flag);
        lb_entrada.setVisible(!flag);
        lb_saida.setVisible(!flag);
        lb_balanca.setVisible(!flag);
        lb_data.setVisible(!flag);
    }
    
    private void loadFields(Object caixa)
    {
        lb_valor.setText("Abertura: $ " + CtrCaixa.instancia().get(caixa, "valorAbertura"));
        lb_entrada.setText("Entrada: $ +" + CtrCaixa.instancia().get(caixa, "entrada"));
        lb_saida.setText("Saida: $ -" + CtrCaixa.instancia().get(caixa, "saida"));
        lb_balanca.setText("Balança: $ " + CtrCaixa.instancia().get(caixa, "balanca"));
        lb_data.setText("DATA" + CtrCaixa.instancia().get(caixa, "data"));
        
        CtrCaixa.finaliza();
    }
    
    private void caixaOpen(Object caixa)
    {
        disableFields(false);
        
        btn_abrir.setDisable(true);
        btn_atualiza.setDisable(false);
        btn_fechar1.setDisable(false);
        
        lb_status.setText("Aberto");
        
        loadFields(caixa);
    }
    
    private void caixaClosed()
    {
        disableFields(true);
        
        btn_abrir.setDisable(false);
        btn_atualiza.setDisable(true);
        btn_fechar1.setDisable(true);
        
        lb_status.setText("Fechado");
        lb_data.setText(Date.valueOf(LocalDate.now()).toString());
    }
    
    private void refresh()
    {
        Object caixa = CtrCaixa.instancia().searchByToday();
        
        if(caixa != null && CtrCaixa.instancia().isOpen(caixa))
            caixaOpen(caixa);
        else
            caixaClosed();
        
        CtrCaixa.finaliza();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        refresh();
    }    

    @FXML
    private void ClickAbrir(ActionEvent event)
    {
        CtrCaixa.instancia().open(200);
        CtrCaixa.finaliza();
        refresh();
    }

    @FXML
    private void ClickAtualizar(ActionEvent event)
    {
        refresh();
    }

    @FXML
    private void ClickFechar(ActionEvent event)
    {
        CtrCaixa.instancia().close();
        CtrCaixa.finaliza();
        refresh();
    }
}
