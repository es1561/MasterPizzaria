package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Interesse
{
    private Categoria categoria;
    private Cliente cliente;

    public Interesse()
    {
        
    }

    public Interesse(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public Interesse(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Interesse(Categoria categoria, Cliente cliente)
    {
        this.categoria = categoria;
        this.cliente = cliente;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Interesse(cat_cod, cli_cod) ";
        String values = "VALUES(?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, categoria.getCodigo());
        statement.setInt(2, cliente.getCodigo());
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Interesse WHERE cat_cod = ?, cli_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, categoria.getCodigo());
        statement.setInt(2, cliente.getCodigo());
        
        return statement.executeUpdate() > 0;
    }
    
    public ObservableList<Object> searchByCliente()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Interesse WHERE cli_cod = " + cliente.getCodigo();
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
                list.add(new Interesse((Categoria)new Categoria(rs.getInt("cat_cod")).searchByCodigo(), cliente));
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public ObservableList<Object> searchByCategoria()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Interesse WHERE cat_cod = " + categoria.getCodigo();
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
                list.add(new Interesse(categoria, (Cliente)new Cliente(rs.getInt("cli_cod")).searchByCodigo()));
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
}
