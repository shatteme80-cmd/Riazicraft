package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Core User-Specified Editorial Palette (Obsidian, Graphite, Warm Taupe, Pure White)
val ObsidianInk = Color(0xFF050206)
val GraphiteSlate = Color(0xFF3B3B3B)
val WarmTaupe = Color(0xFF9A8174)
val PureWhite = Color(0xFFFFFFFF)

// Complementary Editorial Tonal Extensions
val VelvetCharcoal = Color(0xFF100E12)
val SmokedGraphite = Color(0xFF1A181C)
val ElevatedSlate = Color(0xFF262428)
val MutedAsh = Color(0xFF565358)

val DeepMocha = Color(0xFF5D4D45)
val CocoaBronze = Color(0xFF7B665B)
val ChampagneTaupe = Color(0xFFB59E91)
val WarmStone = Color(0xFFD6C6BC)
val WarmAlabaster = Color(0xFFEDEAE7)
val MutedStone = Color(0xFFA9A29D)
val AshStone = Color(0xFF7C7672)

// Base Dark Surfaces
val BgDark = ObsidianInk
val SurfaceDark = VelvetCharcoal
val SurfaceVariantDark = ElevatedSlate
val CardDark = SmokedGraphite
val BorderDark = GraphiteSlate

// iOS-Inspired Smoked Obsidian & Brushed Graphite Glassmorphism
val GlassBase = Color(0xCC110F13)
val GlassSurfaceTop = Color(0x21FFFFFF)      // Pure White frosted specular sheen
val GlassSurfaceBottom = Color(0x2B3B3B3B)   // Graphite Slate frosted depth
val GlassRimTop = Color(0x47FFFFFF)          // Pure White crisp top hairline rim
val GlassRimBottom = Color(0x663B3B3B)       // Graphite Slate lower rim
val AmbientTaupeGlow = Color(0x3D9A8174)     // Warm Taupe subtle ambient mist
val AmbientGraphiteGlow = Color(0x4D3B3B3B)  // Graphite Slate atmospheric glow

// Harmonized App Accent Tokens (Monochrome + Warm Taupe Spectrum)
val MintPrimary = ChampagneTaupe             // Primary Warm Taupe Accent
val MintSecondary = WarmTaupe                // Core #9A8174 Taupe Fill
val MintMuted = DeepMocha                    // Deep Espresso Taupe Container

val PurpleAccent = WarmStone                 // Secondary Warm Stone Highlight
val PurpleDeep = GraphiteSlate               // #3B3B3B Brushed Slate
val PurpleMuted = ElevatedSlate

val GoldAccent = Color(0xFFD8C1AD)           // Champagne Stone for Stars & Badges
val CoralRed = Color(0xFFC86D68)             // Muted Editorial Rose-Terracotta
val CyanAccent = WarmTaupe                   // #9A8174 Taupe Accent for Lesson 3

// Semantic Feedback Colors Harmonized with Palette
val SuccessSage = Color(0xFF84A890)          // Muted Editorial Sage
val SuccessBg = Color(0xFF111B14)
val ErrorBg = Color(0xFF221213)
val ErrorActionTone = Color(0xFF6B423F)      // Muted Rosewood-Taupe CTA

// High-Contrast Editorial Typography Hierarchy
val TextPrimary = PureWhite                  // Crisp #FFFFFF Primary Ink
val TextSecondary = WarmAlabaster            // Soft Off-White Body Readability
val TextMuted = MutedStone                   // Warm Stone Captions
