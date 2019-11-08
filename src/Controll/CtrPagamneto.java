package Controll;

import GoF.Template;
import Model.Movimento;

public class CtrPagamneto extends Template
{
    private static CtrPagamneto instance;
    
    private CtrPagamneto()
    {
    }
            
    public static CtrPagamneto instancia()
    {
        if(instance == null)
            instance = new CtrPagamneto();
            
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
            mov.setTipo(2);
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
