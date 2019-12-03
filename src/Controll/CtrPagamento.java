package Controll;

import DataBase.Banco;
import GoF.Template;
import Model.Compra;
import Model.Movimento;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CtrPagamento extends Template
{
    private static CtrPagamento instance;
    
    private CtrPagamento()
    {
    }
            
    public static CtrPagamento instancia()
    {
        if(instance == null)
            instance = new CtrPagamento();
            
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Object searchByCompra(int comp_cod)
    {
        Banco banco = Banco.conectar();
        Movimento mov = null;
        
        try
        {
            if(banco.isConnected())
            {
                mov = (Movimento)new Movimento(new Compra(comp_cod)).searchByCompra();
                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return mov;
    }
    
    @Override
    protected boolean execute(Movimento mov)
    {
        boolean flag;
        
        try
        {
            mov.setTipo(2);
            flag = mov.getCaixa().checkBalance(mov.getValor()) ? mov.insert() : false;
            
            if(!flag)
                new Alert(Alert.AlertType.ERROR, "Saldo insuficiente no caixa !", ButtonType.OK).showAndWait();
        }
        catch(Exception ex)
        {
            flag = false;
            System.out.println(ex.getMessage());
        }
        
        return flag;
    }
}
