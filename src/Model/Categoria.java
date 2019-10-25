package Model;

import DataBase.Banco;
import GoF.Observer;
import GoF.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Categoria implements Subject
{
    private int codigo;
    private String desc;
    private ObservableList<Observer> observers;
    
    public Categoria()
    {
        
    }

    public Categoria(int codigo)
    {
        this.codigo = codigo;
    }

    public Categoria(int codigo, String desc)
    {
        this.codigo = codigo;
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

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public ObservableList<Observer> getObservers()
    {
        return observers;
    }

    public void setObservers(ObservableList<Observer> observers)
    {
        this.observers = observers;
    }
    
    @Override
    public void add(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyAll(String msg)
    {
        for (Observer observer : observers)
        {
            observer.myNotify();
        }
    }
    
    public boolean insert() throws SQLException
    {
        String sql = "INSERT INTO Categoria(cat_cod, cat_desc) ";
        String values = "VALUES(?, ?)";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql + values);

        statement.setInt(1, Banco.getInstance().getMaxPK("Categoria", "cat_cod") + 1);
        statement.setString(2, desc);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean update() throws SQLException
    {
        String sql = "UPDATE Categoria SET cat_desc = ? WHERE cat_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, desc);
        statement.setInt(2, codigo);

        return statement.executeUpdate() > 0;
    }
    
    public boolean delete() throws SQLException
    {
        String sql = "DELETE FROM Categoria WHERE cat_cod = ?";
        
        Connection connection = Banco.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, codigo);

        return statement.executeUpdate() > 0;
    }
    
    public Object searchByCodigo()
    {
        Categoria obj = null;
        String sql = "SELECT * FROM Categoria WHERE cat_cod = " + codigo;
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                obj = new Categoria(rs.getInt("cat_cod"), rs.getString("cat_desc"));
                
                ObservableList<Object> list = new Interesse(obj).searchByCategoria();
                
                for (Object object : list) 
                {
                    Interesse inte = (Interesse)object;
                    obj.add(inte.getCliente());
                }
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
        String sql = "SELECT * FROM Categoria";
        
        try
        {
            Connection connection = Banco.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            
            while(rs.next())
            {
                Categoria obj = new Categoria(rs.getInt("cat_cod"), rs.getString("cat_desc"));
                
                ObservableList<Object> aux = new Interesse(obj).searchByCategoria();
                
                for (Object object : aux) 
                {
                    Interesse inte = (Interesse)object;
                    obj.add(inte.getCliente());
                }
                
                list.add(obj);
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
        return desc;
    }
}
