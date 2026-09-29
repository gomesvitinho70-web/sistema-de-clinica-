# Sistema de Clínica Médica (console)

Simulador em Java de um sistema para controle de atendimentos de uma clínica, desenvolvido como projeto de estudo. Ele parte de um cenário de levantamento de requisitos e implementa os requisitos funcionais e não funcionais identificados.

## Requisitos

### Funcionais
- Cadastro de pacientes e médicos
- Agendamento e cancelamento de consultas
- Consulta dos próximos agendamentos de um paciente
- Relatório mensal dos atendimentos realizados

### Não funcionais
- **Segurança:** só usuários autenticados acessam o sistema (login no início)
- **Desempenho:** consultas em memória, com resposta imediata (meta: até 3 segundos)
- **Compatibilidade:** versão web/navegadores fica como evolução futura

## Tecnologias
- Java 17 ou superior
- Sem dependências externas

## Como executar

```bash
git clone https://github.com/SEU-USUARIO/clinica-sistema.git
cd clinica-sistema
mkdir out
javac -d out src/*.java
java -cp out Main
```

Login padrão: usuário `admin`, senha `1234`.

## Estrutura

```
src/
├── Main.java            # menu e interação com o usuário
├── Clinica.java         # regras de negócio
├── Consulta.java        # entidade consulta
├── Paciente.java        # entidade paciente
├── Medico.java          # entidade médico
└── StatusConsulta.java  # AGENDADA, CANCELADA, REALIZADA
```

## Regras implementadas
- CPF de paciente não pode se repetir
- Não é possível agendar no passado
- Um médico não pode ter duas consultas no mesmo horário
- Só consultas agendadas podem ser canceladas ou concluídas
- O relatório mensal lista apenas consultas realizadas

## Melhorias futuras
- [ ] Salvar os dados em arquivo ou banco de dados
- [ ] Senhas com hash e múltiplos usuários
- [ ] Testes automatizados com JUnit
- [ ] Interface web (Spring Boot) para atender ao requisito de navegadores
- [ ] Validação real de CPF

## Licença
MIT
