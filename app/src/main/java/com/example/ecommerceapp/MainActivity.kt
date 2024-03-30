package com.example.ecommerceapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.presentation.AddCardView
import com.example.ecommerceapp.presentation.CardViewModel
import com.example.ecommerceapp.presentation.CartProductView
import com.example.ecommerceapp.presentation.CatalogProductView
import com.example.ecommerceapp.presentation.PaymentView
import com.example.ecommerceapp.presentation.ProductDetailView
import com.example.ecommerceapp.presentation.ProductViewModel
import com.example.ecommerceapp.presentation.ui.theme.EcommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val myViewModel: ProductViewModel by viewModels()
    private val myCardViewModel: CardViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                NavigationDrawerItem(
                                    label = { Text(text = "Products On Sale")},
                                    selected = navController.currentBackStackEntryAsState().value?.destination?.route == "CatalogProductView",
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        navController.navigate("CatalogProductView")
                                    }
                                )
                                NavigationDrawerItem(
                                    label = { Text(text = "Products On Cart")},
                                    selected = navController.currentBackStackEntryAsState().value?.destination?.route == "CartProductView",
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        navController.navigate("CartProductView")
                                    }
                                )
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = { CenterAlignedTopAppBar(
                                title = { Text(text = "Ecommerce App")},
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                                    }
                                }
                            )
                            },
                        ) {
                            padding ->

                            Box(modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()) {
                                NavHost(
                                    navController = navController,
                                    startDestination = "CatalogProductView",
                                ) {
                                    composable("CatalogProductView") {
                                        LaunchedEffect(Unit) {
                                            myViewModel.getProductsList()
                                        }

                                        val productList by myViewModel.productList.collectAsStateWithLifecycle()

                                        CatalogProductView(
                                            productList = productList,
                                            addToCart = { product ->
                                                myViewModel.addToCart(product)
                                            },
                                            productDetails = { id ->
//                                                myViewModel.getProductById(id)
                                                navController.navigate("ProductDetailView/$id")
                                            }
                                        )
                                    }
                                    composable("CartProductView") {
                                        val cartList by myViewModel.cartItems.collectAsStateWithLifecycle()
                                        LaunchedEffect(key1 = Unit) {
                                            myCardViewModel.setCreditCardFields()
                                        }

                                        CartProductView(
                                            productList = cartList ?: emptyList(),
                                            addProductIcon = { product ->
                                                myViewModel.addToCart(product)
                                            },
                                            removeProductIcon = { product ->
                                                myViewModel.removeFromCart(product)
                                            },
                                            navigateToCheckOut = {
                                                myViewModel.calculateTotalToPay()
                                                navController.navigate("PaymentView")
                                            }
                                        )
                                    }
                                    composable("PaymentView") {
                                        val totalSum by myViewModel.totalSum.collectAsStateWithLifecycle()
                                        val cardList by myCardViewModel.cardList.collectAsStateWithLifecycle()
                                        PaymentView(
                                            totalAmount = totalSum,
                                            cardList =cardList,
                                            onAddCardClicked = {
                                                navController.navigate("AddCardView")
                                            },
                                            onProceedClicked = {
                                                myViewModel.setCartItemsList()
                                                navController.navigate(route = "CatalogProductView") {
                                                    popUpTo("CatalogProductView") { inclusive = true }
                                                }
                                            }
                                        )
                                    }
                                    composable("AddCardView") {
                                        val cardForm by myCardViewModel.cardForm.collectAsStateWithLifecycle()
                                        val cardNumberValidation by myCardViewModel.cardNumberValidationState.collectAsStateWithLifecycle()
                                        val holderNameValidation by myCardViewModel.holderNameValidationState.collectAsStateWithLifecycle()
                                        val expDateValidation by myCardViewModel.expDateValidationState.collectAsStateWithLifecycle()
                                        val cvvValidation by myCardViewModel.cvvValidationState.collectAsStateWithLifecycle()
                                        val flagCardNumberButtonStateValidation by myCardViewModel.flagCardNumberButtonState.collectAsStateWithLifecycle()
                                        val cardButtonEnabledState by myCardViewModel.cardButtonEnabledState.collectAsStateWithLifecycle()

                                        LaunchedEffect(key1 = Unit) {
                                            myCardViewModel.resetValidationStates()
                                            myCardViewModel.flagCardNumberButton(false)
                                        }

                                        AddCardView(
                                            cardNumberValue = cardForm.cardNumber,
                                            onCardNumberValueChanged = myCardViewModel::onFieldCardChange,
                                            cardNumberValidation = cardNumberValidation,
                                            onCardNumberValidation = {
                                                myCardViewModel.validateCardNumber(it)
                                            },
                                            holderNameValue = cardForm.holderName,
                                            onHolderNameValueChanged = myCardViewModel::onFieldCardChange,
                                            holderNameValidation = holderNameValidation,
                                            onHolderNameValidation = {
                                                myCardViewModel.validateHolderName(it)
                                            },
                                            expDateValue = cardForm.expDate,
                                            onExpDateValueChanged = myCardViewModel::onFieldCardChange,
                                            expDateValidation = expDateValidation,
                                            onExpDateValidation = {
                                                myCardViewModel.validateExpDate(it)
                                            },
                                            cvvValue = cardForm.cvv,
                                            onCvvValueChanged = myCardViewModel::onFieldCardChange,
                                            cvvValidation = cvvValidation,
                                            onCvvValidation = {
                                                myCardViewModel.validateCvv(it)
                                            },
                                            flagAddButtonEnabled = flagCardNumberButtonStateValidation,
                                            onFlagAddButtonEnabled = {
                                                myCardViewModel.flagCardNumberButton(it)
                                            },
                                            cardButtonEnabled = cardButtonEnabledState,
                                            addCardToList = {
                                                myCardViewModel.addCardToList()
                                                navController.popBackStack()
                                            }
                                        )
                                    }
                                    composable(
                                        route = "ProductDetailView/{id}",
                                        arguments = listOf(
                                            navArgument("id") { type = NavType.IntType }
                                        )
                                    ) { navBackStackEntry ->

                                        val id = navBackStackEntry.arguments?.getInt("id")
//                                        myViewModel.getProductById(id!!)
                                        LaunchedEffect(Unit) {
                                            myViewModel.getProductById(id!!)
                                        }

                                        val product by myViewModel.product.collectAsStateWithLifecycle()

                                        ProductDetailView(
                                            product = product,
                                            addProduct = {
                                                myViewModel.addToCart(product)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
