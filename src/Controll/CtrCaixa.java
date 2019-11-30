package Controll;

import DataBase.Banco;
import Model.Caixa;
import Utils.Result;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrCaixa 
{
    private static CtrCaixa instance;
    
    private CtrCaixa()
    {
        
    }
    
    public static CtrCaixa instancia()
    {
        if(instance == null)
            instance = new CtrCaixa();

        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
        
    public Result open(double init)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            boolean flag;
            Caixa caixa = (Caixa) new Caixa().searchByToday();
            
            if(caixa == null)
            {
                flag = false;
                caixa = new Caixa(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), init, 0, 0);
            }
            else
            {
                flag = true;
                caixa.setDataFechamento(null);
            }
            
            if(banco.isConnected())
            {
                boolean comit = flag ? caixa.update() : caixa.insert();
                
                banco.getConnection().setAutoCommit(false);
                if(comit)
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
            Banco.desconectar();
        }
        
        return result;
    }
    
    public Result close()
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = (Caixa)new Caixa().searchByToday();
            
            caixa.setDataFechamento(Date.valueOf(LocalDate.now()));
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(caixa.update())
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
            Banco.desconectar();
        }
        
        return result;
    }
    
    public String get(Object obj, String atribute)
    {
        String result = "";
        Caixa caixa = (Caixa)obj;
        
        if(atribute.compareTo("data") == 0)
            result = caixa.getData().toString();
        else if(atribute.compareTo("dataAbertura") == 0)
            result = caixa.getDataAbertura().toString();
        else if(atribute.compareTo("dataFechamento") == 0)
            result = caixa.getDataFechamento().toString();
        else if(atribute.compareTo("valorAbertura") == 0)
            result = Double.toString(caixa.getValorAbertura());
        else if(atribute.compareTo("entrada") == 0)
            result = Double.toString(caixa.getEntrada());
        else if(atribute.compareTo("saida") == 0)
            result = Double.toString(caixa.getSaida());
        else if(atribute.compareTo("balanca") == 0)
            result = Double.toString(caixa.getValorAbertura() + caixa.getEntrada() - caixa.getSaida());
        
        return result;
    }
    
    public Object searchByToday()
    {
        Object result = null;
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                result = new Caixa().searchByToday();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            Banco.desconectar();
        }
        
        return result;
    }
    
    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> result = FXCollections.observableArrayList();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                result = new Caixa().searchAll();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            Banco.desconectar();
        }
        
        return result;
    }
    
    public boolean isOpen(Object obj)
    {
        return ((Caixa)obj).getDataFechamento() == null;
    }
}
