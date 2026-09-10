package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Maya AI Obsidian & Space Palette
val MayaBgDark = Color(0xFF0B0E14)
val MayaBgElevated = Color(0xFF111724)
val MayaCardBg = Color(0xFF171F30)
val MayaCardBgHover = Color(0xFF1E283D)
val MayaCardBorder = Color(0xFF26334D)
val MayaCardBorderGlow = Color(0xFF3B82F6)

// Maya Brand Accents
val MayaViolet = Color(0xFF7C3AED)
val MayaVioletLight = Color(0xFFA78BFA)
val MayaVioletDark = Color(0xFF5B21B6)
val MayaCyan = Color(0xFF06B6D4)
val MayaCyanLight = Color(0xFF38BDF8)
val MayaEmerald = Color(0xFF10B981)
val MayaEmeraldLight = Color(0xFF34D399)
val MayaAmber = Color(0xFFF59E0B)
val MayaRose = Color(0xFFF43F5E)

// Text Colors
val MayaTextWhite = Color(0xFFF8FAFC)
val MayaTextSecondary = Color(0xFF94A3B8)
val MayaTextTertiary = Color(0xFF64748B)

// Light Theme Palette
val MayaBgLight = Color(0xFFF8FAFC)
val MayaSurfaceLight = Color(0xFFFFFFFF)
val MayaCardBorderLight = Color(0xFFE2E8F0)
val MayaTextDark = Color(0xFF0F172A)
val MayaTextMutedLight = Color(0xFF64748B)

// Brand Gradients
val MayaGradientBrand = Brush.linearGradient(
    listOf(MayaViolet, MayaCyan)
)

val MayaGradientAccent = Brush.linearGradient(
    listOf(MayaViolet, MayaRose)
)

val MayaGradientCard = Brush.verticalGradient(
    listOf(Color(0xFF1A2234), Color(0xFF121724))
)

val MayaOrbGlow = Brush.radialGradient(
    listOf(MayaCyan.copy(alpha = 0.9f), MayaViolet.copy(alpha = 0.6f), Color.Transparent)
)

