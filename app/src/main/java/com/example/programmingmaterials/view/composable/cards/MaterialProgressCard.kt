package com.example.programmingmaterials.view.composable.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.programmingmaterials.model.MaterialProgressUiModel
import com.example.programmingmaterials.ui.theme.ProgrammingMaterialsTheme

@Composable
fun MaterialProgressCard(
    uiModel: MaterialProgressUiModel,
    onClick: (Int) -> Unit,
    cardColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        modifier = Modifier
            .height(56.dp)
            .clickable(onClick = { onClick(uiModel.id) })
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardColor)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = uiModel.materialName,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                uiModel.categoryName,
                style = MaterialTheme.typography.labelMedium,
                color = textColor.copy(alpha = 0.6f)
            )
            Text(
                text = uiModel.status,
                style = MaterialTheme.typography.labelMedium,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun MaterialProgressPreview() {
    ProgrammingMaterialsTheme {
        MaterialProgressCard(
            MaterialProgressUiModel(1, "Material 1", "Category 1", "Started"),
            onClick = {}
        )
    }
}