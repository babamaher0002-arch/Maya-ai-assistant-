package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MayaGlowingOrb
import com.example.ui.components.MayaGradientButton
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextTertiary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("alex.morgan@maya.ai") }
    var password by remember { mutableStateOf("••••••••") }
    var name by remember { mutableStateOf("Alex Morgan") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MayaGlowingOrb(size = 90.dp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (isSignUp) "Create Account" else "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = MayaTextWhite,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isSignUp) "Join the next era of autonomous AI" else "Sign in to access your Maya AI workspace",
            style = MaterialTheme.typography.bodyMedium,
            color = MayaTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tab Selector
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MayaCardBg,
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(4.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (!isSignUp) MayaViolet else MayaCardBg)
                        .clickable { isSignUp = false }
                        .padding(vertical = 10.dp)
                        .testTag("tab_signin"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.labelLarge,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSignUp) MayaViolet else MayaCardBg)
                        .clickable { isSignUp = true }
                        .padding(vertical = 10.dp)
                        .testTag("tab_signup"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.labelLarge,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isSignUp) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name", color = MayaTextSecondary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_name_field"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MayaCyanLight,
                    unfocusedBorderColor = MayaCardBorder,
                    focusedTextColor = MayaTextWhite,
                    unfocusedTextColor = MayaTextWhite
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address", color = MayaTextSecondary) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = MayaCyanLight)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("auth_email_field"),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MayaCyanLight,
                unfocusedBorderColor = MayaCardBorder,
                focusedTextColor = MayaTextWhite,
                unfocusedTextColor = MayaTextWhite
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = MayaTextSecondary) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MayaCyanLight)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = MayaTextTertiary
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("auth_password_field"),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MayaCyanLight,
                unfocusedBorderColor = MayaCardBorder,
                focusedTextColor = MayaTextWhite,
                unfocusedTextColor = MayaTextWhite
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        MayaGradientButton(
            text = if (isSignUp) "Create Maya Account" else "Sign In",
            onClick = onAuthSuccess,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Social Button
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MayaCardBg),
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(onClick = onAuthSuccess)
                .testTag("google_auth_btn")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "🌐", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Continue with Google",
                    style = MaterialTheme.typography.labelLarge,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Guest Access option
        Button(
            onClick = onAuthSuccess,
            colors = ButtonDefaults.buttonColors(containerColor = MayaCardBg.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, MayaViolet.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("guest_access_btn")
        ) {
            Text(
                text = "⚡ Instant Demo Access (Skip Sign In)",
                style = MaterialTheme.typography.labelMedium,
                color = MayaCyanLight,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "By continuing you accept Maya AI Terms of Service and Privacy Policy.",
            style = MaterialTheme.typography.bodySmall,
            color = MayaTextTertiary,
            textAlign = TextAlign.Center
        )
    }
}
