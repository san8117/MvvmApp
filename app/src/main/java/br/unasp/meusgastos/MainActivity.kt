package br.unasp.meusgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import br.unasp.meusgastos.ui.TelaGastos

private val CoresMeusGastos = lightColorScheme(
    primary = Color(0xFF123F63),
    secondary = Color(0xFFF56E50),
    background = Color(0xFFF1F5F9),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF123F63),
    onSurface = Color(0xFF123F63)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = CoresMeusGastos) {
                TelaGastos()
            }
        }
    }
}
