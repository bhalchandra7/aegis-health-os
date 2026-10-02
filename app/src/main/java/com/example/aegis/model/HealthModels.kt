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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aegis.data.SampleData
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.ui.components.AegisTopBar
import com.example.aegis.ui.screens.AppointmentsScreen
import com.example.aegis.ui.screens.AssistantScreen
import com.example.aegis.ui.screens.EmergencyScreen
import com.example.aegis.ui.screens.HealthScreen
import com.example.aegis.ui.screens.HomeScreen
import com.example.aegis.ui.screens.MedicationsScreen
import com.example.aegis.ui.screens.OnboardingScreen
import com.example.aegis.ui.screens.ProfileSecurityScreen
import com.example.aegis.ui.theme.AegisTheme

enum class MainTab {
    HOME,
    HEALTH,
    MEDICATIONS,
    APPOINTMENTS,
    PROFILE
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

@Composable
fun AppRoot() {
    val profile = remember { SampleData.defaultProfile() }

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
                    when (currentTab) {
                        MainTab.HOME -> HomeScreen(
                            emergencyProfile = profile,
                            onNavigateToEmergency = { isEmergencyModeActive = true },
                            onNavigateToMedications = { currentTab = MainTab.MEDICATIONS },
                            onNavigateToAppointments = { currentTab = MainTab.APPOINTMENTS },
                            onNavigateToAssistant = { showAssistantOverlay = true },
                            onNavigateToHealth = { currentTab = MainTab.HEALTH }
                        )

                        MainTab.HEALTH -> HealthScreen(
                            profile = profile,
                            onNavigateToAssistantWithQuery = { showAssistantOverlay = true }
                        )

                        MainTab.MEDICATIONS -> MedicationsScreen(medications = SampleData.medications())

                        MainTab.APPOINTMENTS -> AppointmentsScreen(appointments = SampleData.appointments())

                        MainTab.PROFILE -> ProfileSecurityScreen(
                            profile = profile,
                            onOpenOnboarding = { showOnboardingOverlay = true }
                        )
                    }

                    AnimatedVisibility(
                        visible = showAssistantOverlay,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                    ) {
                        BackHandler { showAssistantOverlay = false }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            AssistantScreen(
                                profile = profile,
                                onNavigateToEmergency = {
                                    showAssistantOverlay = false
                                    isEmergencyModeActive = true
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = isEmergencyModeActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                EmergencyScreen(
                    profile = profile,
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
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
    androidx.compose.material3.Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(18.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
