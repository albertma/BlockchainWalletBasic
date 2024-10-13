package albert.ma.blockchain.walletbasic

import albert.ma.blockchain.walletbasic.apps.GenerateMnemonicActivity
import albert.ma.blockchain.walletbasic.ui.BottomNavigationBar
import albert.ma.blockchain.walletbasic.ui.theme.BlockchainWalletBasicTheme
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlockchainWalletBasicTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                   MyApp()
                }
            }
        }
    }
}

data class GridButtonRes(@IntegerRes val resourceId: Int, val name: String, val id: Int){}

enum class FeatureId(val id:Int){
    MNEMONICS(0x0000001),


}
@Composable
fun DrawerContent() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menu Item 1")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Menu Item 2")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Menu Item 3")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent()
            }
    ) {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            modifier = modifier,
                            navigationIcon = {
                                IconButton(
                                        onClick = {
                                            scope.launch { drawerState.open() }
                                        }
                                ) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }

                    )
                },
                bottomBar = {
                    BottomNavigationBar(selectedTabIndex) {
                        index -> selectedTabIndex = index
                    }
                }
        ) {
            paddingValues -> Column(modifier = Modifier.padding(paddingValues)) {
                when (selectedTabIndex) {
                    0 -> Home()
                    1 -> TabContent2()
                    2 -> TabContent3()
                }
            }
        }
    }
}



fun handleClickGridButton(context: Context, id:Int){
    when(id){
        FeatureId.MNEMONICS.id -> {
            val intent = Intent(context, GenerateMnemonicActivity::class.java)
            context.startActivity(intent)
        }
        else ->{
            println("click button id: $id")
        }
    }
}

@Composable
fun MyGridButton(
    iconRes: Int,
    name: String,
    id: Int,
    onClick: (Int)->Unit,
    modifier: Modifier = Modifier // 传入 Modifier，默认为空){}
) {
    Column(
            modifier = modifier
                .padding(8.dp)
                .clickable {onClick(id)},
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.size(48.dp)
        )
        Text(text = name,
             modifier = Modifier.fillMaxWidth(),
             fontSize = 13.sp, // 初始字体大小
             fontWeight = FontWeight.Bold,
             maxLines = 2, // 限制文字显示为1行
             overflow = TextOverflow.Ellipsis
        )
    }
}



@Preview
@Composable
fun Home(){
    BlockchainWalletBasicTheme {
        val icons = listOf(
            R.drawable.mnemonics, R.drawable.mnemonics,
            R.drawable.mnemonics, R.drawable.mnemonics,
            R.drawable.mnemonics, R.drawable.mnemonics,
            R.drawable.mnemonics, R.drawable.mnemonics
        )
        val labels = listOf(
                stringResource(R.string.mnemonics), "Button 2", "Button 3", "Button 4",
                "Button 5", "Button 6", "Button 7", "Button 8"
        )
        val ids = listOf(
                FeatureId.MNEMONICS.id,FeatureId.MNEMONICS.id,
                FeatureId.MNEMONICS.id,FeatureId.MNEMONICS.id,
                FeatureId.MNEMONICS.id,FeatureId.MNEMONICS.id,
                FeatureId.MNEMONICS.id,FeatureId.MNEMONICS.id,
        )
        val gridBtnList:ArrayList<GridButtonRes> = arrayListOf();
        for(i in icons.indices){
            gridBtnList.add(GridButtonRes(icons[i], labels[i], ids[i]))
        }
        MyGridScreen(gridBtnList)
    }
}

@Composable
fun MyGridScreen(gridButtonList: List<GridButtonRes>) {
    val context = LocalContext.current
    LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
    ) {
        items(gridButtonList) { item ->
            MyGridButton(
                    iconRes = item.resourceId,
                    name = item.name,
                    id = item.id,
                    onClick = { handleClickGridButton(context, it)}
            )
        }
    }
}

@Composable
fun TabContent2() {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Content for Tab 2")
    }
}

@Composable
fun TabContent3() {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Content for Tab 3")
    }
}


