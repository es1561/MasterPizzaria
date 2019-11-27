package Controll;

import DataBase.Banco;
import Model.Compra;
import Model.Fornecedor;
import Model.ItemCompra;
import Utils.Result;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrCompra
{
    private static CtrCompra instance = null;
    
    private CtrCompra()
    {
        
    }
    
    public static CtrCompra instancia()
    {
        if(instance == null)
            instance = new CtrCompra();
        
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(Object fornecedor, ObservableList<Object> item, double total)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                Compra compra;
                
                CtrCliente.finaliza();
                compra = new Compra((Fornecedor)fornecedor);
                
                banco.getConnection().setAutoCommit(false);
                if(compra.insert())
                {
                    boolean comit = true;
                    ItemCompra itemp;
                    
                    for(int i = 0; comit && i < item.size(); i++)
                    {
                        itemp = (ItemCompra)item.get(i);
                        itemp.setCompra(compra);
                        comit = itemp.insert();
                    }

                    if(comit)
                    {
                        if(!CtrPagamento.instancia().insert(total, -1, compra.getCodigo()).isError())                        
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
    
    public Result delete(int comp_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(new Compra(comp_cod).delete())
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
        }
        
        Banco.desconectar();
        return result;
    }
    
        
    public Object searchByCodigo(int ped_cod)
    { 
        Banco banco = Banco.conectar();
        Object result = null;
        
        try
        {
            if(banco.isConnected())
                result = new Compra(ped_cod).searchByCodigo();
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        Banco.desconectar();
        
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
                list.addAll(new Compra().searchAll());
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
