package com.example.programmingmaterials.viewmodel

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.media.MediaScannerConnection
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingmaterials.AuthManager
import com.example.programmingmaterials.data.repositories.HomeRepo
import com.example.programmingmaterials.data.repositories.ProgressRepo
import com.example.programmingmaterials.model.UserProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val progressRepo: ProgressRepo
) : ViewModel() {
    private val _state = mutableStateOf(UserProfileScreenState())
    val state: State<UserProfileScreenState> = _state

    fun onEmailUserTextEdit(value: String) {
        _state.value = _state.value.copy(userMailText = value)
    }

    fun onButtonClick() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val progress = progressRepo.getUserProgressByUserEmail(state.value.userMailText)
            _state.value = _state.value.copy(
                finishedMaterials = progress.finishedMaterials,
                finishedTests = progress.finishedTests,
                difficultyAVG = progress.difficultyAVG,
                userName = progress.userName,
                startedMaterials = progress.startedMaterials,
                pendingMaterials = progress.pendingMaterials,
                finishedTestsAVG = progress.finishedTestsAVG,
                currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            )
        }
        _state.value = _state.value.copy(isLoading = false, isDataValid = true)
    }


    fun exportToPdf(context: Context, state: UserProfileScreenState) {
        val document = PdfDocument()

        val pageInfo =
            PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 12f
        var yPos = 80f

        fun drawText(text: String) {
            canvas.drawText(text, 50f, yPos, paint)
            yPos += 30f
        }

        paint.textSize = 18f
        paint.isFakeBoldText = true
        canvas.drawText("ОТЧЕТ ОБ УСПЕВАЕМОСТИ", 50f, 50f, paint)
        paint.textSize = 12f
        paint.isFakeBoldText = false
        yPos += 40f

        drawText("Дата отчета: ${state.currentDate}")
        drawText("Пользователь: ${state.userName}")
        drawText("")
        drawText("Завершено материалов: ${state.finishedMaterials}")
        drawText("Отложено материалов: ${state.pendingMaterials}")
        drawText("Начато материалов: ${state.startedMaterials}")
        drawText("Средняя сложность материалов: ${state.difficultyAVG}")
        drawText("Выполнено тестов: ${state.finishedTests}")
        drawText("Среднее число выполненных условий теста: ${state.finishedTestsAVG}")

        document.finishPage(page)

        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "Отчет_${state.userName}_${state.currentDate.replace(".", "-")}.pdf"
        val file = File(downloadsDir, fileName)

        try {
            document.writeTo(FileOutputStream(file))
            document.close()

            Toast.makeText(
                context,
                "PDF сохранен в папку Загрузки: $fileName",
                Toast.LENGTH_LONG
            ).show()

            MediaScannerConnection.scanFile(
                context,
                arrayOf(file.absolutePath),
                null,
                null
            )
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Ошибка при сохранении PDF",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            document.close()
        }
    }
}