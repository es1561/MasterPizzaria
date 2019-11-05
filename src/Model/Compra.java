package Model;

public class Compra
{
    private int codigo;
    private Fornecedor fornecedor;

    public Compra()
    {
    }

    public Compra(int codigo)
    {
        this.codigo = codigo;
    }

    public Compra(Fornecedor fornecedor)
    {
        this.fornecedor = fornecedor;
    }

    public Compra(int codigo, Fornecedor fornecedor)
    {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public Fornecedor getFornecedor()
    {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor)
    {
        this.fornecedor = fornecedor;
    }
    
    
}
