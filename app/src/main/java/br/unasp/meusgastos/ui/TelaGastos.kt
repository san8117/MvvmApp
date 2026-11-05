package br.unasp.meusgastos.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unasp.meusgastos.model.Categoria
import br.unasp.meusgastos.model.Gasto
import br.unasp.meusgastos.viewmodel.GastosViewModel
import java.text.NumberFormat
import java.util.Locale

private val AzulMarinho = Color(0xFF123F63)
private val Laranja = Color(0xFFF56E50)
private val AzulClaro = Color(0xFFE5EFF8)
private val Fundo = Color(0xFFF1F5F9)
private val TextoSecundario = Color(0xFF6383A0)

@Composable
fun TelaGastos(
    vm: GastosViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Fundo) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Text("Meus Gastos", color = AzulMarinho, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(18.dp))

            FormularioGasto(
                descricao = vm.descricao,
                valorTexto = vm.valorTexto,
                categoria = vm.categoriaSelecionada,
                erro = vm.erro,
                aoDigitarDescricao = vm::aoDigitarDescricao,
                aoDigitarValor = vm::aoDigitarValor,
                aoSelecionarCategoria = vm::aoSelecionarCategoria,
                aoAdicionar = vm::adicionar
            )

            Spacer(Modifier.height(22.dp))
            FiltroCategorias(selecionada = vm.filtro, aoFiltrar = vm::aoFiltrar)
            Spacer(Modifier.height(14.dp))
            ResumoTotal(total = vm.total, quantidade = vm.quantidade)
            Spacer(Modifier.height(14.dp))

            if (vm.gastosVisiveis.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text("Nenhum gasto nesta categoria.", color = TextoSecundario, modifier = Modifier.padding(20.dp))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items = vm.gastosVisiveis, key = { it.id }) { gasto ->
                        LinhaGasto(gasto = gasto, aoRemover = { vm.remover(gasto.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun FormularioGasto(
    descricao: String,
    valorTexto: String,
    categoria: Categoria,
    erro: String?,
    aoDigitarDescricao: (String) -> Unit,
    aoDigitarValor: (String) -> Unit,
    aoSelecionarCategoria: (Categoria) -> Unit,
    aoAdicionar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CampoGasto(descricao, aoDigitarDescricao, "Descrição do gasto", KeyboardType.Text)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CampoGasto(
                    valorTexto, aoDigitarValor, "0,00", KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = aoAdicionar,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulMarinho, contentColor = Color.White),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Adicionar", fontWeight = FontWeight.Bold)
                }
            }
            SeletorCategorias(selecionada = categoria, aoSelecionar = aoSelecionarCategoria)
            if (erro != null) {
                Text(erro, color = Laranja, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun CampoGasto(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, color = TextoSecundario) },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AzulClaro,
            unfocusedBorderColor = AzulClaro,
            focusedContainerColor = Fundo,
            unfocusedContainerColor = Fundo,
            cursorColor = AzulMarinho
        )
    )
}

@Composable
fun SeletorCategorias(selecionada: Categoria, aoSelecionar: (Categoria) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Categoria.entries.forEach { categoria ->
            ChipCategoria(categoria.rotulo, categoria == selecionada) { aoSelecionar(categoria) }
        }
    }
}

@Composable
fun FiltroCategorias(selecionada: Categoria?, aoFiltrar: (Categoria?) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        ChipCategoria("Todas", selecionada == null) { aoFiltrar(null) }
        Categoria.entries.forEach { categoria ->
            ChipCategoria(categoria.rotulo, categoria == selecionada) { aoFiltrar(categoria) }
        }
    }
}

@Composable
private fun ChipCategoria(texto: String, selecionada: Boolean, aoClicar: () -> Unit) {
    TextButton(
        onClick = aoClicar,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (selecionada) Laranja else AzulClaro,
            contentColor = if (selecionada) Color.White else TextoSecundario
        ),
        modifier = Modifier.height(36.dp)
    ) {
        Text(texto, fontSize = 13.sp, fontWeight = if (selecionada) FontWeight.Bold else FontWeight.Medium, maxLines = 1)
    }
}

@Composable
fun ResumoTotal(total: Double, quantidade: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AzulMarinho)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total", color = Color(0xFFB8D0E4), fontSize = 13.sp)
                Spacer(Modifier.height(3.dp))
                Text(formatarMoeda(total), color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
            Text("$quantidade gasto(s)", color = Color(0xFFB8D0E4), fontSize = 14.sp)
        }
    }
}

@Composable
fun LinhaGasto(gasto: Gasto, aoRemover: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(gasto.descricao, color = AzulMarinho, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(3.dp))
                Text(gasto.categoria.rotulo, color = TextoSecundario, fontSize = 13.sp)
            }
            Spacer(Modifier.width(8.dp))
            Text(formatarMoeda(gasto.valor), color = AzulMarinho, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            TextButton(onClick = aoRemover) {
                Text("remover", color = Laranja, fontSize = 12.sp)
            }
        }
    }
}

private fun formatarMoeda(valor: Double): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valor)

@Preview(showBackground = true)
@Composable
fun ResumoTotalPreview() {
    MaterialTheme { ResumoTotal(total = 201.70, quantidade = 4) }
}

@Preview(showBackground = true)
@Composable
fun LinhaGastoPreview() {
    MaterialTheme {
        LinhaGasto(Gasto(1, "Almoço no bandejão", 18.50, Categoria.ALIMENTACAO), aoRemover = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioComErroPreview() {
    MaterialTheme {
        FormularioGasto(
            descricao = "Café",
            valorTexto = "abc",
            categoria = Categoria.ALIMENTACAO,
            erro = "O valor precisa ser um número",
            aoDigitarDescricao = {},
            aoDigitarValor = {},
            aoSelecionarCategoria = {},
            aoAdicionar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FiltroCategoriasPreview() {
    MaterialTheme { FiltroCategorias(selecionada = null, aoFiltrar = {}) }
}

@Preview(showBackground = true)
@Composable
fun TelaGastosPreview() {
    MaterialTheme { TelaGastos(vm = viewModel()) }
}
