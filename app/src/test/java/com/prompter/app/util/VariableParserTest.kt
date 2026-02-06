package com.prompter.app.util

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for VariableParser
 */
class VariableParserTest {

    @Test
    fun extractVariables_withNoVariables_returnsEmptyList() {
        val input = "This is a simple text without variables"
        val result = VariableParser.extractVariables(input)
        assertTrue(result.isEmpty())
    }

    @Test
    fun extractVariables_withSingleVariable_returnsSingleVariable() {
        val input = "Hello {name}!"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("name"), result)
    }

    @Test
    fun extractVariables_withMultipleVariables_returnsAllVariables() {
        val input = "Hello {name}, you are {age} years old and live in {city}"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("name", "age", "city"), result)
    }

    @Test
    fun extractVariables_withDuplicateVariables_returnsUniqueVariables() {
        val input = "Hello {name}, nice to meet you {name}!"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("name"), result)
    }

    @Test
    fun extractVariables_withUnderscores_returnsVariables() {
        val input = "User: {user_name}, Email: {user_email}"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("user_name", "user_email"), result)
    }

    @Test
    fun extractVariables_withNumbers_returnsVariables() {
        val input = "Item {item1} and {item2}"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("item1", "item2"), result)
    }

    @Test
    fun extractVariables_withInvalidCharacters_ignoresThem() {
        val input = "Valid {name} and invalid {invalid-var} and {invalid.var}"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("name"), result)
    }

    @Test
    fun fillVariables_withAllVariablesFilled_replacesAll() {
        val input = "Hello {name}, you are {age} years old"
        val values = mapOf("name" to "João", "age" to "25")
        val result = VariableParser.fillVariables(input, values)
        assertEquals("Hello João, you are 25 years old", result)
    }

    @Test
    fun fillVariables_withPartialVariables_replacesOnlyProvided() {
        val input = "Hello {name}, you are {age} years old"
        val values = mapOf("name" to "João")
        val result = VariableParser.fillVariables(input, values)
        assertEquals("Hello João, you are {age} years old", result)
    }

    @Test
    fun fillVariables_withNoVariables_returnsOriginal() {
        val input = "Hello world"
        val values = mapOf("name" to "João")
        val result = VariableParser.fillVariables(input, values)
        assertEquals("Hello world", result)
    }

    @Test
    fun fillVariables_withEmptyValues_returnsOriginal() {
        val input = "Hello {name}"
        val values = emptyMap<String, String>()
        val result = VariableParser.fillVariables(input, values)
        assertEquals("Hello {name}", result)
    }

    @Test
    fun hasVariables_withVariables_returnsTrue() {
        val input = "Hello {name}"
        assertTrue(VariableParser.hasVariables(input))
    }

    @Test
    fun hasVariables_withoutVariables_returnsFalse() {
        val input = "Hello world"
        assertFalse(VariableParser.hasVariables(input))
    }

    @Test
    fun areAllVariablesFilled_withAllFilled_returnsTrue() {
        val input = "Hello {name}, you are {age}"
        val values = mapOf("name" to "João", "age" to "25")
        assertTrue(VariableParser.areAllVariablesFilled(input, values))
    }

    @Test
    fun areAllVariablesFilled_withPartialFilled_returnsFalse() {
        val input = "Hello {name}, you are {age}"
        val values = mapOf("name" to "João")
        assertFalse(VariableParser.areAllVariablesFilled(input, values))
    }

    @Test
    fun areAllVariablesFilled_withEmptyValue_returnsFalse() {
        val input = "Hello {name}"
        val values = mapOf("name" to "")
        assertFalse(VariableParser.areAllVariablesFilled(input, values))
    }

    @Test
    fun areAllVariablesFilled_withBlankValue_returnsFalse() {
        val input = "Hello {name}"
        val values = mapOf("name" to "   ")
        assertFalse(VariableParser.areAllVariablesFilled(input, values))
    }

    @Test
    fun getMissingVariables_withAllFilled_returnsEmpty() {
        val input = "Hello {name}, you are {age}"
        val values = mapOf("name" to "João", "age" to "25")
        val result = VariableParser.getMissingVariables(input, values)
        assertTrue(result.isEmpty())
    }

    @Test
    fun getMissingVariables_withPartialFilled_returnsMissing() {
        val input = "Hello {name}, you are {age} from {city}"
        val values = mapOf("name" to "João")
        val result = VariableParser.getMissingVariables(input, values)
        assertEquals(listOf("age", "city"), result)
    }

    @Test
    fun getMissingVariables_withEmptyValue_returnsThatVariable() {
        val input = "Hello {name}, you are {age}"
        val values = mapOf("name" to "", "age" to "25")
        val result = VariableParser.getMissingVariables(input, values)
        assertEquals(listOf("name"), result)
    }

    @Test
    fun extractVariables_complexPrompt_extractsCorrectly() {
        val input = """
            Escreve um email para {destinatario} sobre {assunto}.
            
            Contexto: {contexto}
            Tom: {tom}
            Comprimento: {comprimento}
            
            Inclui {pontos_chave} pontos principais.
        """.trimIndent()
        
        val result = VariableParser.extractVariables(input)
        assertEquals(
            listOf("destinatario", "assunto", "contexto", "tom", "comprimento", "pontos_chave"),
            result
        )
    }

    @Test
    fun fillVariables_complexPrompt_fillsCorrectly() {
        val input = """
            Escreve um email para {destinatario} sobre {assunto}.
            Tom: {tom}
        """.trimIndent()
        
        val values = mapOf(
            "destinatario" to "João Silva",
            "assunto" to "Reunião de Projeto",
            "tom" to "Formal"
        )
        
        val expected = """
            Escreve um email para João Silva sobre Reunião de Projeto.
            Tom: Formal
        """.trimIndent()
        
        val result = VariableParser.fillVariables(input, values)
        assertEquals(expected, result)
    }
}
