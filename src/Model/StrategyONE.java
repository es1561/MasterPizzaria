
package Model;

import GoF.Strategy;

public class StrategyONE implements Strategy
{
    @Override
    public double execute(double peso)
    {
        return peso < 5 ? 4.99 : new StrategyTWO().execute(peso);
    }
    
}
