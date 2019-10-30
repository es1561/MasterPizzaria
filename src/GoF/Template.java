package GoF;

import DataBase.Banco;
import Model.Caixa;
import Utils.Result;
import java.sql.SQLException;

public abstract class Template
{
    public final Result insert(double valor)
    {
        return null;
    }
    
    abstract boolean execute();
}
