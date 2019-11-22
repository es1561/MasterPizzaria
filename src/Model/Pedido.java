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

public class Pedido
{
    private int codigo;
    private Cliente cliente;
    private double peso;
    private double entrega;
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

    public Pedido(Cliente cliente, double peso, double entrega)
    {
        this.cliente = cliente;
        this.peso = peso;
        this.entrega = entrega;
    }
    
    public Pedido(Cliente cliente, double peso, double entrega, ObservableList<ItemPedido> itens)
    {
        this.cliente = cliente;
        this.peso = peso;
        this.entrega = entrega;
        this.itens = itens;
    }

    public Pedido(int codigo, Cliente cliente, double peso, double entrega, ObservableList<ItemPedido> itens)
    {
        this.codigo = codigo;
        this.cliente = cliente;
        this.peso = peso;
        this.entrega = entrega;
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

    public double getPeso()
    {
        return peso;
    }

    public void setPeso(double peso)
    {
        this.peso = peso;
        this.entrega = new StrategyONE().execute(peso);
    }

    public double getEntrega()
    {
        return entrega;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Pedido(ped_cod, ped_data, cli_cod, ped_peso, ped_entrega) ";
        String values = "VALUES(?, ?, ?, ?, ?)";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Pedido", "ped_cod") + 1);
        statement.setDate(2, Date.valueOf(LocalDate.now()));
        statement.setInt(3, cliente.getCodigo());
        statement.setDouble(4, peso);
        statement.setDouble(5, entrega);

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

                obj = new Pedido(cli, rs.getDouble("ped_peso"), rs.getDouble("ped_entrega"), item);
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return obj;
    }

    public ObservableList<Object> searchByCliente()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Pedido WHERE cli_cod = " + cliente.getCodigo();

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                Cliente cli = (Cliente)new Cliente(cliente.getCodigo()).searchByCodigo();
                ObservableList<Object> aux = new ItemPedido(new Pedido(rs.getInt("ped_cod"))).searchByPedido();
                ObservableList<ItemPedido> item = castAll(aux);

                list.add(new Pedido(rs.getInt("ped_cod"), cli, rs.getDouble("ped_peso"), rs.getDouble("ped_entrega"), item));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Pedido";

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                Cliente cli = (Cliente)new Cliente(rs.getInt("cli_cod")).searchByCodigo();
                ObservableList<Object> aux = new ItemPedido(new Pedido(rs.getInt("ped_cod"))).searchByPedido();
                ObservableList<ItemPedido> item = castAll(aux);

                list.add(new Pedido(rs.getInt("ped_cod"), cli, rs.getDouble("ped_peso"), rs.getDouble("ped_entrega"), item));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    private ObservableList<ItemPedido> castAll(ObservableList<Object> list)
    {
        ObservableList<ItemPedido> result = FXCollections.observableArrayList();

        for(Object object: list)
            result.add((ItemPedido)object);

        return result;
    }
}
