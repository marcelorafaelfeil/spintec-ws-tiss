package br.com.spintec.wstiss.model.response;

import lombok.Data;

@Data
public class SolicitacaoProcedimentoResponseModel<RetornoSolicitacaoProcedimento> {
    private String versaoTISS;
    private boolean sucesso;
    private Exception erro;
    private String message;
    private RetornoSolicitacaoProcedimento retornoSolicitacaoProcedimento;
}
