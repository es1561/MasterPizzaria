package Controll;

import DataBase.Banco;
import Model.Compra;
import Model.Fornecedor;
import Model.ItemCompra;
import Model.Movimento;
import Utils.Result;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    
    public Result update(int comp_cod, Object fornecedor, ObservableList<Object> item, double total)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            if(banco.isConnected())
            {
                Compra compra = new Compra(comp_cod, (Fornecedor)fornecedor, total);
                
                banco.getConnection().setAutoCommit(false);
                if(compra.update())
                {
                    boolean comit = new ItemCompra(compra).deleteAllByCompra();
                    ItemCompra itemp;
                    
                    for(int i = 0; comit && i < item.size(); i++)
                    {
                        itemp = (ItemCompra)item.get(i);
                        itemp.setCompra(compra);
                        comit = itemp.insert();
                    }

                    if(comit)
                    {
                        Movimento mov = (Movimento) CtrPagamento.instancia().searchByCompra(comp_cod);
                        
                        if(!CtrPagamento.instancia().update(mov.getCodigo(), mov.getCaixa().getData(), 2, total, -1, compra.getCodigo()).isError())                        
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
                Compra compra = (Compra) new Compra(comp_cod).searchByCodigo();
                
                banco.getConnection().setAutoCommit(false);
                if(compra.delete())
                {
                    boolean comit = true;
                    ItemCompra itemp;
                    
                    for(int i = 0; comit && i < compra.getItens().size(); i++)
                    {
                        itemp = compra.getItens().get(i);
                        itemp.setCompra(compra);
                        comit = itemp.delete();
                    }
                    
                    if(comit)
                        banco.getConnection().commit();
                    else
                        banco.getConnection().rollback();
                }
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
    
    public void load(Object selected_item, ComboBox<Object> fornecedor, TextField total, ListView<Object> list)
    {
        Compra compra = (Compra) selected_item;
        
        fornecedor.setValue(compra.getFornecedor());
        total.setText(String.valueOf(compra.getValor()));
        list.setItems(castAll(compra.getItens()));
    }
    
    public int getCodigo(Object compra)
    {
        return ((Compra)compra).getCodigo();
    }
            
    private ObservableList<Object> castAll(ObservableList<ItemCompra> list)
    {
        ObservableList<Object> result = FXCollections.observableArrayList();
        
        for (ItemCompra cliente : list)
            result.add(cliente);
        
        return result;
    }
}
