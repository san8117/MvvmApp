# ESTRAGOS — Etapa 7

> Registro de análise dos cinco experimentos propostos no tutorial. Os resultados abaixo descrevem o comportamento esperado a partir do código e do mecanismo de estado do Compose. **Os experimentos ainda precisam ser executados no Android Studio para registrar a observação real e copiar qualquer mensagem literal exibida pelo ambiente.** Não inventei logs ou mensagens de erro.

## Estrago 1 — Instanciar o ViewModel diretamente

**Alteração:** trocar `vm: GastosViewModel = viewModel()` por `vm: GastosViewModel = GastosViewModel()`.

**Previsão:** a tela passa a criar uma instância diretamente durante a composição, em vez de obter a instância gerenciada pelo mecanismo de ViewModel.

**O que se espera observar:** ao ocorrer uma recomposição, uma nova instância pode ser criada e o estado digitado/lista pode ser perdido ou reinicializado. Isso também ignora o ciclo de vida e o escopo de armazenamento do ViewModel.

**Mensagem literal:** não há uma mensagem de erro específica garantida por essa alteração; registrar aqui somente se o Android Studio/Logcat apresentar uma mensagem durante o teste.

## Estrago 2 — Usar uma lista comum no ViewModel

**Alteração:** substituir `mutableStateListOf<Gasto>()` por `mutableListOf<Gasto>()`.

**Previsão:** a lista deixa de notificar o Compose quando seus elementos mudam.

**O que se espera observar:** adicionar ou remover um gasto altera a lista em memória, mas essa alteração sozinha não solicita recomposição. A tela pode continuar exibindo a lista antiga até que outro estado observado provoque uma recomposição.

**Mensagem literal:** normalmente não é um erro de compilação; registrar eventual mensagem real do ambiente, sem inventar uma.

## Estrago 3 — Guardar o total em uma variável atualizada só ao adicionar

**Alteração:** usar `var total by mutableStateOf(0.0)` e atualizar o valor somente em `adicionar()`.

**Previsão:** o total ficará desatualizado quando uma operação que altera o resultado não atualizar essa variável.

**O que se espera observar:** depois de adicionar três gastos e remover um, a lista perde o item, mas o total continua incluindo o valor removido. Se houver filtro, o total também não acompanhará necessariamente a categoria selecionada.

**Mensagem literal:** não se espera erro de compilação; o problema é de consistência dos dados exibidos.

## Estrago 4 — Fazer o ViewModel depender da classe concreta

**Alteração:** substituir a dependência de `GastoRepository` por uma instância concreta de `GastoRepositoryMemoria`.

**Previsão:** o ViewModel ficará acoplado à implementação em memória.

**O que se espera observar:** a troca para `GastoRepositoryDemo` deixa de ser feita apenas pela substituição da implementação recebida no construtor. Será necessário alterar o próprio ViewModel, contrariando o teste de substituição da Etapa 6.

**Mensagem literal:** não há mensagem de erro garantida; o obstáculo é arquitetural e aparece ao tentar trocar a implementação.

## Estrago 5 — Colocar a regra de validação no onClick

**Alteração:** mover a condição de descrição não vazia para dentro do `Button(onClick = { ... })` na tela.

**Previsão:** a regra deixa de pertencer ao ViewModel e passa a ficar presa àquela tela.

**O que se espera observar:** a validação pode funcionar naquele botão, mas outra tela que também cadastra gastos teria que copiar a mesma regra. Um teste do ViewModel chamando `adicionar()` também não verificaria a validação que ficou na UI.

**Mensagem literal:** não se espera erro de compilação; a falha é de separação de responsabilidades.

---

## Registro de execução pendente

Depois de executar cada alteração temporariamente no Android Studio:

1. Anote o que realmente aconteceu no emulador.
2. Copie a mensagem literal do Build/Logcat quando houver.
3. Restaure o código correto antes de continuar para o próximo estrago.
4. Atualize as seções acima com suas próprias observações.

Não mantenha nenhuma das cinco alterações quebradas na versão final do aplicativo.
