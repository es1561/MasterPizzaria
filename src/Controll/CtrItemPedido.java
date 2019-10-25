package Controll;

import Model.Cliente;
import Model.ItemPedido;
import Model.Pedido;
import Model.Produto;
import Utils.Result;
import DataBase.Banco;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrItemPedido
{
    private static CtrItemPedido instance = null;
    
    private CtrItemPedido()
    {
        
    }
    
    public static CtrItemPedido instancia()
    {
        if(instance == null)
            instance = new CtrItemPedido();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(int ped_cod, int prod_cod, int quant)
    {
        Result result = new Result();
        ItemPedido item;
        
        try
        {
            Banco banco = Banco.conectar();
            
            if(banco.isConnected())
            {
                Pedido pedido;
                Produto produto;

                pedido = (Pedido)CtrPedido.instancia().searchByCodigo(ped_cod);
                CtrPedido.finaliza();
                produto = (Produto)CtrProduto.instancia().searchByCodigo(prod_cod);
                CtrProduto.finaliza();
                item = new ItemPedido(pedido, produto, quant);
                
                banco.getConnection().setAutoCommit(false);
                if(item.insert())
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
    
    public Result delete(int ped_cod, int prod_cod)
    {
        Result result = new Result();
        ItemPedido item;
        
        try
        {
            Banco banco = Banco.conectar();
            
            if(banco.isConnected())
            {
                Pedido pedido;
                Produto produto;

                pedido = (Pedido)CtrPedido.instancia().searchByCodigo(ped_cod);
                CtrPedido.finaliza();
                produto = (Produto)CtrProduto.instancia().searchByCodigo(prod_cod);
                CtrProduto.finaliza();
                item = new ItemPedido(pedido, produto);

                banco.getConnection().setAutoCommit(false);
                if(item.delete())
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
    
    public ObservableList<Object> searchByPedido(int ped_cod)
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                Pedido pedido;
                
                pedido = (Pedido)CtrPedido.instancia().searchByCodigo(ped_cod);
                CtrPedido.finaliza();
                ItemPedido item = new ItemPedido(pedido);
                
                list.addAll(item.searchByPedido());
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
