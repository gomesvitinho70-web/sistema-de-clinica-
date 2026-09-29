import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String USUARIO = "admin";
    private static final String SENHA = "1234";

    private static final Scanner sc = new Scanner(System.in);
    private static final Clinica clinica = new Clinica();

    public static void main(String[] args) {
        if (!autenticar()) {
            System.out.println("Acesso negado.");
            return;
        }
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha: ");
            try {
                executar(opcao);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Erro: data em formato inválido.");
            }
        } while (opcao != 0);
        System.out.println("Sistema encerrado.");
    }

    private static boolean autenticar() {
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            System.out.print("Usuário: ");
            String u = sc.nextLine();
            System.out.print("Senha: ");
            String s = sc.nextLine();
            if (USUARIO.equals(u) && SENHA.equals(s)) {
                return true;
            }
            System.out.println("Credenciais inválidas (" + tentativa + "/3).");
        }
        return false;
    }

    private static void exibirMenu() {
        System.out.println("\n===== CLÍNICA =====");
        System.out.println("1 - Cadastrar paciente");
        System.out.println("2 - Cadastrar médico");
        System.out.println("3 - Agendar consulta");
        System.out.println("4 - Cancelar consulta");
        System.out.println("5 - Concluir consulta (marcar como realizada)");
        System.out.println("6 - Consultar próximos agendamentos do paciente");
        System.out.println("7 - Relatório mensal de atendimentos");
        System.out.println("8 - Listar pacientes e médicos");
        System.out.println("0 - Sair");
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> {
                String nome = lerTexto("Nome: ");
                String cpf = lerTexto("CPF: ");
                System.out.println("Cadastrado: " + clinica.cadastrarPaciente(nome, cpf));
            }
            case 2 -> {
                String nome = lerTexto("Nome: ");
                String esp = lerTexto("Especialidade: ");
                System.out.println("Cadastrado: " + clinica.cadastrarMedico(nome, esp));
            }
            case 3 -> {
                int pacienteId = lerInt("ID do paciente: ");
                int medicoId = lerInt("ID do médico: ");
                String data = lerTexto("Data e hora (dd/MM/yyyy HH:mm): ");
                LocalDateTime dh = LocalDateTime.parse(data, Consulta.FORMATO);
                System.out.println("Agendada: " + clinica.agendar(pacienteId, medicoId, dh));
            }
            case 4 -> {
                clinica.cancelar(lerInt("ID da consulta: "));
                System.out.println("Consulta cancelada.");
            }
            case 5 -> {
                clinica.concluir(lerInt("ID da consulta: "));
                System.out.println("Consulta marcada como realizada.");
            }
            case 6 -> {
                List<Consulta> lista = clinica.proximasConsultas(lerInt("ID do paciente: "));
                if (lista.isEmpty()) System.out.println("Nenhum agendamento futuro.");
                lista.forEach(System.out::println);
            }
            case 7 -> {
                String mes = lerTexto("Mês (MM/yyyy): ");
                YearMonth ym = YearMonth.parse(mes, DateTimeFormatter.ofPattern("MM/yyyy"));
                List<Consulta> lista = clinica.relatorioMensal(ym);
                System.out.println("--- Atendimentos realizados em " + mes + " ---");
                lista.forEach(System.out::println);
                System.out.println("Total: " + lista.size());
            }
            case 8 -> {
                System.out.println("Pacientes:");
                clinica.getPacientes().forEach(p -> System.out.println("  " + p));
                System.out.println("Médicos:");
                clinica.getMedicos().forEach(m -> System.out.println("  " + m));
            }
            case 0 -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
