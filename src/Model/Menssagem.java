package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Menssagem
{
    private int codigo;
    private Cliente cliente;
    private Categoria categoria;
    private String desc;

    public Menssagem()
    {
    }

    public Menssagem(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Menssagem(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public Menssagem(Cliente cliente, Categoria categoria, String desc)
    {
        this.cliente = cliente;
        this.categoria = categoria;
        this.desc = desc;
    }

    public Menssagem(int codigo, Cliente cliente, Categoria categoria, String desc)
    {
        this.codigo = codigo;
        this.cliente = cliente;
        this.categoria = categoria;
        this.desc = desc;
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

    public Categoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Menssagem(men_cod, cli_cod, cat_cod, men_desc) ";
        String values = "VALUES(?, ?, ?, ?)";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Menssagem", "men_cod") + 1);
        statement.setInt(2, cliente.getCodigo());
        statement.setInt(2, categoria.getCodigo());
        statement.setString(2, desc);
        

        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Menssagem men_cod = ?";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, codigo);

        return statement.executeUpdate() > 0;
    }
    
    public ObservableList<Object> searchByCliente()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Menssagem WHERE cli_cod = " + cliente.getCodigo();

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                Cliente cli = (Cliente) new Cliente(cliente.getCodigo()).searchByCodigo();
                Categoria cat = (Categoria) new Categoria(rs.getInt("cat_cod")).searchByCodigo();

                list.add(new Menssagem(rs.getInt("men_cod"), cli, cat, rs.getString("men_desc")));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public ObservableList<Object> searchByCategoria()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Menssagem WHERE cat_cod = " + categoria.getCodigo();

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                Cliente cli = (Cliente) new Cliente(rs.getInt("cli_cod")).searchByCodigo();
                Categoria cat = (Categoria) new Categoria(categoria.getCodigo()).searchByCodigo();

                list.add(new Menssagem(rs.getInt("men_cod"), cli, cat, rs.getString("men_desc")));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}
