package pl.org.opi.ragdeterm.prompt;

import java.util.*;
import java.util.regex.*;
import java.util.function.Function;

public class PromptProcessor {

    private static final Pattern RG_PATTERN =
            Pattern.compile("\\[\\*RG\\s+(.*?)\\s+\\*RG\\]", Pattern.DOTALL);

    public static String process(String input) {
        Matcher matcher = RG_PATTERN.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String commandBody = matcher.group(1).trim();
            String replacement = executeCommand(commandBody);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private static String executeCommand(String commandBody) {
        ParsedCommand cmd = ParsedCommand.parse(commandBody);

        Function<List<String>, String> function =
                RGFunctionDispatcher.get(cmd.name);

        if (function == null) {
            throw new IllegalArgumentException("Undefined RG command: " + cmd.name);
        }

        return function.apply(cmd.arguments);
    }

    private static class ParsedCommand {
        final String name;
        final List<String> arguments;

        private ParsedCommand(String name, List<String> arguments) {
            this.name = name;
            this.arguments = arguments;
        }

        static ParsedCommand parse(String text) {
            int openParen = text.indexOf('(');
            int closeParen = text.lastIndexOf(')');

            if (openParen < 0 || closeParen < 0) {
                throw new IllegalArgumentException("RG syntax error: " + text);
            }

            String name = text.substring(0, openParen).trim();
            String argsPart = text.substring(openParen + 1, closeParen);

            List<String> args = parseArguments(argsPart);
            return new ParsedCommand(name, args);
        }

        private static List<String> parseArguments(String args) {
            List<String> result = new ArrayList<>();
            Matcher m = Pattern.compile(
                            "\"([^\"]*)\"|([^,\\s]+)")
                    .matcher(args);

            while (m.find()) {
                if (m.group(1) != null) {
                    result.add(m.group(1));
                } else {
                    result.add(m.group(2));
                }
            }
            return result;
        }
    }
}
