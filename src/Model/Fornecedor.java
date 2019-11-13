package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class Fornecedor
{
    private int codigo;
    private String nome;
    private String fone;

    public Fornecedor()
    {
    }

    public Fornecedor(int codigo)
    {
        this.codigo = codigo;
    }

    public Fornecedor(String nome)
    {
        this.nome = nome;
    }

    public Fornecedor(String nome, String fone)
    {
        this.nome = nome;
        this.fone = fone;
    }
    
    public Fornecedor(int codigo, String nome, String fone)
    {
        this.codigo = codigo;
        this.nome = nome;
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

    public String getFone()
    {
        return fone;
    }

    public void setFone(String fone)
    {
        this.fone = fone;
    }
    
    public Object searchByCodigo()
    {
        Fornecedor obj = null;
        String sql = "SELECT * FROM Fornecedor WHERE for_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                obj = new Fornecedor(rs.getInt("for_cod"), rs.getString("for_nome"), rs.getString("for_fone"));
               
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
}
