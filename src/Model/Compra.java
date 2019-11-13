package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class Compra
{
    private int codigo;
    private Fornecedor fornecedor;

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

    public Compra(int codigo, Fornecedor fornecedor)
    {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
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
    
    public Object searchByCodigo()
    {
        Compra obj = null;
        String sql = "SELECT * FROM Compra WHERE comp_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                Fornecedor forn = (Fornecedor) new Fornecedor(rs.getInt("for_cod")).searchByCodigo();
                
                obj = new Compra(codigo, forn);
                
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
}
