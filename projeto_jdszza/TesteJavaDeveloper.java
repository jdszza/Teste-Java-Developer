/**
 * Esta aplicação Java demonstra um sistema financeiro com entidades de Empresa e Cliente.
 * Cada entidade possui um identificador único (CPF ou CNPJ) e realiza transações
 * com abatimento de taxas de sistema.
 *
 * O sistema utiliza a interface TaxaSistema para calcular as taxas de sistema,
 * e a implementação básica TaxaSaque como exemplo.
 *
 * As transações são notificadas para as entidades financeiras e um callback é
 * enviado para a Empresa após cada transação bem-sucedida, simulado usando webhook.site.
 */
package projeto_jdszza;

// Interface para definir a taxa de sistema
interface TaxaSistema {
    /**
     * Calcula a taxa de sistema com base no valor da transação.
     *
     * @param valor O valor da transação.
     * @return O valor da taxa de sistema.
     */
    double calcularTaxa(double valor);
}

// Implementação básica de TaxaSistema
class TaxaSaque implements TaxaSistema {
    @Override
    public double calcularTaxa(double valor) {
        return valor * 0.03; // Exemplo de taxa de saque
    }
}

/**
 * Classe para representar uma entidade financeira (Empresa ou Cliente).
 * Cada entidade possui um identificador único e um saldo que é afetado por transações.
 */
class EntidadeFinanceira {
    private String identificacao;
    private double saldo;
    private TaxaSistema taxaSistema;

    /**
     * Construtor da EntidadeFinanceira.
     *
     * @param identificacao O identificador único da entidade (CPF ou CNPJ).
     * @param taxaSistema A implementação da taxa de sistema a ser utilizada.
     */
    public EntidadeFinanceira(String identificacao, TaxaSistema taxaSistema) {
        this.identificacao = identificacao;
        this.taxaSistema = taxaSistema;
    }

    /**
     * Obtém o saldo atual da entidade financeira.
     *
     * @return O saldo atual.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Realiza uma transação na entidade financeira, afetando o saldo e notificando a transação.
     *
     * @param valor O valor da transação.
     */
    public void realizarTransacao(double valor) {
        double taxa = taxaSistema.calcularTaxa(valor);
        double novoSaldo = saldo + valor - taxa;

        if (callbackTransacao()) {
            this.saldo = novoSaldo;
            notificarTransacao();
        } else {
            System.out.println("Falha no callback. Transação não concluída.");
        }
    }

    /**
     * Notifica a entidade financeira sobre a transação bem-sucedida.
     */
    protected void notificarTransacao() {
        // Lógica de notificação para a Entidade Financeira (pode ser e-mail, SMS, etc.)
        System.out.println("Notificação para a Entidade Financeira: Transação realizada com sucesso. Saldo atual: " + saldo);
    }

    /**
     * Envia um callback para a Empresa após a transação.
     *
     * @return true se o callback foi bem-sucedido, false caso contrário.
     */
    protected boolean callbackTransacao() {
        // Lógica para enviar um callback para a Empresa
        // Simulação usando webhook.site
        String urlCallback = "https://webhook.site/seu-token";
        System.out.println("Enviando callback para a Empresa: " + urlCallback);
        try {
            // Lógica de envio do callback
            return true; // Callback bem-sucedido
        } catch (Exception e) {
            System.err.println("Erro ao enviar callback: " + e.getMessage());
            return false; // Callback falhou
        }
    }
}

/**
 * Classe para representar um cliente que é uma entidade financeira.
 * Além dos atributos da entidade financeira, possui um CPF único.
 */
class Cliente extends EntidadeFinanceira {
    private String cpf;

    /**
     * Construtor do Cliente.
     *
     * @param cpf O CPF único do cliente.
     * @param taxaSistema A implementação da taxa de sistema a ser utilizada.
     */
    public Cliente(String cpf, TaxaSistema taxaSistema) {
        super(cpf, taxaSistema);
        this.cpf = cpf;
    }
}

/**
 * Classe de teste para a aplicação.
 */
public class TesteJavaDeveloper {
    /**
     * Método principal para execução do exemplo.
     *
     * @param args Os argumentos da linha de comando (não utilizados neste exemplo).
     */
    public static void main(String[] args) {
        // Exemplo de uso
        TaxaSistema taxaSaque = new TaxaSaque();
        EntidadeFinanceira minhaEmpresa = new EntidadeFinanceira("123456789", taxaSaque);
        Cliente meuCliente = new Cliente("987654321", taxaSaque);

        meuCliente.realizarTransacao(1800.0);
    }
}
