/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import DataBase.Banco;
import Model.Compra;
import Model.ItemCompra;
import Model.Produto;
import Utils.Result;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aluno
 */
public class CtrItemCompra
{
    private static CtrItemCompra instance = null;
    
    private CtrItemCompra()
    {
        
    }
    
    public static CtrItemCompra instancia()
    {
        if(instance == null)
            instance = new CtrItemCompra();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(int comp_cod, int prod_cod, int quant)
    {
        Result result = new Result();
        ItemCompra item;
        
        try
        {
            Banco banco = Banco.conectar();
            
            if(banco.isConnected())
            {
                Compra compra;
                Produto produto;

                compra = (Compra)CtrPedido.instancia().searchByCodigo(comp_cod);
                CtrPedido.finaliza();
                produto = (Produto)CtrProduto.instancia().searchByCodigo(prod_cod);
                CtrProduto.finaliza();
                item = new ItemCompra(compra, produto, quant);
                
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
    
    public Result delete(int comp_cod, int prod_cod)
    {
        Result result = new Result();
        ItemCompra item;
        
        try
        {
            Banco banco = Banco.conectar();
            
            if(banco.isConnected())
            {
                Compra compra;
                Produto produto;

                compra = (Compra)CtrPedido.instancia().searchByCodigo(comp_cod);
                CtrPedido.finaliza();
                produto = (Produto)CtrProduto.instancia().searchByCodigo(prod_cod);
                CtrProduto.finaliza();
                item = new ItemCompra(compra, produto);

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
    
    public ObservableList<Object> searchByCompra(int comp_cod)
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                Compra compra;
                
                compra = (Compra)CtrPedido.instancia().searchByCodigo(comp_cod);
                CtrPedido.finaliza();
                ItemCompra item = new ItemCompra(compra);
                
                list.addAll(item.searchByCompra());
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
    
    public void add(Object obj, ObservableList<Object> list)
    {
        boolean insert = true;
        
        for(int i = 0; insert && i < list.size(); i++)
        {
            Produto prod = (Produto)obj;
            ItemCompra item = (ItemCompra)list.get(i);
            
            if(item.getProduto().getCodigo() == prod.getCodigo())
            {
                item.setQuant(item.getQuant() + 1);
                insert = false;
            }
        }
        
        if(insert)
            list.add(new ItemCompra((Produto)obj, 1));
    }
    
    public void remove(Object obj, ObservableList<Object> list)
    {
        boolean flag = true;
        
        for(int i = 0; flag && i < list.size(); i++)
        {
            Produto prod = (Produto)obj;
            ItemCompra item = (ItemCompra)list.get(i);
            
            if(item.getProduto().getCodigo() == prod.getCodigo() && item.getQuant() > 1)
            {
                item.setQuant(item.getQuant() - 1);
                flag = false;
            }
            else
            {
                list.remove(i);
                flag = false;
            }
        }
    }
    
    public double getValorTotal(ObservableList<Object> list)
    {
        double value = 0;
        
        for(Object object: list)
        {
            value += ((ItemCompra)object).getQuant() * ((ItemCompra)object).getProduto().getValor();
        }
        
        return value;
    }

}
