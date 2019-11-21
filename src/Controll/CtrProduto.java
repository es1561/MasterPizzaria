package Controll;

import Model.Categoria;
import Model.Produto;
import Utils.Result;
import DataBase.Banco;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrProduto 
{
    private static CtrProduto instance = null;
    
    private CtrProduto()
    {
        
    }
    
    public static CtrProduto instancia()
    {
        if(instance == null)
            instance = new CtrProduto();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(String nome, int cat_cod, double preco, double peso)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                Produto produto = new Produto(nome, preco, peso, (Categoria)new Categoria(cat_cod).searchByCodigo());
                
                banco.getConnection().setAutoCommit(false);
                if(produto.insert())
                {
                    banco.getConnection().commit();
                    
                    produto.getCategoria().notifyAll(produto.getNome());
                }
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
    
    public Result update(int codigo, String nome, int cat_cod, double preco, double peso)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                Produto produto = new Produto(codigo, nome, preco, peso, (Categoria)new Categoria(cat_cod).searchByCodigo());
                
                banco.getConnection().setAutoCommit(false);
                if(produto.update())
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
    
    public Result delete(int codigo)
    {
        Produto produto = new Produto(codigo);
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(produto.delete())
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
    
    public Produto searchByCodigo(int codigo)
    {
        Banco banco = Banco.conectar();
        Produto produto = null;
        
        try
        {
            if(banco.isConnected())
            {
                produto = CtrProduto.instancia().searchByCodigo(codigo);
                CtrProduto.finaliza();
                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return produto;
    }
    
    public ObservableList<Object> searchAll()
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                list.addAll(new Produto().searchAll());
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
