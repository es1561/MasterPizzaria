/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aluno
 */
public class ItemCompra
{
    private Compra compra;
    private Produto produto;
    private int quant;

    public ItemCompra()
    {
    }

    public ItemCompra(Produto produto)
    {
        this.produto = produto;
    }

    public ItemCompra(Compra compra)
    {
        this.compra = compra;
    }

    public ItemCompra(Produto produto, int quant)
    {
        this.produto = produto;
        this.quant = quant;
    }

    public ItemCompra(Compra compra, Produto produto)
    {
        this.compra = compra;
        this.produto = produto;
        this.quant = 1;
    }

    public ItemCompra(Compra compra, Produto produto, int quant)
    {
        this.compra = compra;
        this.produto = produto;
        this.quant = quant;
    }

    public Compra getCompra()
    {
        return compra;
    }

    public void setCompra(Compra compra)
    {
        this.compra = compra;
    }

    public Produto getProduto()
    {
        return produto;
    }

    public void setProduto(Produto produto)
    {
        this.produto = produto;
    }

    public int getQuant()
    {
        return quant;
    }

    public void setQuant(int quant)
    {
        this.quant = quant;
    }

    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO ItemCompra(comp_cod, prod_cod, itemc_quant) ";
        String values = "VALUES(?, ?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, compra.getCodigo());
        statement.setInt(2, produto.getCodigo());
        statement.setInt(3, quant);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE ItemCompra SET itemc_quant = ? WHERE comp_cod = ? AND prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, quant);
        statement.setInt(2, compra.getCodigo());
        statement.setInt(3, produto.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM ItemPedido WHERE comp_cod = ? AND prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, compra.getCodigo());
        statement.setInt(2, produto.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public ObservableList<Object> searchByCompra()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ItemCompra WHERE comp_cod = " + compra.getCodigo();
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
            {
                Produto prod = (Produto)new Produto(rs.getInt("prod_cod")).searchByCodigo();
                ItemCompra obj = new ItemCompra(compra, prod, rs.getInt("itemc_quant"));

                list.add(obj);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    @Override
    public String toString()
    {
        return quant + "x " +  produto.getNome();
    }
}
