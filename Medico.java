public record Medico(int id, String nome, String especialidade) {
    @Override
    public String toString() {
        return "[" + id + "] Dr(a). " + nome + " - " + especialidade;
    }
}
