package Controll;

import GoF.Template;
import Model.Movimento;
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
