package Controll;

import Model.Categoria;
import Model.Cliente;
import Model.Menssagem;
import Utils.Result;
import DataBase.Banco;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CtrMenssagem 
{
    private static CtrMenssagem instance = null;
    
    private CtrMenssagem()
    {
        
    }
    
    public static CtrMenssagem instancia()
    {
        if(instance == null)
            instance = new CtrMenssagem();

        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(int cli_cod, int cat_cod, String desc)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Cliente cliente = (Cliente)CtrCliente.instancia().searchByCodigo(cli_cod);
            CtrCliente.finaliza();
            Categoria categoria = (Categoria)CtrCategoria.instancia().searchByCodigo(cat_cod);
            CtrCategoria.finaliza();
            Menssagem msg = new Menssagem(cliente, categoria, desc);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(msg.insert())
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
    
    public ObservableList<Object> searchByCliente(int cli_cod)
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            Cliente cliente = (Cliente)CtrCliente.instancia().searchByCodigo(cli_cod);
            CtrCliente.finaliza();
            
            if(banco.isConnected())
            {
                list.addAll(new Menssagem(cliente).searchByCliente());
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
