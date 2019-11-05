package Model;

public class Fornecedor
{
    private int codigo;
    private String nome;
    private String fone;

    public Fornecedor()
    {
    }

    public Fornecedor(int codigo)
    {
        this.codigo = codigo;
    }

    public Fornecedor(String nome)
    {
        this.nome = nome;
    }

    public Fornecedor(String nome, String fone)
    {
        this.nome = nome;
        this.fone = fone;
    }
    
    public Fornecedor(int codigo, String nome, String fone)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.fone = fone;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getFone()
    {
        return fone;
    }

    public void setFone(String fone)
    {
        this.fone = fone;
    }
    
    
}
