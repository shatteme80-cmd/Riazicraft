package com.example

import com.example.ui.components.ProbabilityMath
import com.example.util.PersianUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testPersianDigitsConversion() {
        assertEquals("۱۲۳۴۵", PersianUtils.toPersian(12345))
        assertEquals("۰", PersianUtils.toPersian(0))
        assertEquals("۱۰۰", PersianUtils.toPersian("100"))
    }

    @Test
    fun testSetFormatting() {
        val set = setOf(3, 1, 2)
        assertEquals("{ ۱، ۲، ۳ }", PersianUtils.formatSet(set))
        assertEquals("A ∪ B = { ۱، ۲، ۳ }", PersianUtils.formatSet(set, "A ∪ B"))
        assertEquals("∅", PersianUtils.formatSet(emptySet()))
    }

    @Test
    fun testSetMathematicalOperations() {
        val setA = setOf(1, 2, 3)
        val setB = setOf(3, 4, 5)

        // Union
        val union = setA union setB
        assertEquals(setOf(1, 2, 3, 4, 5), union)

        // Intersection
        val intersection = setA intersect setB
        assertEquals(setOf(3), intersection)

        // Difference A - B
        val diffAB = setA - setB
        assertEquals(setOf(1, 2), diffAB)

        // Difference B - A != A - B
        val diffBA = setB - setA
        assertEquals(setOf(4, 5), diffBA)
        assertTrue(diffAB != diffBA)
    }

    @Test
    fun testProbabilityCalculation() {
        val sampleSpace = setOf(1, 2, 3, 4, 5, 6)
        val eventEven = sampleSpace.filter { it % 2 == 0 }.toSet()
        val eventPrimes = setOf(2, 3, 5)

        assertEquals(3, eventEven.size)
        assertEquals(3, eventPrimes.size)
        assertEquals("۳ / ۶", PersianUtils.formatFraction(eventEven.size, sampleSpace.size))
    }

    @Test
    fun testProbabilityMathFractions() {
        assertEquals(3, ProbabilityMath.gcd(6, 9))
        assertEquals(2, ProbabilityMath.gcd(4, 6))

        assertTrue(ProbabilityMath.areFractionsEqual(4, 6, 2, 3))
        assertTrue(ProbabilityMath.areFractionsEqual(6, 9, 2, 3))
        assertTrue(ProbabilityMath.areFractionsEqual(0, 12, 0, 1))
        assertFalse(ProbabilityMath.areFractionsEqual(7, 9, 6, 9))
        assertFalse(ProbabilityMath.areFractionsEqual(1, 0, 1, 2))

        assertTrue(ProbabilityMath.isSimplified(2, 3))
        assertTrue(ProbabilityMath.isSimplified(7, 12))
        assertTrue(ProbabilityMath.isSimplified(0, 1))
        assertFalse(ProbabilityMath.isSimplified(4, 6))
        assertFalse(ProbabilityMath.isSimplified(6, 9))
        assertFalse(ProbabilityMath.isSimplified(0, 12))
    }
}
