package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Compra
{

    private int codigo;
    private Fornecedor fornecedor;
    private Date data;
    private ObservableList<ItemCompra> itens;

    public Compra()
    {
    }

    public Compra(int codigo)
    {
        this.codigo = codigo;
    }

    public Compra(Fornecedor fornecedor)
    {
        this.fornecedor = fornecedor;
    }

    public Compra(int codigo, Fornecedor fornecedor, Date data)
    {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.data = data;
    }

    public Compra(int codigo, Fornecedor fornecedor, Date data, ObservableList<ItemCompra> itens)
    {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.data = data;
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

    public Fornecedor getFornecedor()
    {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor)
    {
        this.fornecedor = fornecedor;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Compra(comp_cod, comp_data, for_cod) ";
        String values = "VALUES(?, ?, ?)";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        codigo = Banco.getInstance().getMaxPK("Compra", "comp_cod") + 1;
        statement.setInt(1, codigo);
        statement.setDate(2, Date.valueOf(LocalDate.now()));
        statement.setInt(3, fornecedor.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "INSERT INTO Compra SET for_cod = ? WHERE comp_cod = " + codigo;

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, fornecedor.getCodigo());

        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Compra WHERE comp_cod = " + codigo;

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        return statement.executeUpdate() > 0;
    }
    
    public Object searchByCodigo()
    {
        Compra obj = null;
        String sql = "SELECT * FROM Compra WHERE comp_cod = " + codigo;

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next())
            {
                Fornecedor forn = (Fornecedor) new Fornecedor(rs.getInt("for_cod")).searchByCodigo();

                obj = new Compra(codigo, forn, rs.getDate("comp_data"));
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return obj;
    }
    
    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Compra";

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                Fornecedor forn = (Fornecedor)new Fornecedor(rs.getInt("for_cod")).searchByCodigo();
                ObservableList<Object> aux = new ItemCompra(new Compra(rs.getInt("comp_cod"))).searchByCompra();
                ObservableList<ItemCompra> item = castAll(aux);

                list.add(new Compra(rs.getInt("comp_cod"), forn, rs.getDate("comp_data"), item));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    private ObservableList<ItemCompra> castAll(ObservableList<Object> list)
    {
        ObservableList<ItemCompra> result = FXCollections.observableArrayList();

        for(Object object: list)
            result.add((ItemCompra)object);

        return result;
    }
}
