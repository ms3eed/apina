package fi.evident.apina.cli

import java.util.*

internal class CommandLineArguments {

    val files: MutableList<String> = ArrayList()
    val blackBoxPatterns: MutableList<String> = ArrayList()
    val imports: MutableList<ImportArgument> = ArrayList()

    private fun parse(arg: String) {
        // This could be more general, but this is all we need for now.

        val blackBox = parseOptionalWithValue("black-box", arg)
        if (blackBox != null) {
            blackBoxPatterns.add(blackBox)
            return
        }

        val anImport = parseOptionalWithValue("import", arg)
        if (anImport != null) {
            val colonIndex = anImport.indexOf(':')
            if (colonIndex == -1)
                throw IllegalArgumentException("invalid import: " + anImport)

            val types = anImport.substring(0, colonIndex).split(",".toRegex()).toTypedArray()
            val module = anImport.substring(colonIndex + 1)

            imports.add(ImportArgument(Arrays.asList(*types), module))
            return
        }

        files.add(arg)
    }

    class ImportArgument(val types: List<String>, val module: String)

    companion object {

        private fun parseOptionalWithValue(name: String, arg: String): String? {
            val prefix = "--$name="
            return if (arg.startsWith(prefix))
                arg.substring(prefix.length)
            else
                null
        }

        fun parse(args: Array<String>): CommandLineArguments {
            val result = CommandLineArguments()

            for (arg in args)
                result.parse(arg)

            return result
        }
    }
}
