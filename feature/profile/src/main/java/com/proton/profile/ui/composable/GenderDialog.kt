package com.proton.profile.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.proton.profile.R

@Composable
fun GenderDialog(
    modifier: Modifier = Modifier,
    isMale: Boolean,
    onDismiss: () -> Unit,
    onGenderChanged: (Boolean) -> Unit,
) {
    var male by remember { mutableStateOf(isMale) }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(modifier = modifier) {
            Text(text = "Gender")
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Checkbox(checked = !male, onCheckedChange = {
                    if (male) {
                        onGenderChanged.invoke(!male)
                        male = !male
                    }
                })
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.female),
                    contentDescription = null
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
            ) {
                Text(text = "Save", modifier = Modifier.clickable {
                    onGenderChanged.invoke(male)
                    onDismiss.invoke()
                })
                Text(text = "Cancel", modifier = Modifier.clickable {
                    onDismiss.invoke()
                })
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Checkbox(checked = male, onCheckedChange = {
                    if (!male) {
                        onGenderChanged.invoke(!male)
                        male = !male
                    }
                })
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.male),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderDialogPreview() {
    GenderDialog(
        isMale = false,
        onDismiss = { },
        onGenderChanged = {},
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    )
}