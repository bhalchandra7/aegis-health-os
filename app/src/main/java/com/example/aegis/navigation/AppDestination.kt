package com.example.aegis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aegis.data.HealthDatabase
import com.example.aegis.data.HealthRepository
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.model.HealthUiState
import com.example.aegis.model.Medication
import com.example.aegis.ui.components.AegisTopBar
import com.example.aegis.ui.screens.AppointmentsScreen
import com.example.aegis.ui.screens.AssistantScreen
import com.example.aegis.ui.screens.EditAppointmentScreen
import com.example.aegis.ui.screens.EditMedicationScreen
import com.example.aegis.ui.screens.EditProfileScreen
import com.example.aegis.ui.screens.EmergencyScreen
import com.example.aegis.ui.screens.HealthScreen
import com.example.aegis.ui.screens.HomeScreen
import com.example.aegis.ui.screens.MedicationsScreen
import com.example.aegis.ui.screens.OnboardingScreen
import com.example.aegis.ui.screens.ProfileSecurityScreen
import com.example.aegis.ui.theme.AegisTheme
import com.example.aegis.viewmodel.HealthViewModel

enum class MainTab {
    HOME,
    HEALTH,
    MEDICATIONS,
    APPOINTMENTS,
    PROFILE
}

sealed class AppDestination(val route: String) {
    data object Home : AppDestination("home")
    data object Health : AppDestination("health")
    data object Medications : AppDestination("medications")
    data object Appointments : AppDestination("appointments")
    data object Profile : AppDestination("profile")
    data object EditProfile : AppDestination("edit_profile")
    data object EditMedication : AppDestination("edit_medication")
    data object EditAppointment : AppDestination("edit_appointment")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}

