
package Model;

import GoF.Strategy;

public class StrategyONE implements Strategy
{
    @Override
    public double execute(double peso)
    {
        return peso == 0 ? 0 : (peso < 3 ? 7.99 : new StrategyTWO().execute(peso));
    }
    
}
