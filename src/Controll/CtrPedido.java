package Controll;

import Model.Cliente;
import Model.Pedido;
import Utils.Result;
import DataBase.Banco;
import Model.ItemPedido;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrPedido 
{
    private static CtrPedido instance = null;
    
    private CtrPedido()
    {
        
    }
    
    public static CtrPedido instancia()
    {
        if(instance == null)
            instance = new CtrPedido();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(Object cliente, double peso, double entrega, double total, ObservableList<Object> item)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                Pedido pedido;
                
                CtrCliente.finaliza();
                pedido = new Pedido((Cliente) cliente, peso, entrega);
                
                banco.getConnection().setAutoCommit(false);
                if(pedido.insert())
                {
                    boolean comit = true;
                    ItemPedido itemp;
                    
                    for(int i = 0; comit && i < item.size(); i++)
                    {
                        itemp = (ItemPedido)item.get(i);
                        itemp.setPedido(pedido);
                        comit = itemp.insert();
                    }

                    if(comit)
                    {
                        if(!CtrRecebimento.instancia().insert(total, pedido.getCodigo(), -1).isError())                        
                            banco.getConnection().commit();
                        else
                            banco.getConnection().rollback();
                    }
                    else
                        banco.getConnection().rollback();
                }
                else
                    banco.getConnection().rollback();

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
        
        Banco.desconectar();
        
        return result;
    }
    
    public Result delete(int ped_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(new Pedido(ped_cod).delete())
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
                Cliente cliente;
                Pedido pedido;
                
                cliente = (Cliente)CtrCliente.instancia().searchByCodigo(cli_cod);
                CtrCliente.finaliza();
                pedido = new Pedido(cliente);
                
                list.addAll(pedido.searchByCliente());
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
    
    public Object searchByCodigo(int ped_cod)
    { 
        Banco banco = Banco.conectar();
        Object result = null;
        
        try
        {
            if(banco.isConnected())
            {
                result = new Pedido(ped_cod).searchByCodigo();

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
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                list.addAll(new Pedido().searchAll());
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
    
    public double getEntrega(double peso)
    {
        return new Pedido(peso).getEntrega();
    }
}
