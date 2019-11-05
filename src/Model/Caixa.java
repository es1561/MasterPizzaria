package Model;

import GoF.Template;
import java.sql.Date;
import javafx.collections.ObservableList;

public class Caixa
{
    private Date data;
    private Date dataAbertura;
    private Date dataFechamento;
    private double valorAbertura;
    private double entrada;
    private double saida;
    ObservableList<Template> movimentos;

    public Caixa()
    {
    }

    public Caixa(Date data)
    {
        this.data = data;
    }

    public Caixa(Date data, double valorAbertura)
    {
        this.data = data;
        this.valorAbertura = valorAbertura;
    }

    public Caixa(Date data, Date dataAbertura, Date dataFechamento, double valorAbertura, double entrada, double saida)
    {
        this.data = data;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.valorAbertura = valorAbertura;
        this.entrada = entrada;
        this.saida = saida;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public Date getDataAbertura()
    {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura)
    {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento()
    {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento)
    {
        this.dataFechamento = dataFechamento;
    }

    public double getValorAbertura()
    {
        return valorAbertura;
    }

    public void setValorAbertura(double valorAbertura)
    {
        this.valorAbertura = valorAbertura;
    }

    public double getEntrada()
    {
        return entrada;
    }

    public void setEntrada(double entrada)
    {
        this.entrada = entrada;
    }

    public double getSaida()
    {
        return saida;
    }

    public void setSaida(double saida)
    {
        this.saida = saida;
    }
    
    public boolean isOpen()
    {
        return dataFechamento != null;
    }

    public ObservableList<Template> getMovimentos()
    {
        return movimentos;
    }

    public void setMovimentos(ObservableList<Template> movimentos)
    {
        this.movimentos = movimentos;
    }
    
    public Object searchByToday()
    {
        return null;
    }
}
