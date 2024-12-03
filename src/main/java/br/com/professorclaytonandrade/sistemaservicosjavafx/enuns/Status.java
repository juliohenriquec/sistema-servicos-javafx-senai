package br.com.professorclaytonandrade.sistemaservicosjavafx.enuns;

public enum Status {
    ABERTO(0, "Aberto"),
    ANDAMENTO(1, "Andamento"),
    ENCERRADO(2, "Encerrado");

    private Integer codigo;
    private String descricao;

    private Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Status status : Status.values()) {
            if (cod.equals(status.getCodigo())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido");
    }
}