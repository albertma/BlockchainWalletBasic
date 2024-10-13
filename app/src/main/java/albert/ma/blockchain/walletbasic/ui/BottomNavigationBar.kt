package albert.ma.blockchain.walletbasic.ui

import albert.ma.blockchain.walletbasic.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(selectedTabIndex: Int, onTabSelected: (Int) -> Unit){
    NavigationBar(
            containerColor = Color.Blue,
            modifier = Modifier.fillMaxWidth()
    ){
        NavigationBarItem(
                selected = selectedTabIndex == 0,
                onClick = { onTabSelected(0) },
                label = { Text(stringResource(R.string.features)) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Tab 1") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Gray, // 选中时图标的颜色
                        selectedTextColor = Color.White, // 选中时文本的颜色
                        unselectedIconColor = Color.White, // 未选中时图标的颜色
                        unselectedTextColor = Color.White // 未选中时文本的颜色
                )
        )
        NavigationBarItem(
                selected = selectedTabIndex == 1,
                onClick = { onTabSelected(1) },
                label = { Text("Tab 2") },
                icon = { Icon(Icons.Default.AccountBox, contentDescription = "Tab 2") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                )
        )
        NavigationBarItem(
                selected = selectedTabIndex == 2,
                onClick = { onTabSelected(2) },
                label = { Text("Tab 3") },
                icon = { Icon(Icons.Default.Menu, contentDescription = "Tab 3") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                )
        )
    }

}