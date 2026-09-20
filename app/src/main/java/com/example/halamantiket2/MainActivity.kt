package com.example.halamantiket2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var hargaTiket by remember {
                mutableStateOf(50000)
            }

            var jumlahTiket by remember {
                mutableStateOf(1)
            }

            var namaPembeli by remember {
                mutableStateOf("")
            }

            var status by remember {
                mutableStateOf("Silakan pesan tiket")
            }

            var isProcessing by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(isProcessing) {
                if (isProcessing) {
                    delay(5000)

                    status = "Tiket telah dipesan"
                    isProcessing = false
                }
            }

            TicketScreen(
                hargaTiket = hargaTiket,
                jumlahTiket = jumlahTiket,
                namaPembeli = namaPembeli,
                status = status,
                isProcessing = isProcessing,

                onNamaChange = {
                    namaPembeli = it
                },

                onTambahTiket = {
                    jumlahTiket++
                },

                onKurangTiket = {
                    if (jumlahTiket > 1) {
                        jumlahTiket--
                    }
                },

                onPesanTiket = {

                    if (namaPembeli.isBlank()) {
                        status = "Nama masih kosong"
                    } else {
                        status = "Memproses pesanan..."
                        isProcessing = true
                    }
                }
            )
        }
    }
}


@Composable
fun TicketScreen(
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    status: String,
    isProcessing: Boolean,

    onNamaChange: (String) -> Unit,
    onTambahTiket: () -> Unit,
    onKurangTiket: () -> Unit,
    onPesanTiket: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2167D5))
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                )
        ) {
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            Text(
                text = "Nama",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = namaPembeli,
                onValueChange = onNamaChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Masukkan nama Anda"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(6.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Jumlah Tiket",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = onKurangTiket,
                    modifier = Modifier
                        .width(82.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEAF2FD),
                        contentColor = Color(0xFF1A1A1A)
                    )
                ) {
                    Text(
                        text = "-",
                        fontSize = 24.sp
                    )
                }

                Text(
                    text = "$jumlahTiket",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onTambahTiket,
                    modifier = Modifier
                        .width(82.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEAF2FD),
                        contentColor = Color(0xFF1A1A1A)
                    )
                ) {
                    Text(
                        text = "+",
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Harga Tiket: Rp ${hargaTiket * jumlahTiket}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = onPesanTiket,
                enabled = !isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2167D5)
                )
            ) {
                Text(
                    text = "Pesan Tiket",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            val statusBackground = when {
                status == "Nama masih kosong" ->
                    Color(0xFFFFE9EC)

                status == "Memproses pesanan..." ->
                    Color(0xFFEAF4FF)

                status == "Tiket telah dipesan" ->
                    Color(0xFFE4F7E9)

                else ->
                    Color(0xFFF1F6FC)
            }

            val statusColor = when {
                status == "Nama masih kosong" ->
                    Color(0xFFD32F2F)

                status == "Memproses pesanan..." ->
                    Color(0xFF2167D5)

                status == "Tiket telah dipesan" ->
                    Color(0xFF299653)

                else ->
                    Color(0xFF333333)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = statusBackground,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(18.dp)
            ) {

                Text(
                    text = "Status: $status",
                    color = statusColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}