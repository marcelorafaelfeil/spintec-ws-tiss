package br.com.spintec.wstiss.service;

import br.com.spintec.wstiss.core.TissWsClient;
import br.com.spintec.wstiss.model.SolicitacaoProcedimentoModel;
import br.com.spintec.wstiss.model.response.SolicitacaoProcedimentoResponseModel;
import br.gov.ans.padroes.tiss.schemas.v30500.AutorizacaoProcedimentoWS;
import br.gov.ans.padroes.tiss.schemas.v30500.SolicitacaoProcedimentoWS;

import java.util.Arrays;
import java.util.List;

public class SolicitacaoProcedimentoService {
    private TissWsClient<SolicitacaoProcedimentoWS, SolicitacaoProcedimentoModel, AutorizacaoProcedimentoWS> clientWS = new TissWsClient<>();

    public SolicitacaoProcedimentoResponseModel<AutorizacaoProcedimentoWS> enviarSolicitacao(SolicitacaoProcedimentoModel solicitacaoProcedimento) {
        SolicitacaoProcedimentoResponseModel<AutorizacaoProcedimentoWS> retorno = new SolicitacaoProcedimentoResponseModel<>();
        try {
            retorno.setVersaoTISS("3.05.00");
            final AutorizacaoProcedimentoWS respostaSolicitacao = clientWS.chamarWS(solicitacaoProcedimento, SolicitacaoProcedimentoWS.class, "3.05.00");
            if (respostaSolicitacao.getAutorizacaoProcedimento() != null && respostaSolicitacao.getAutorizacaoProcedimento().getMensagemErro() != null) {
                retorno.setSucesso(false);
            }
            retorno.setRetornoSolicitacaoProcedimento(respostaSolicitacao);
        } catch (Throwable re) {
            preencherRetorno(retorno, re);
        }
        return retorno;
    }

    private void preencherRetorno(SolicitacaoProcedimentoResponseModel<AutorizacaoProcedimentoWS> retorno, Throwable e) {
        e.setStackTrace(tratarStackTrace(e));
        retorno.setSucesso(false);
        retorno.setErro(e);
    }

    private StackTraceElement[] tratarStackTrace(Throwable e) {
        List<StackTraceElement> stack = Arrays.asList(e.getStackTrace());
        if(stack.size() > 3) {
            List<StackTraceElement> subStack = stack.subList(0, 3);
            return subStack.toArray(new StackTraceElement[3]);
        } else {
            return e.getStackTrace();
        }
    }
}
