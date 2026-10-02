package com.example.aegis.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aegis.model.Appointment

@Composable
fun EditAppointmentScreen(
    onSave: (Appointment) -> Unit,
    onCancel: () -> Unit
) {
    var doctorName by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var questions by remember { mutableStateOf("") }
    var followUpNotes by remember { mutableStateOf("") }
    var completed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add Appointment")

        OutlinedTextField(value = doctorName, onValueChange = { doctorName = it }, label = { Text("Doctor") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = specialty, onValueChange = { specialty = it }, label = { Text("Specialty") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = symptoms, onValueChange = { symptoms = it }, label = { Text("Symptoms") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = questions, onValueChange = { questions = it }, label = { Text("Questions") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = followUpNotes, onValueChange = { followUpNotes = it }, label = { Text("Follow-up Notes") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onCancel) { Text("Cancel") }
            Button(onClick = {
                if (doctorName.isNotBlank()) {
                    onSave(
                        Appointment(
                            doctorName = doctorName,
                            specialty = specialty,
                            date = date,
                            time = time,
                            location = location,
                            symptoms = symptoms.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            questions = questions.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            followUpNotes = followUpNotes,
                            completed = completed
                        )
                    )
                }
            }) {
                Text("Save")
            }
        }
    }
}
