# Meus Gastos — Kotlin + Jetpack Compose + MVVM

Aplicativo de controle de gastos desenvolvido em Kotlin com Jetpack Compose, seguindo a arquitetura Model–ViewModel–View e a identidade visual da referência da atividade.

## Funcionalidades

- Cadastro de gastos com descrição, valor e categoria.
- Categorias: Alimentação, Transporte, Moradia, Lazer e Outros.
- Filtro por categoria.
- Resumo calculado do total e da quantidade de gastos visíveis.
- Remoção individual de gastos.
- Validação dos campos no ViewModel.

## Estrutura

```text
br.unasp.meusgastos/
├── model/
│   ├── Categoria.kt
│   ├── Gasto.kt
│   └── GastoRepository.kt
├── viewmodel/
│   └── GastosViewModel.kt
├── ui/
│   └── TelaGastos.kt
└── MainActivity.kt
```

## Abrir e executar

1. Abra a pasta raiz deste repositório no Android Studio.
2. Aguarde o Gradle sincronizar e instale um Android SDK compatível com `compileSdk = 35`.
3. Execute a configuração `app` em um emulador ou dispositivo Android.

O projeto inclui configurações Gradle Kotlin DSL, Version Catalog e Manifest. Se o Android Studio solicitar a instalação/atualização de componentes do SDK, aceite e sincronize novamente.

## Dependências principais

- Kotlin 2.0.21
- Android Gradle Plugin 8.7.3
- Jetpack Compose via Compose BOM 2025.01.00
- Lifecycle ViewModel Compose 2.10.0
- Java 17

## Identidade visual

| Elemento | Cor |
|---|---|
| Azul-marinho | `#123F63` |
| Laranja | `#F56E50` |
| Azul-claro | `#E5EFF8` |
| Fundo | `#F1F5F9` |
| Cartões | `#FFFFFF` |

## Repositórios

O ViewModel depende da interface `GastoRepository`. Por padrão, utiliza `GastoRepositoryMemoria`, que começa vazia. `GastoRepositoryDemo` disponibiliza quatro gastos de exemplo para a demonstração da Etapa 6. Para alternar a fonte, basta mudar a implementação padrão no construtor do ViewModel.

## Previews

O arquivo `TelaGastos.kt` inclui cinco previews: resumo, item de gasto, formulário com erro, filtros e tela completa. Os componentes visuais recebem dados e callbacks, sem depender diretamente do ViewModel.

## Documentação da atividade

- [ARQUITETURA.md](ARQUITETURA.md): respostas às três perguntas da Etapa 8.
- [ESTRAGOS.md](ESTRAGOS.md): previsões e análise dos cinco experimentos da Etapa 7. Os resultados reais e mensagens literais precisam ser preenchidos após executar os testes.
