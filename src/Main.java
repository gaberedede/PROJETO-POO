
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Exceção personalizada para desconto maior que juros na classe Casa
class DescontoMaiorDoQueJurosException extends Exception {
    public DescontoMaiorDoQueJurosException(String mensagem) {
        super(mensagem);
    }
}

// Classe abstrata Financiamento
abstract class Financiamento {
    protected double valorImovel;
    protected int prazoFinanciamento;
    protected double taxaJurosAnual;

    // Construtor
    public Financiamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual) {
        this.valorImovel = valorImovel;
        this.prazoFinanciamento = prazoFinanciamento;
        this.taxaJurosAnual = taxaJurosAnual;
    }

    // Método abstrato para calcular o pagamento mensal
    public abstract double calcularPagamentoMensal();

    // Método para calcular o total do pagamento
    public double calcularTotalPagamento() {
        double pagamentoMensal = calcularPagamentoMensal();
        return pagamentoMensal * prazoFinanciamento * 12;
    }

    // Método para obter o valor do imóvel
    public double getValorImovel() {
        return valorImovel;
    }
}

// Subclasse Casa
class Casa extends Financiamento {
    private double areaConstruida;
    private double tamanhoTerreno;

    public Casa(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, double areaConstruida, double tamanhoTerreno) {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);
        this.areaConstruida = areaConstruida;
        this.tamanhoTerreno = tamanhoTerreno;
    }

    @Override
    public double calcularPagamentoMensal() {
        double taxaJurosMensal = taxaJurosAnual / 12 / 100;
        int meses = prazoFinanciamento * 12;
        double pagamentoMensal = (valorImovel / meses) * (1 + taxaJurosMensal);
        return pagamentoMensal + 80; // adiciona R$ 80 após o cálculo do pagamento mensal com juros
    }

    // Método para aplicar desconto
    public void aplicarDesconto(double desconto) throws DescontoMaiorDoQueJurosException {
        double taxaJurosMensal = taxaJurosAnual / 12 / 100;
        double pagamentoMensalComJuros = (valorImovel / (prazoFinanciamento * 12)) * (1 + taxaJurosMensal);
        if (desconto > taxaJurosMensal) {
            throw new DescontoMaiorDoQueJurosException("O desconto não pode ser maior do que os juros da mensalidade.");
        }
        double pagamentoMensalComDesconto = pagamentoMensalComJuros - desconto;
        System.out.println("Pagamento mensal com desconto: R$" + pagamentoMensalComDesconto);
    }
}

// Subclasse Apartamento
class Apartamento extends Financiamento {
    private int numeroVagasGaragem;
    private int numeroAndar;

    public Apartamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, int numeroVagasGaragem, int numeroAndar) {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);
        this.numeroVagasGaragem = numeroVagasGaragem;
        this.numeroAndar = numeroAndar;
    }

    @Override
    public double calcularPagamentoMensal() {
        double taxaMensal = taxaJurosAnual / 12 / 100 ;
        int meses = prazoFinanciamento * 12;
        double pagamentoMensal = (valorImovel * Math.pow(1 + taxaMensal, meses)) / (Math.pow(1 + taxaMensal, meses) - 1);
        return pagamentoMensal;
    }
}

// Subclasse Terreno
class Terreno extends Financiamento {
    private String tipoZona;

    public Terreno(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, String tipoZona) {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);
        this.tipoZona = tipoZona;
    }

    @Override
    public double calcularPagamentoMensal() {
        double pagamentoMensal = 0;
        return pagamentoMensal * 1.02; // adiciona 2% após o cálculo do pagamento mensal com juros
    }
}

// Classe InterfaceUsuario
class InterfaceUsuario {
    private Scanner scanner;

    // Construtor
    public InterfaceUsuario() {
        scanner = new Scanner(System.in);
    }

    // Método para pedir ao usuário o valor do imóvel
    public double pedirValorImovel() {
        double valor = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o valor do imóvel: ");
                valor = scanner.nextDouble();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return valor;
    }

    // Método para pedir ao usuário o prazo do financiamento
    public int pedirPrazoFinanciamento() {
        int prazo = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o prazo do financiamento (em anos): ");
                prazo = scanner.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número inteiro válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return prazo;
    }

    // Método para pedir ao usuário a taxa de juros anual
    public double pedirTaxaJurosAnual() {
        double taxa = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite a taxa de juros anual (%): ");
                taxa = scanner.nextDouble();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return taxa;
    }

    // Métodos para pedir atributos específicos de cada tipo de financiamento

    // Para Casa
    public double pedirAreaConstruida() {
        double area = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite a área construída da casa m²: ");
                area = scanner.nextDouble();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return area;
    }

    public double pedirTamanhoTerreno() {
        double tamanho = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o tamanho do terreno da casa m²: ");
                tamanho = scanner.nextDouble();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return tamanho;
    }

    // Para Apartamento
    public int pedirNumeroVagasGaragem() {
        int vagas = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o número de vagas na garagem do apartamento: ");
                vagas = scanner.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número inteiro válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return vagas;
    }

