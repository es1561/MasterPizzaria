package Model;

import DataBase.Banco;
import GoF.Template;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        return entrada;
    }

    public void setEntrada(double entrada)
    {
        this.entrada = entrada;
    }

    public double getSaida()
    {
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
        String sql = "INSERT INTO Caixa(caixa_data, caixa_data_a, caixa_valor_a, caixa_entrada, caixa_saida) ";
        String values = "VALUES(?, ?, ?, ?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setDate(1, data);
        statement.setDate(2, dataAbertura);
        statement.setDouble(3, valorAbertura);
        statement.setDouble(4, entrada);
        statement.setDouble(5, saida);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Caixa caixa_valor_a = ? , caixa_entrada = ?, caixa_saida = ? WHERE caixa_data = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDouble(1, valorAbertura);
        statement.setDouble(2, entrada);
        statement.setDouble(3, saida);
        statement.setDate(4, data);
        
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
                obj = new Caixa(rs.getDate("caixa_data_a"), rs.getDate("caixa_data_a"), rs.getDate("caixa_data_f"), rs.getDouble("caixa_valor_a"), rs.getDouble("caixa_entrada"), rs.getDouble("caixa_saida"));
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
}
