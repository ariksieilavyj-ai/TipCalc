package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalc.ui.theme.TipCalcTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Slider
import kotlin.math.round
import kotlin.ranges.rangeTo
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment
import kotlin.ranges.contains
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    onScreen()
                }
            }
        }
    }
}

fun sellCount(sum: Int?, quentity: Int?): Int {
    if (sum!=null && quentity!=null){
        return when (quentity) {
            in 1 .. 2 -> round(sum * 0.03).toInt()
            in 3 .. 5 -> round(sum * 0.05).toInt()
            in 6 .. 10 -> round(sum * 0.07).toInt()
            in 11 .. 100 -> round(sum * 0.1).toInt()
            else -> 0
        }
    }
    else return 0
}

fun tipsCount(percent: Float?, sum: Int?): Int {
    if (percent != null && sum!= null) {
        return (sum * (percent * 0.01)).toInt()
    }
    else return 0
}

@Composable
fun onScreen() {
    var sumOrder by remember { mutableStateOf("") }
    var queOrder by remember { mutableStateOf("") }
    var sliderValue by remember { mutableStateOf(0f) }
    var radioValue = queOrder.toIntOrNull()
    var sell = sellCount(sumOrder.toIntOrNull(),queOrder.toIntOrNull())
    var tips = tipsCount(sliderValue, sumOrder.toIntOrNull())
    var result by remember { mutableStateOf("") }
    var finish by remember { mutableStateOf("") }
    Box(modifier = Modifier.
    fillMaxSize()
    ){
        Column(){
            TextField(value = sumOrder, modifier = Modifier.padding(5.dp),
                onValueChange = {
                    sumOrder = it
                    finish = ""
                    result = "Скидка: " + sell.toString()
                }, placeholder = {Text("Сумма заказа")})
            if ((sumOrder.toIntOrNull()==null) && (sumOrder.isNullOrEmpty()==false)){
                Text("Неподдерживаемый формат", modifier = Modifier.padding(5.dp), )
            }
            TextField(value = queOrder, modifier = Modifier.padding(5.dp),
                onValueChange = {
                    queOrder = it
                    finish = ""
                    result = "Скидка: " + sell.toString()
                }, placeholder = {Text("Кол-во блюд")})
            if ((queOrder.toIntOrNull()==null) && (queOrder.isNullOrEmpty()==false)){
                Text("Неподдерживаемый формат", modifier = Modifier.padding(5.dp), )
            }
            Text("Чаевые: " + tips.toString(), modifier = Modifier.padding(5.dp))
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    finish = ""
                    result = "Скидка: " + sell.toString()
                },
                valueRange = 0f..25f,
                steps = 4,
                modifier = Modifier.padding(5.dp),
            )
            Text(round(sliderValue).toInt().toString(), modifier = Modifier.padding(5.dp))
            Row(modifier = Modifier.padding(5.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = (radioValue in 1 .. 2),
                        onClick = {},
                    )
                    Text("3%")

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = (radioValue in 3 .. 5),
                        onClick = {},
                    )
                    Text("5%")

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = (radioValue in 6 .. 10),
                        onClick = {},
                    )
                    Text("7%")

                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = (radioValue in (11..100)),
                        onClick = {},
                    )
                    Text("10%")

                }
            }
            if (finish != ""){
                Text(finish, modifier = Modifier.padding(5.dp))
            }
            else Text(result, modifier = Modifier.padding(5.dp))
            Button(
                onClick = {
                    if (sumOrder.toIntOrNull() != null){
                        finish = (sumOrder.toInt() - sell + tips).toString()
                    }
                    else finish = "Скидка: " + sell.toString()
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Нажми")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalcTheme {
        onScreen()
    }
}