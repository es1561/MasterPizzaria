package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemPedido
{
    private Pedido pedido;
    private Produto produto;
    private int quant;

    public ItemPedido()
    {
    }

    public ItemPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public ItemPedido(Produto produto)
    {
        this.produto = produto;
    }

    public ItemPedido(Pedido pedido, Produto produto)
    {
        this.pedido = pedido;
        this.produto = produto;
    }

    public ItemPedido(Pedido pedido, Produto produto, int quant)
    {
        this.pedido = pedido;
        this.produto = produto;
        this.quant = quant;
    }

    public Pedido getPedido()
    {
        return pedido;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
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
        String sql = "INSERT INTO ItemPedido(ped_cod, prod_cod, itemp_quant) ";
        String values = "VALUES(?, ?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, pedido.getCodigo());
        statement.setInt(2, produto.getCodigo());
        statement.setInt(3, quant);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE ItemPedido SET itemp_quant = ? WHERE ped_cod = ? AND prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, quant);
        statement.setInt(2, pedido.getCodigo());
        statement.setInt(3, produto.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM ItemPedido WHERE ped_cod = ? AND prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, pedido.getCodigo());
        statement.setInt(2, produto.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public ObservableList<Object> searchByPedido()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ItemPedido WHERE ped_cod = " + pedido.getCodigo();
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
            {
                Produto prod = (Produto)new Produto(rs.getInt("prod_cod")).searchByCodigo();
                ItemPedido obj = new ItemPedido(pedido, prod, rs.getInt("itemp_quant"));

                list.add(obj);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
}
