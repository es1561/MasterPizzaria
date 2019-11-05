package Model;

public class Movimento
{
    private int codigo;
    private int tipo;
    private double valor;
    private Caixa caixa;
    private Pedido pedido;
    private Compra compra;

    public Movimento()
    {
    }

    public Movimento(int codigo)
    {
        this.codigo = codigo;
    }

    public Movimento(Caixa caixa)
    {
        this.caixa = caixa;
    }

    public Movimento(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Movimento(Compra compra)
    {
        this.compra = compra;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Pedido pedido)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Compra compra)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.compra = compra;
    }

    public Movimento(double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }

    public Movimento(int tipo, double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }
    
    public Movimento(int codigo, int tipo, double valor, Caixa caixa, Pedido pedido, Compra compra)
    {
        this.codigo = codigo;
        this.tipo = tipo;
        this.valor = valor;
        this.caixa = caixa;
        this.pedido = pedido;
        this.compra = compra;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public int getTipo()
    {
        return tipo;
    }

    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public Caixa getCaixa()
    {
        return caixa;
    }

    public void setCaixa(Caixa caixa)
    {
        this.caixa = caixa;
    }

    public Pedido getPedido()
    {
        return pedido;
    }

    public void setPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }

    public Compra getCompra()
    {
        return compra;
    }

    public void setCompra(Compra compra)
    {
        this.compra = compra;
    }
    
    
}
