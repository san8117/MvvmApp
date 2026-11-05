# ARQUITETURA — Meus Gastos

## 1. Se os gastos viessem de uma API, o que mudaria?

Eu criaria uma implementação de `GastoRepository` que faz as chamadas para a API. A interface em `model/GastoRepository.kt` continuaria definindo as operações que o app precisa. O `GastosViewModel` continuaria chamando `listar`, `adicionar` e `remover` pela interface, sem precisar conhecer HTTP ou detalhes da API. A tela em `ui/TelaGastos.kt` também não precisaria mudar, porque ela continua recebendo os mesmos estados e eventos.

Na prática, mudaria a implementação e a forma de fornecer essa implementação ao ViewModel. O contrato da interface e os componentes visuais permaneceriam iguais, desde que a API ofereça as mesmas operações.

## 2. Por que o total é calculado e não guardado?

Porque o total depende dos gastos que estão visíveis no filtro atual. Calculando com `gastosVisiveis.sumOf { it.valor }`, ele sempre acompanha a lista e o filtro.

Se o total fosse uma variável atualizada manualmente, eu poderia esquecer de descontar um gasto ao removê-lo. Por exemplo, depois de excluir uma despesa de R$ 20,00, a lista ficaria correta, mas o total continuaria incluindo esses R$ 20,00. A tela mostraria informações diferentes para os mesmos dados.

## 3. O que o FormularioGasto permite por não receber o ViewModel?

Ele recebe somente os valores que precisa mostrar e funções para avisar quando o usuário interage. Assim, posso testar e visualizar o formulário isoladamente com um `@Preview`, inclusive simulando um erro sem abrir o aplicativo ou criar um ViewModel real.

Também posso reutilizar o formulário em outra tela ou ligar seus eventos a outra implementação, sem deixar o componente preso à lógica do ViewModel.
