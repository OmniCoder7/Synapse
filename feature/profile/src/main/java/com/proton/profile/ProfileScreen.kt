import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.proton.domain.models.User
import com.proton.profile.LoadingScreen
import com.proton.profile.ProfileViewModel
import com.proton.profile.R
import com.proton.profile.ui.composable.AccountStatusDialog
import com.proton.profile.ui.composable.AddressListDialog
import com.proton.profile.ui.composable.DOBDialog
import com.proton.profile.ui.composable.GenderDialog
import com.proton.profile.ui.composable.TextEditDialog
import com.proton.profile.ui.state.DialogState
import com.proton.profile.ui.state.ProfileItemData
import com.proton.profile.ui.state.ProfileUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    id: Long = 0,
) {
    val viewModel = koinViewModel<ProfileViewModel> { parametersOf(id) }
    val userState by viewModel.userState.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsState()
    val accountItems by viewModel.accountInfo.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    when (val state = userState) {
        ProfileUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is ProfileUiState.Success -> ProfileScreen(
            modifier = modifier,
            user = state.user,
            userInfo = userInfo,
            accountInfo = accountItems,
            dialogState = dialogState
        )
    }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    userInfo: List<ProfileItemData>,
    accountInfo: List<ProfileItemData>,
    dialogState: DialogState,
) {
    val viewModel = koinViewModel<ProfileViewModel> { parametersOf(user.userId) }

    Column(
        modifier = modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfileImage(user.profile)
        ProfileItemsList(
            userInfo = userInfo, accountInfo = accountInfo, onEditClick = viewModel::showDialog
        )
        DialogHandler(dialogState, user, viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileItemsList(
    userInfo: List<ProfileItemData>,
    accountInfo: List<ProfileItemData>,
    onEditClick: (ProfileItemData) -> Unit,
) {
    val scrollableState = rememberLazyListState()
    LazyColumn(
        state = scrollableState,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader { Text(text = "Personal Information") }
        items(items = userInfo, key = { it.title }, contentType = {it.type}) { item ->
            ProfileItem(item = item, onEditClick = { onEditClick(item) })
        }

        stickyHeader { Text("Account Settings") }
        items(items = accountInfo, key = { it.title }, contentType = {it.type}) { item ->
            ProfileItem(item = item, onEditClick = { onEditClick(item) })
        }
    }
}

@Composable
fun DialogHandler(dialogState: DialogState, user: User, viewModel: ProfileViewModel) {
    dialogState.let {
        when (dialogState) {
            is DialogState.Hidden -> Unit
            is DialogState.TextEdit -> TextEditDialog(
                title = dialogState.title, initialValue = dialogState.initialValue, onEditSave = {
                    dialogState.onSave(it)
                }, onDismissRequest = viewModel::hideDialog, keyboardType = dialogState.keyboardType
            )

            is DialogState.AccountStatus -> AccountStatusDialog(
                onDismiss = viewModel::hideDialog, id = user.userId
            )

            is DialogState.DOB -> DOBDialog(onDismiss = viewModel::hideDialog)
            is DialogState.Gender -> GenderDialog(modifier = Modifier.fillMaxWidth(),
                onDismiss = viewModel::hideDialog,
                isMale = user.gender == "Male",
                onGenderChanged = { viewModel.updateUserField { user: User -> user.copy(gender = if (it) "Male" else "Female") } })

            is DialogState.AddressList -> AddressListDialog(
                addressType = dialogState.type, onDismiss = viewModel::hideDialog
            )

            DialogState.Accessibility -> {}
            DialogState.AccountManagement -> {}
            DialogState.LanguageAndRegion -> {}
            DialogState.NotificationPreferences -> {}
            DialogState.Privacy -> {}
            DialogState.Security -> {}
        }
    }
}

@Composable
fun ProfileImage(profile: String) {
    AsyncImage(
        model = profile,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        placeholder = painterResource(R.drawable.profile_placeholder)
    )
}

@Composable
fun ProfileItem(
    item: ProfileItemData,
    onEditClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(25),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp, pressedElevation = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = item.iconId),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)) {
                Text(
                    text = item.title,
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = colorResource(R.color.feature_profile_profile_item_title)
                )
                if (item.subTitle.isNotEmpty()) Text(
                    text = item.subTitle.substring(range = 0..<if (item.subTitle.length < 24) item.subTitle.length else 24),
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    color = colorResource(R.color.feature_profile_profile_item_title)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            item.onClickListener

            item.onEditSave?.let {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .clickable(onClick = onEditClick)
                )

            }
        }
    }
}