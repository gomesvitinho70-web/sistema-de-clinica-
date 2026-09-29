import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {
    public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private final Paciente paciente;
    private final Medico medico;
    private final LocalDateTime dataHora;
    private StatusConsulta status = StatusConsulta.AGENDADA;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getDataHora() { return dataHora; }
    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }

    @Override
    public String toString() {
        return "Consulta #" + id + " | " + dataHora.format(FORMATO)
                + " | Paciente: " + paciente.nome()
                + " | Médico: " + medico.nome() + " (" + medico.especialidade() + ")"
                + " | " + status;
    }
}
