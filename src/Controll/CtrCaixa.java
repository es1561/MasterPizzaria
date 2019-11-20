package Controll;

import DataBase.Banco;
import Model.Caixa;
import Utils.Result;
import java.sql.Date;
import java.sql.SQLException;

public class CtrCaixa 
{
    private static CtrCaixa instance;
    
    private CtrCaixa()
    {
        
    }
    
    public static CtrCaixa instancia()
    {
        if(instance == null)
            instance = new CtrCaixa();

        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    public Result insert(Date data, Date dataAbertura, double valor)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = new Caixa(data, dataAbertura, null, valor, 0, 0);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(caixa.insert())
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
    
    public Result insert(Date data, Date dataFechamento, double valor, double entrada, double saida)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = (Caixa) new Caixa(data).searchByToday();
            
            caixa.setDataFechamento(dataFechamento);
            caixa.setValorAbertura(valor);
            caixa.setEntrada(entrada);
            caixa.setSaida(saida);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(caixa.update())
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
    
    public Result insert(Date data)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = new Caixa(data);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(caixa.delete())
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
    
    public Result fechar(Date data)
    {
        Result result = new Result();
        Banco banco = Banco.conectar();
        
        try
        {
            Caixa caixa = new Caixa(data);
            
            if(banco.isConnected())
            {
                banco.getConnection().setAutoCommit(false);
                if(caixa.close())
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
}
