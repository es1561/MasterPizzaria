package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pedido
{
    private int codigo;
    private Cliente cliente;
    private ObservableList<ItemPedido> itens;
    
    public Pedido()
    {
    }

    public Pedido(int codigo)
    {
        this.codigo = codigo;
    }

    public Pedido(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Pedido(int codigo, Cliente cliente)
    {
        this.codigo = codigo;
        this.cliente = cliente;
    }

    public Pedido(int codigo, Cliente cliente, ObservableList<ItemPedido> itens)
    {
        this.codigo = codigo;
        this.cliente = cliente;
        this.itens = itens;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public ObservableList<ItemPedido> getItens()
    {
        return itens;
    }

    public void setItens(ObservableList<ItemPedido> itens)
    {
        this.itens = itens;
    }
    
    private ObservableList<ItemPedido> castAll(ObservableList<Object> list)
    {
        ObservableList<ItemPedido> result = FXCollections.observableArrayList();
        
        for (Object object : list)
        {
            result.add((ItemPedido)object);
        }
        
        return result;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Pedido(ped_cod, cli_cod) ";
        String values = "VALUES(?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Pedido", "ped_cod") + 1);
        statement.setInt(2, cliente.getCodigo());
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Pedido SET cli_cod = ? WHERE ped_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, cliente.getCodigo());
        statement.setInt(2, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Pedido WHERE ped_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public Object searchByCodigo()
    {
        Pedido obj = null;
        String sql = "SELECT * FROM Pedido WHERE ped_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                Cliente cli = (Cliente)new Cliente(rs.getInt("cli_cod")).searchByCodigo();
                ObservableList<Object> aux = new ItemPedido(new Pedido(codigo)).searchByPedido();
                ObservableList<ItemPedido> item = castAll(aux);

                obj = new Pedido(codigo, cli, item);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
    
    public Object searchByCodigo()
    {
        Pedido obj = null;
        String sql = "SELECT * FROM Pedido WHERE ped_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                Cliente cli = (Cliente)new Cliente(rs.getInt("cli_cod")).searchByCodigo();
                ObservableList<Object> aux = new ItemPedido(new Pedido(codigo)).searchByPedido();
                ObservableList<ItemPedido> item = castAll(aux);

                obj = new Pedido(codigo, cli, item);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
}
