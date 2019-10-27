package Controll;

import DataBase.Banco;
import Model.Categoria;
import Utils.Result;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class CtrCategoria
{
    private static CtrCategoria instance;
    
    private CtrCategoria()
    {
        
    }
    
    public static CtrCategoria instancia()
    {
        if(instance == null)
            instance = new CtrCategoria();

        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(String desc)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Categoria cat = new Categoria(desc);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cat.insert())
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
        }
        
        return result;
    }
    
    public Result update(int cat_cod, String desc)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Categoria cat = new Categoria(cat_cod, desc);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cat.update())
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
        }
        
        return result;
    }
    
    public Result delete(int cat_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Categoria cat = new Categoria(cat_cod);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cat.delete())
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
        }
        
        return result;
    }
    
    public Object searchByCodigo(int cat_cod)
    {
        Object result = null;
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                result = new Categoria(cat_cod).searchByCodigo();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> result = null;
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                result = new Categoria().searchAll();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
}