class HealthViewModelFactory(
    private val repository: HealthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HealthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun AppRoot() {
    val context = LocalContext.current.applicationContext
    val database = remember { HealthDatabase.getDatabase(context) }
    val repository = remember { HealthRepository(database.healthDao()) }
    val viewModel: HealthViewModel = viewModel(factory = HealthViewModelFactory(repository))
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    var showAssistantOverlay by remember { mutableStateOf(false) }
    var showOnboardingOverlay by remember { mutableStateOf(false) }
    var isEmergencyModeActive by remember { mutableStateOf(false) }
    var isDarkTheme by remember { mutableStateOf(false) }

    AegisTheme(darkTheme = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AegisTopBar(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme },
                        onEmergencyClick = { isEmergencyModeActive = true }
                    )
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .testTag("main_navigation_bar")
                    ) {
                        NavigationBarItem(
                            selected = currentTab == MainTab.HOME && !showAssistantOverlay,
                            onClick = {
                                showAssistantOverlay = false
                                currentTab = MainTab.HOME
                                navController.navigate(AppDestination.Home.route) {
                                    popUpTo(AppDestination.Home.route) { inclusive = false }
                                }
                            },
                            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Home") },
                            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_home")
                        )
                        NavigationBarItem(
                            selected = currentTab == MainTab.HEALTH && !showAssistantOverlay,
                            onClick = {
                                showAssistantOverlay = false
                                currentTab = MainTab.HEALTH
                                navController.navigate(AppDestination.Health.route) {
                                    popUpTo(AppDestination.Home.route) { inclusive = false }
                                }
                            },
                            icon = { Icon(imageVector = Icons.Default.MedicalServices, contentDescription = "Health") },
                            label = { Text("Health", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_health")
                        )
                        NavigationBarItem(
                            selected = currentTab == MainTab.MEDICATIONS && !showAssistantOverlay,
                            onClick = {
                                showAssistantOverlay = false
                                currentTab = MainTab.MEDICATIONS
                                navController.navigate(AppDestination.Medications.route) {
                                    popUpTo(AppDestination.Home.route) { inclusive = false }
                                }
                            },
                            icon = { Icon(imageVector = Icons.Default.Medication, contentDescription = "Medications") },
                            label = { Text("Meds", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_meds")
                        )
                        NavigationBarItem(
                            selected = currentTab == MainTab.APPOINTMENTS && !showAssistantOverlay,
                            onClick = {
                                showAssistantOverlay = false
                                currentTab = MainTab.APPOINTMENTS
                                navController.navigate(AppDestination.Appointments.route) {
                                    popUpTo(AppDestination.Home.route) { inclusive = false }
                                }
                            },
                            icon = { Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Appointments") },
                            label = { Text("Visits", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_appointments")
                        )
                        NavigationBarItem(
                            selected = currentTab == MainTab.PROFILE && !showAssistantOverlay,
                            onClick = {
                                showAssistantOverlay = false
                                currentTab = MainTab.PROFILE
                                navController.navigate(AppDestination.Profile.route) {
                                    popUpTo(AppDestination.Home.route) { inclusive = false }
                                }
                            },
                            icon = { Icon(imageVector = Icons.Default.Security, contentDescription = "Profile") },
                            label = { Text("Security", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_profile")
                        )
                    }
                },
                floatingActionButton = {
                    if (!showAssistantOverlay && !isEmergencyModeActive) {
                        FloatingActionButton(
                            onClick = { showAssistantOverlay = true },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(56.dp)
                                .testTag("fab_assistant")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Ask Health AI",
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    AppNavHost(
                        navController = navController,
                        uiState = uiState,
                        onOpenEmergency = { isEmergencyModeActive = true },
                        onOpenAssistant = { showAssistantOverlay = true },
                        onEditProfile = { navController.navigate(AppDestination.EditProfile.route) },
                        onAddMedication = { navController.navigate(AppDestination.EditMedication.route) },
                        onAddAppointment = { navController.navigate(AppDestination.EditAppointment.route) },
                        onSaveProfile = { profile ->
                            viewModel.updateProfile(profile)
                            navController.popBackStack()
                        },
                        onSaveMedication = { medication ->
                            viewModel.addMedication(medication)
                            navController.popBackStack()
                        },
                        onSaveAppointment = { appointment ->
                            viewModel.addAppointment(appointment)
                            navController.popBackStack()
                        },
                        showAssistantOverlay = showAssistantOverlay,
                        onAssistantClose = { showAssistantOverlay = false },
                        onOpenOnboarding = { showOnboardingOverlay = true },
                        onEmergencyClosed = { isEmergencyModeActive = false }
                    )
                }
            }

            AnimatedVisibility(
                visible = isEmergencyModeActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                EmergencyScreen(
                    profile = uiState.profile,
                    onCloseEmergency = { isEmergencyModeActive = false }
                )
            }

            AnimatedVisibility(
                visible = showOnboardingOverlay,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                BackHandler { showOnboardingOverlay = false }
                OnboardingScreen(onFinish = { showOnboardingOverlay = false })
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    uiState: HealthUiState,
    onOpenEmergency: () -> Unit,
    onOpenAssistant: () -> Unit,
    onEditProfile: () -> Unit,
    onAddMedication: () -> Unit,
    onAddAppointment: () -> Unit,
    onSaveProfile: (EmergencyProfile) -> Unit,
    onSaveMedication: (Medication) -> Unit,
    onSaveAppointment: (com.example.aegis.model.Appointment) -> Unit,
    showAssistantOverlay: Boolean,
    onAssistantClose: () -> Unit,
    onOpenOnboarding: () -> Unit,
    onEmergencyClosed: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route
    ) {
        composable(AppDestination.Home.route) {
            HomeScreen(
                emergencyProfile = uiState.profile,
                onNavigateToEmergency = onOpenEmergency,
                onNavigateToMedications = { navController.navigate(AppDestination.Medications.route) },
                onNavigateToAppointments = { navController.navigate(AppDestination.Appointments.route) },
                onNavigateToAssistant = onOpenAssistant,
                onNavigateToHealth = { navController.navigate(AppDestination.Health.route) }
            )
        }

        composable(AppDestination.Health.route) {
            HealthScreen(
                profile = uiState.profile,
                onNavigateToAssistantWithQuery = { onOpenAssistant() }
            )
        }

        composable(AppDestination.Medications.route) {
            MedicationsScreen(
                medications = uiState.medications,
                onAddMedication = onAddMedication,
                onEditMedication = { med -> onSaveMedication(med) }
            )
        }

        composable(AppDestination.Appointments.route) {
            AppointmentsScreen(
                appointments = uiState.appointments,
                onAddAppointment = onAddAppointment,
                onEditAppointment = { appointment -> onSaveAppointment(appointment) }
            )
        }

        composable(AppDestination.Profile.route) {
            ProfileSecurityScreen(
                profile = uiState.profile,
                onOpenOnboarding = onOpenOnboarding,
                onEditProfile = onEditProfile,
                onAddContact = onEditProfile
            )
        }

        composable(AppDestination.EditProfile.route) {
            EditProfileScreen(
                profile = uiState.profile,
                onSave = onSaveProfile,
                onCancel = { navController.popBackStack() }
            )
        }

        composable(AppDestination.EditMedication.route) {
            EditMedicationScreen(
                onSave = onSaveMedication,
                onCancel = { navController.popBackStack() }
            )
        }

        composable(AppDestination.EditAppointment.route) {
            EditAppointmentScreen(
                onSave = onSaveAppointment,
                onCancel = { navController.popBackStack() }
            )
        }
    }

    if (showAssistantOverlay) {
        BackHandler { onAssistantClose() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AssistantScreen(
                profile = uiState.profile,
                onNavigateToEmergency = {
                    onAssistantClose()
                    onOpenEmergency()
                }
            )
        }
    }

    if (showAssistantOverlay) {
        Box(modifier = Modifier)
    }
}

@Composable
fun TitleRow(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        androidx.compose.material3.IconButton(onClick = {}) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.filled.Add,
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun InfoPanelCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                imageVector = androidx.compose.material.icons.automirrored.filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun QuickMetricCard(label: String, value: String, sub: String) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(sub, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
