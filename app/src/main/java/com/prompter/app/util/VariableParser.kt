package com.prompter.app.util

/**
 * Utility class for parsing and handling prompt variables
 * Variables are declared in the format {variable_name}
 */
object VariableParser {
    
    // Regex pattern to match {variable_name}
    private val VARIABLE_PATTERN = Regex("""\{([a-zA-Z0-9_]+)\}""")
    
    /**
     * Extract all variable names from a prompt body
     * @param promptBody The prompt template text
     * @return List of unique variable names (without braces)
     */
    fun extractVariables(promptBody: String): List<String> {
        return VARIABLE_PATTERN.findAll(promptBody)
            .map { it.groupValues[1] }
            .distinct()
            .toList()
    }
    
    /**
     * Replace variables in the prompt body with provided values
     * @param promptBody The prompt template text
     * @param values Map of variable names to their values
     * @return Prompt text with variables replaced
     */
    fun fillVariables(promptBody: String, values: Map<String, String>): String {
        var result = promptBody
        values.forEach { (key, value) ->
            result = result.replace("{$key}", value)
        }
        return result
    }
    
    /**
     * Check if a prompt body contains any variables
     * @param promptBody The prompt template text
     * @return True if variables are found
     */
    fun hasVariables(promptBody: String): Boolean {
        return VARIABLE_PATTERN.containsMatchIn(promptBody)
    }
    
    /**
     * Validate that all variables in the prompt have values
     * @param promptBody The prompt template text
     * @param values Map of variable names to their values
     * @return True if all variables have non-empty values
     */
    fun areAllVariablesFilled(promptBody: String, values: Map<String, String>): Boolean {
        val variables = extractVariables(promptBody)
        return variables.all { variable ->
            values[variable]?.isNotBlank() == true
        }
    }
    
    /**
     * Get missing variables (variables without values)
     * @param promptBody The prompt template text
     * @param values Map of variable names to their values
     * @return List of variable names that are missing or empty
     */
    fun getMissingVariables(promptBody: String, values: Map<String, String>): List<String> {
        val variables = extractVariables(promptBody)
        return variables.filter { variable ->
            values[variable]?.isBlank() != false
        }
    }
}