    public int pedirNumeroAndar() {
        int andar = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o número do andar do apartamento: ");
                andar = scanner.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número inteiro válido.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return andar;
    }

    // Para Terreno
    public String pedirTipoZona() {
        String tipo = "";
        boolean inputValido = false;
        do {
            try {
                System.out.print("Digite o tipo de zona do terreno (residencial/comercial): ");
                tipo = scanner.next();
                if (!tipo.equalsIgnoreCase("residencial") && !tipo.equalsIgnoreCase("comercial")) {
                    throw new InputMismatchException();
                }
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite 'residencial' ou 'comercial'.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return tipo;
    }

    // Método para pedir ao usuário quantos financiamentos deseja fazer
    public int pedirQuantidadeFinanciamentos() {
        int quantidade = 0;
        boolean inputValido = false;
        do {
            try {
                System.out.print("Quantos financiamentos deseja fazer? Digite um número inteiro positivo: ");
                quantidade = scanner.nextInt();
                if (quantidade <= 0) {
                    throw new InputMismatchException();
                }
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número inteiro positivo.");
                scanner.next(); // limpar o buffer do scanner
            }
        } while (!inputValido);
        return quantidade;
    }
}

// Classe Main
public class Main {
    public static void main(String[] args) {
        // Cria uma instância de InterfaceUsuario para receber os dados do usuário
        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();
        ArrayList<Financiamento> financiamentos = new ArrayList<>();

        // Pergunta ao usuário quantos financiamentos deseja fazer
        int quantidadeFinanciamentos = interfaceUsuario.pedirQuantidadeFinanciamentos();

        // Loop para coletar os dados dos financiamentos
        Scanner scanner = null;
        for (int i = 0; i < quantidadeFinanciamentos; i++) {
            // Seleção do tipo de financiamento
            System.out.println("\nEscolha o tipo de financiamento para o " + (i + 1) + "º financiamento:");
            System.out.println("1. Casa");
            System.out.println("2. Apartamento");
            System.out.println("3. Terreno");

            scanner = new Scanner(System.in);
            int escolha = 0;
            boolean inputValido = false;
            do {
                try {
                    System.out.print("Digite o número correspondente ao tipo de imóvel desejado: ");
                    escolha = scanner.nextInt();
                    if (escolha < 1 || escolha > 3) {
                        throw new InputMismatchException();
                    }
                    inputValido = true;
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida. Digite um número válido.");
                    scanner.next(); // limpar o buffer do scanner
                }
            } while (!inputValido);

            // Financiamento inserido pelo usuário
            System.out.println("\nInsira os dados do " + (i + 1) + "º financiamento:");

            double valorImovel = interfaceUsuario.pedirValorImovel();
            int prazoFinanciamento = interfaceUsuario.pedirPrazoFinanciamento();
            double taxaJurosAnual = interfaceUsuario.pedirTaxaJurosAnual();

            switch (escolha) {
                case 1:
                    double areaConstruidaCasa = interfaceUsuario.pedirAreaConstruida();
                    double tamanhoTerrenoCasa = interfaceUsuario.pedirTamanhoTerreno();
                    Casa casa = new Casa(valorImovel, prazoFinanciamento, taxaJurosAnual, areaConstruidaCasa, tamanhoTerrenoCasa);
                    financiamentos.add(casa);
                    break;
                case 2:
                    int numeroVagasGaragem = interfaceUsuario.pedirNumeroVagasGaragem();
                    int numeroAndar = interfaceUsuario.pedirNumeroAndar();
                    Apartamento apartamento = new Apartamento(valorImovel, prazoFinanciamento, taxaJurosAnual, numeroVagasGaragem, numeroAndar);
                    financiamentos.add(apartamento);
                    break;
                case 3:
                    String tipoZona = interfaceUsuario.pedirTipoZona();
                    Terreno terreno = new Terreno(valorImovel, prazoFinanciamento, taxaJurosAnual, tipoZona);
                    financiamentos.add(terreno);
                    break;
            }
        }

        // Calcula e exibe o total dos valores dos imóveis e o total dos financiamentos
        double totalValorImoveis = 0;
        double totalFinanciamentos = 0;

        for (Financiamento financiamento : financiamentos) {
            totalValorImoveis += financiamento.getValorImovel();
            totalFinanciamentos += financiamento.calcularTotalPagamento();
        }

        System.out.printf("\nTotal de todos os imóveis: R$ %.2f%n", totalValorImoveis);
        System.out.printf("Total de todos os financiamentos: R$ %.2f%n", totalFinanciamentos);

        // Exemplo de aplicação da exceção na classe Casa
        if (!financiamentos.isEmpty()) {
            if (financiamentos.get(0) instanceof Casa) {
                Casa casa = (Casa) financiamentos.get(0);
                try {
                    casa.aplicarDesconto(100); // Tentativa de aplicar desconto maior que os juros
                } catch (DescontoMaiorDoQueJurosException e) {
                    System.out.println("Erro ao aplicar desconto: " + e.getMessage());
                }
            }
        }

        // Fechando o scanner
        scanner.close();
    }
}