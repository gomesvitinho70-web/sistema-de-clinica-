public record Paciente(int id, String nome, String cpf) {
    @Override
    public String toString() {
        return "[" + id + "] " + nome + " (CPF: " + cpf + ")";
    }
}
