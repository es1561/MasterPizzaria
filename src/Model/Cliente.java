package Model;

import DataBase.Banco;
import GoF.Observer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente implements Observer
{
    private int codigo;
    private String nome;
    private String cpf;
    private String fone;

    public Cliente() 
    {
        
    }

    public Cliente(int codigo)
    {
        this.codigo = codigo;
    }

    public Cliente(String nome, String cpf, String fone)
    {
        this.nome = nome;
        this.cpf = cpf;
        this.fone = fone;
    }

    public Cliente(int codigo, String nome, String cpf, String fone)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.fone = fone;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }

    public String getFone()
    {
        return fone;
    }

    public void setFone(String fone)
    {
        this.fone = fone;
    }

    @Override
    public void myNotify()
    {
        
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Cliente(cli_cod, cli_nome, cli_cpf, cli_fone) ";
        String values = "VALUES(?, ?, ?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Cliente", "cli_cod") + 1);
        statement.setString(2, nome);
        statement.setString(3, cpf);
        statement.setString(4, fone);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Cliente SET cli_nome = ?, cli_cpf = ?, cli_fone = ? WHIERE cli_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, nome);
        statement.setString(2, cpf);
        statement.setString(3, fone);
        statement.setInt(4, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Cliente WHIERE cli_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public Object searchByCodigo()
    {
        Cliente obj = null;
        String sql = "SELECT * FROM Cliente WHERE cli_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
                obj = new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_cpf"), rs.getString("cli_fone"));
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
    
    public ObservableList<Cliente> searchAll()
    {
        ObservableList<Cliente> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Cliente";
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
                list.add(new Cliente(rs.getInt("cli_cod"), rs.getString("cli_nome"), rs.getString("cli_cpf"), rs.getString("cli_fone")));
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
        return nome + "(" + cpf + ")";
    }
}
