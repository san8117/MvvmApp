# Meus Gastos — Kotlin + Jetpack Compose + MVVM

Aplicativo de controle de gastos desenvolvido em Kotlin com Jetpack Compose e arquitetura MVVM, seguindo a identidade visual da referência da atividade.

## Funcionalidades

- Cadastro de gastos com descrição, valor e categoria.
- Categorias: Alimentação, Transporte, Moradia, Lazer e Outros.
- Filtro por categoria.
- Resumo do total e da quantidade de gastos.
- Remoção individual de gastos.
- Validação dos campos do formulário.

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

## Configuração no Android Studio

1. Crie um projeto **Empty Activity** com Kotlin e Jetpack Compose.
2. Configure o package como `br.unasp.meusgastos`.
3. Copie os arquivos Kotlin deste repositório para os caminhos correspondentes.
4. Confira as dependências do módulo `app`:

```kotlin
implementation(platform("androidx.compose:compose-bom:<versão-do-projeto>"))
implementation("androidx.activity:activity-compose")
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
debugImplementation("androidx.compose.ui:ui-tooling")
```

Use a versão da Compose BOM compatível com o template/Android Studio instalado, sem duplicar dependências que já existam no projeto.

## Identidade visual

| Elemento | Cor |
|---|---|
| Azul-marinho | `#123F63` |
| Laranja | `#F56E50` |
| Azul-claro | `#E5EFF8` |
| Fundo | `#F1F5F9` |
| Cartões | `#FFFFFF` |

## Repositório de dados

O ViewModel utiliza `GastoRepositoryMemoria` por padrão, com lista inicialmente vazia. Os dados permanecem apenas em memória e são perdidos ao encerrar o processo do aplicativo.

A classe `GastoRepositoryDemo` contém gastos de exemplo para demonstrar uma lista preenchida. Para usá-la, altere a implementação padrão no construtor de `GastosViewModel` para `GastoRepositoryDemo()`.

## Previews

O arquivo `TelaGastos.kt` inclui previews para o resumo, item de gasto, formulário com erro, filtros e tela.
