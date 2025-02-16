package Model;

import DataBase.Banco;
import GoF.Template;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Caixa
{
    private Date data;
    private Date dataAbertura;
    private Date dataFechamento;
    private double valorAbertura;
    private double entrada;
    private double saida;
    ObservableList<Movimento> movimentos;

    public Caixa()
    {
    }

    public Caixa(Date data)
    {
        this.data = data;
    }
    
    public Caixa(Date data, Date dataAbertura, double valorAbertura, double entrada, double saida)
    {
        this.data = data;
        this.dataAbertura = dataAbertura;
        this.valorAbertura = valorAbertura;
        this.entrada = entrada;
        this.saida = saida;
    }

    public Caixa(Date data, Date dataAbertura, Date dataFechamento, double valorAbertura, double entrada, double saida)
    {
        this.data = data;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.valorAbertura = valorAbertura;
        this.entrada = entrada;
        this.saida = saida;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public Date getDataAbertura()
    {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura)
    {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento()
    {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento)
    {
        this.dataFechamento = dataFechamento;
    }

    public double getValorAbertura()
    {
        return valorAbertura;
    }

    public void setValorAbertura(double valorAbertura)
    {
        this.valorAbertura = valorAbertura;
    }

    public double getEntrada()
    {
        if(movimentos != null)
        {
            entrada = 0;

            for(Movimento movimento: movimentos)
                if(movimento.getTipo() == 1)
                    entrada += movimento.getValor();
        }

        return entrada;
    }

    public void setEntrada(double entrada)
    {
        this.entrada = entrada;
    }

    public double getSaida()
    {
        if(movimentos != null)
        {
            saida = 0;
            for(Movimento movimento: movimentos)
                if(movimento.getTipo() == 2)
                    saida += movimento.getValor();
        }

        return saida;
    }

    public void setSaida(double saida)
    {
        this.saida = saida;
    }

    public boolean isOpen()
    {
        return dataFechamento != null;
    }

    public ObservableList<Movimento> getMovimentos()
    {
        return movimentos;
    }

    public void setMovimentos(ObservableList<Movimento> movimentos)
    {
        this.movimentos = movimentos;
    }

    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Caixa(caixa_data, caixa_data_a, caixa_data_f, caixa_valor_a, caixa_entrada, caixa_saida) ";
        String values = "VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setDate(1, data);
        statement.setDate(2, dataAbertura);
        if(dataFechamento != null)
            statement.setDate(3, dataFechamento);
        else
            statement.setNull(3, Types.NULL);
        statement.setDouble(4, valorAbertura);
        statement.setDouble(5, entrada);
        statement.setDouble(6, saida);

        return statement.executeUpdate() > 0;
    }

    public boolean update() throws SQLException
    {
        String sql = "UPDATE Caixa SET caixa_data_f = ?, caixa_valor_a = ? , caixa_entrada = ?, caixa_saida = ? WHERE caixa_data = ?";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        if(dataFechamento != null)
            statement.setDate(1, dataFechamento);
        else
            statement.setNull(1, Types.NULL);
        statement.setDouble(2, valorAbertura);
        statement.setDouble(3, entrada);
        statement.setDouble(4, saida);
        statement.setDate(5, data);

        return statement.executeUpdate() > 0;
    }

    public boolean close() throws SQLException
    {
        String sql = "UPDATE Caixa caixa_data_f = ? WHERE caixa_data = ?";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, Date.valueOf(LocalDate.now()));
        statement.setDate(2, data);

        return statement.executeUpdate() > 0;
    }

    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Caixa WHERE caixa_data = ?";

        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, data);

        return statement.executeUpdate() > 0;
    }

    public boolean checkBalance(double value) throws SQLException
    {
        double balance = valorAbertura + (entrada - saida);

        return balance - value >= 0;
    }

    public Object searchByToday()
    {
        Caixa obj = null;
        String sql = "SELECT * FROM Caixa WHERE caixa_data = ?";

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, Date.valueOf(LocalDate.now()));

            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                obj = new Caixa(rs.getDate("caixa_data_a"), rs.getDate("caixa_data_a"), rs.getDate("caixa_data_f"), rs.getDouble("caixa_valor_a"), rs.getDouble("caixa_entrada"), rs.getDouble("caixa_saida"));

                obj.setMovimentos(new Movimento().cast(new Movimento(obj).searchByDate()));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return obj;
    }

    public Object searchByData()
    {
        Caixa obj = null;
        String sql = "SELECT * FROM Caixa WHERE caixa_data = ?";

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, data);

            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                obj = new Caixa(rs.getDate("caixa_data_a"), rs.getDate("caixa_data_a"), rs.getDate("caixa_data_f"), rs.getDouble("caixa_valor_a"), rs.getDouble("caixa_entrada"), rs.getDouble("caixa_saida"));

                obj.setMovimentos(new Movimento().cast(new Movimento(obj).searchByDate()));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return obj;
    }
    
    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> obj = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Caixa ORDER BY caixa_data DESC";

        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
                obj.add(new Caixa(rs.getDate("caixa_data"), rs.getDate("caixa_data_a"), rs.getDate("caixa_data_f"), rs.getDouble("caixa_valor_a"), rs.getDouble("caixa_entrada"), rs.getDouble("caixa_saida")));
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return obj;
    }

}
