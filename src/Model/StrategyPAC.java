
package Model;

import GoF.Strategy;

public class StrategyPAC implements Strategy
{
    @Override
    public double execute(double peso)
    {
        return peso < 2 ? 9.9 : new StrategySEDEX().execute(peso);
    }
    
}
