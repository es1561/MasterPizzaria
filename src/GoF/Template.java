package GoF;

import DataBase.Banco;
import Model.Caixa;
import Model.Compra;
import Model.Movimento;
import Model.Pedido;
import Utils.Result;
import java.sql.Date;
import java.sql.SQLException;

public abstract class Template
{
    public final Result insert(double valor, int ped_cod, int com_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = (Caixa) new Caixa().searchByToday();
            Pedido ped = new Pedido(ped_cod);
            Compra comp = new Compra(com_cod);
            Movimento mov = new Movimento(valor, caixa, ped, comp);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(execute(mov))
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public final Result update(int mov_cod, Date cixa_data, int tipo, double valor, int ped_cod, int com_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = null;
            Pedido ped = null;
            Compra comp = null;
            Movimento mov = new Movimento(com_cod, tipo, valor, caixa, ped, comp);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(execute(mov))
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line...");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public Result delete(int mov_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Movimento movimento = new Movimento(mov_cod);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(movimento.delete())
                    banco.getConnection().commit();
                else
                    banco.getConnection().rollback();

                Banco.desconectar();
            }
            else
                throw new SQLException("Banco Off-Line");
        }
        catch(SQLException ex)
        {
            result.setError(true);
            result.setMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    protected abstract boolean execute(Movimento mov);
}
