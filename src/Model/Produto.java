package Model;

import DataBase.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto
{
    private int codigo;
    private String nome;
    private double valor;
    private double peso;
    private Categoria categoria;

    public Produto()
    {
    }

    public Produto(int codigo)
    {
        this.codigo = codigo;
    }

    public Produto(String nome, double valor, double peso, Categoria categoria)
    {
        this.nome = nome;
        this.valor = valor;
        this.peso = peso;
        this.categoria = categoria;
    }

    public Produto(int codigo, String nome, double valor, double peso, Categoria categoria)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.peso = peso;
        this.categoria = categoria;
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

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public double getPeso()
    {
        return peso;
    }

    public void setPeso(double peso)
    {
        this.peso = peso;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Produto(prod_cod, prod_nome, prod_valor, prod_peso, cat_cod) ";
        String values = "VALUES(?, ?, ?, ?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Produto", "prod_cod") + 1);
        statement.setString(2, nome);
        statement.setDouble(3, valor);
        statement.setDouble(4, peso);
        statement.setInt(5, categoria.getCodigo());
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Produto SET prod_nome = ?, prod_valor = ?, prod_peso = ?, cat_cod = ? WHERE prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, nome);
        statement.setDouble(2, valor);
        statement.setDouble(3, peso);
        statement.setInt(4, categoria.getCodigo());
        statement.setInt(5, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Produto WHERE prod_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, codigo);
        
        return statement.executeUpdate() > 0;
    }
    
    public Object searchByCodigo()
    {
        Produto obj = null;
        String sql = "SELECT * FROM Produto WHERE prod_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                Categoria cat = (Categoria)new Categoria(rs.getInt("cat_cod")).searchByCodigo();
                obj = new Produto(rs.getInt("prod_cod"), rs.getString("prod_nome"), rs.getDouble("prod_valor"), rs.getDouble("prod_peso"), cat);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return obj;
    }
    
    public ObservableList<Object> searchAll()
    {
        ObservableList<Object> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Produto";
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
            {
                Categoria cat = (Categoria)new Categoria(rs.getInt("cat_cod")).searchByCodigo();
                list.add(new Produto(rs.getInt("prod_cod"), rs.getString("prod_nome"), rs.getDouble("prod_valor"), rs.getDouble("prod_peso"), cat));
            }
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
        return nome + " (" + valor + ")";
    }
}
