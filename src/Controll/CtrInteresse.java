package Controll;

import DataBase.Banco;
import Model.Categoria;
import Model.Cliente;
import Model.Interesse;
import Utils.Result;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrInteresse
{
    private static CtrInteresse instance;
    
    private CtrInteresse()
    {
        
    }
    
    public static CtrInteresse instancia()
    {
        if(instance == null)
            instance = new CtrInteresse();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(int cli_cod, int cat_cod)
    {
        Result result = new Result();
        Interesse interesse;
        
        try
        {
            Banco banco = Banco.conectar();
            
            if(banco.isConnected())
            {
                Cliente cli;
                Categoria cat;

                cli = (Cliente) CtrCliente.instancia().searchByCodigo(cli_cod);
                CtrCliente.finaliza();
                cat = (Categoria) CtrCategoria.instancia().searchByCodigo(cat_cod);
                CtrCategoria.finaliza();
                interesse = new Interesse(cat, cli);
                
                banco.getConnection().setAutoCommit(false);
                if(interesse.insert())
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
            System.out.println(ex.getMessage());
            
            result.setError(true);
            result.setMessage(ex.getMessage());
        }
        
        return result;
    }
    
    public ObservableList<Object> searchByCliente(int cli_cod)
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                list = CtrInteresse.instancia().searchByCliente(cli_cod);
                CtrInteresse.finaliza();
                
                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
}
