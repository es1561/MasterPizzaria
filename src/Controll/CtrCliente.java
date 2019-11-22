package Controll;

import Model.Cliente;
import Model.Interesse;
import Utils.Result;
import DataBase.Banco;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrCliente 
{
    private static CtrCliente instance = null;
    
    private CtrCliente()
    {
        
    }
    
    public static CtrCliente instancia()
    {
        if(instance == null)
            instance = new CtrCliente();
            
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(String nome, String cpf, String fone)
    {
        Cliente cliente = new Cliente(nome, cpf, fone);
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cliente.insert())
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
    
    public Result update(int codigo, String nome, String cpf, String fone)
    {
        Cliente cliente = new Cliente(codigo, nome, cpf, fone);
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cliente.update())
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public Result delete(int codigo)
    {
        Cliente cliente = new Cliente(codigo);
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(cliente.delete())
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public Object searchByCodigo(int codigo)
    {
        Banco banco = Banco.conectar();
        Cliente cliente = null;
        
        try
        {
            if(banco.isConnected())
            {
                cliente = (Cliente)new Cliente(codigo).searchByCodigo();
                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return cliente;
    }
    
    public ObservableList<Object> searchAll()
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                list = castAll(new Cliente().searchAll());
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
    
    public ObservableList<Object> searchInteresse(int cli_cod)
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                ObservableList<Object> ctr_list = CtrInteresse.instancia().searchByCliente(cli_cod);
                CtrInteresse.finaliza();
                
                for(Object obj: ctr_list)
                {
                    Interesse interesse = (Interesse)obj;
                    list.add(interesse.getCategoria());
                }
                
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
    
    private ObservableList<Object> castAll(ObservableList<Cliente> list)
    {
        ObservableList<Object> result = FXCollections.observableArrayList();
        
        for (Cliente cliente : list)
            result.add(cliente);
        
        return result;
    }
}
