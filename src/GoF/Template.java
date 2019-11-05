package GoF;

import DataBase.Banco;
import Model.Caixa;
import Model.Compra;
import Model.Movimento;
import Model.Pedido;
import Utils.Result;
import java.sql.SQLException;

public abstract class Template
{
    public final Result insert(double valor, int ped_cod, int com_cod)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = null;
            Pedido ped = null;
            Compra comp = null;
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
    
    abstract boolean execute(Movimento mov);
}
