package Controll;
import GoF.Template;
import Model.Movimento;

public class CtrRecebimento extends Template
{
    private static CtrRecebimento instance;
    
    private CtrRecebimento()
    {
    }
            
    public static CtrRecebimento instancia()
    {
        if(instance == null)
            instance = new CtrRecebimento();
            
        return instance;
    }
    
    public static void finaliza()
    {
        instance = null;
    }
    
    @Override
    protected boolean execute(Movimento mov)
    {
        boolean flag;
        
        try
        {
            mov.setTipo(1);
            flag = mov.insert();
        }
        catch(Exception ex)
        {
            flag = false;
            System.out.println(ex.getMessage());
        }
        
        return flag;
    }
}
