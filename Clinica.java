import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Clinica {
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();
    private int proximoIdPaciente = 1;
    private int proximoIdMedico = 1;
    private int proximoIdConsulta = 1;

    public Paciente cadastrarPaciente(String nome, String cpf) {
        if (nome.isBlank() || cpf.isBlank()) {
            throw new IllegalArgumentException("Nome e CPF são obrigatórios.");
        }
        boolean existe = pacientes.stream().anyMatch(p -> p.cpf().equals(cpf));
        if (existe) {
            throw new IllegalArgumentException("Já existe um paciente com esse CPF.");
        }
        Paciente p = new Paciente(proximoIdPaciente++, nome, cpf);
        pacientes.add(p);
        return p;
    }

    public Medico cadastrarMedico(String nome, String especialidade) {
        if (nome.isBlank() || especialidade.isBlank()) {
            throw new IllegalArgumentException("Nome e especialidade são obrigatórios.");
        }
        Medico m = new Medico(proximoIdMedico++, nome, especialidade);
        medicos.add(m);
        return m;
    }

    public Consulta agendar(int pacienteId, int medicoId, LocalDateTime dataHora) {
        Paciente paciente = buscarPaciente(pacienteId);
        Medico medico = buscarMedico(medicoId);

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar no passado.");
        }
        boolean horarioOcupado = consultas.stream().anyMatch(c ->
                c.getMedico().id() == medicoId
                && c.getDataHora().equals(dataHora)
                && c.getStatus() == StatusConsulta.AGENDADA);
        if (horarioOcupado) {
            throw new IllegalStateException("O médico já possui consulta nesse horário.");
        }
        Consulta c = new Consulta(proximoIdConsulta++, paciente, medico, dataHora);
        consultas.add(c);
        return c;
    }

    public void cancelar(int consultaId) {
        Consulta c = buscarConsulta(consultaId);
        if (c.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalStateException("Só é possível cancelar consultas agendadas.");
        }
        c.setStatus(StatusConsulta.CANCELADA);
    }

    public void concluir(int consultaId) {
        Consulta c = buscarConsulta(consultaId);
        if (c.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalStateException("Só é possível concluir consultas agendadas.");
        }
        c.setStatus(StatusConsulta.REALIZADA);
    }

    public List<Consulta> proximasConsultas(int pacienteId) {
        buscarPaciente(pacienteId);
        LocalDateTime agora = LocalDateTime.now();
        return consultas.stream()
                .filter(c -> c.getPaciente().id() == pacienteId)
                .filter(c -> c.getStatus() == StatusConsulta.AGENDADA)
                .filter(c -> c.getDataHora().isAfter(agora))
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .toList();
    }

    public List<Consulta> relatorioMensal(YearMonth mes) {
        return consultas.stream()
                .filter(c -> c.getStatus() == StatusConsulta.REALIZADA)
                .filter(c -> YearMonth.from(c.getDataHora()).equals(mes))
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .toList();
    }

    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Consulta> getConsultas() { return consultas; }

    private Paciente buscarPaciente(int id) {
        return pacientes.stream().filter(p -> p.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));
    }

    private Medico buscarMedico(int id) {
        return medicos.stream().filter(m -> m.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));
    }

    private Consulta buscarConsulta(int id) {
        return consultas.stream().filter(c -> c.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada."));
    }
}
