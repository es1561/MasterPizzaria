package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import javafx.animation.KeyValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Movimento
{
    private int codigo;
    private int tipo;
    private double valor;
    private Caixa caixa;
    private Pedido pedido;
    private Compra compra;

    public Movimento()
    {
    }

    public Movimento(int codigo)
    {
        this.codigo = codigo;
    }

    public Movimento(Caixa caixa)
    {
        this.caixa = caixa;
    }

    public Movimento(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Movimento(Compra compra)
    {
        this.compra = compra;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Pedido pedido)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Compra compra)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.compra = compra;
    }

    public Movimento(double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }
    
    public Movimento(int codigo, int tipo, double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.codigo = codigo;
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public int getTipo()
    {
        return tipo;
    }

    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public Caixa getCaixa()
    {
        return caixa;
    }

    public void setCaixa(Caixa caixa)
    {
        this.caixa = caixa;
    }

    public Pedido getPedido()
    {
        return pedido;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Compra getCompra()
    {
        return compra;
    }

    public void setCompra(Compra compra)
    {
        this.compra = compra;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Movimento(mov_cod, caixa_data, mov_tipo, mov_data, mov_valor, ped_cod, comp_cod) ";
        String values = "VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Movimento", "mov_cod") + 1);
        statement.setDate(2, caixa.getData());
        statement.setInt(3, tipo);
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setDouble(5, valor);
        
        if(pedido.getCodigo() >= 0)
            statement.setInt(6, pedido.getCodigo());
        else
            statement.setNull(6, Types.NULL);

        if(compra.getCodigo() >= 0)
            statement.setInt(7, compra.getCodigo());
        else
            statement.setNull(7, Types.NULL);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Movimento SET caixa_data = ?, mov_tipo = ?, mov_data = ?, mov_valor = ?, ped_cod = ?, comp_cod = ? WHERE mov_cod = " + codigo;

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, caixa.getData());
        statement.setInt(2, tipo);
        statement.setDate(3, Date.valueOf(LocalDate.now()));
        statement.setDouble(4, valor);
        
        if(pedido != null)
            statement.setInt(5, pedido.getCodigo());
        else
            statement.setNull(5, Types.INTEGER);

        if(compra != null)
            statement.setInt(6, compra.getCodigo());
        else
            statement.setNull(6, Types.INTEGER);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Movimento WHERE mov_cod = " + codigo;

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        return statement.executeUpdate() > 0;
    }
    
    public ObservableList<Object> searchByDate()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Movimento WHERE caixa_data = ?";
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, caixa.getData());
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
            {
                int tipo = rs.getInt("mov_tipo");
                
                Pedido ped = null;
                Compra comp = null;
                
                if(tipo == 1)
                    ped = (Pedido) new Pedido(rs.getInt("ped_cod")).searchByCodigo();
                else
                    comp = (Compra) new Compra(rs.getInt("comp_cod")).searchByCodigo();
                
                Movimento obj = new Movimento(rs.getInt("mov_cod"), rs.getInt("mov_tipo"), rs.getDouble("mov_valor"), caixa, ped, comp);
                
                list.add(obj);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public ObservableList<Movimento> cast(ObservableList<Object> list)
    {
        ObservableList<Movimento> result = FXCollections.observableArrayList();
        
        for(Object object: list)
        {
            result.add((Movimento) object);
        }
        
        return result;
    }
}
