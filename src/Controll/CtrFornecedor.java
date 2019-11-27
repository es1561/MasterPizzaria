/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import DataBase.Banco;
import Model.Fornecedor;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aluno
 */
public class CtrFornecedor
{
    private static CtrFornecedor instance = null;
    
    private CtrFornecedor()
    {
        
    }
    
    public static CtrFornecedor instancia()
    {
        if(instance == null)
            instance = new CtrFornecedor();
            
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
        
    
    public Object searchByCodigo(int codigo)
    {
        Banco banco = Banco.conectar();
        Fornecedor forn = null;
        
        try
        {
            if(banco.isConnected())
            {
                forn = (Fornecedor)new Fornecedor(codigo).searchByCodigo();
                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return forn;
    }
    
    public ObservableList<Object> searchAll()
    {
        Banco banco = Banco.conectar();
        ObservableList<Object> list = FXCollections.observableArrayList();
        
        try
        {
            if(banco.isConnected())
            {
                list = castAll(new Fornecedor().searchAll());
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
    
    private ObservableList<Object> castAll(ObservableList<Fornecedor> list)
    {
        ObservableList<Object> result = FXCollections.observableArrayList();
        
        for (Fornecedor cliente : list)
            result.add(cliente);
        
        return result;
    }
}
